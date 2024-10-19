package sourceCode;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DailyWeatherSummary {

	public void createSummaryTable() throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            // Drop the table if it already exists (if that's the intended behavior)
            String dropSQL = "DROP TABLE daily_weather_summary";
            try {
                PreparedStatement dropStmt = conn.prepareStatement(dropSQL);
                dropStmt.execute();
            } catch (SQLException e) {
                // Table does not exist, ignore the error
            }

            // Now create the table
            String createSQL = "CREATE TABLE daily_weather_summary (" +
                    "summary_date DATE PRIMARY KEY, " +
                    "average_temperature FLOAT, " +
                    "max_temperature FLOAT, " +
                    "min_temperature FLOAT, " +
                    "dominant_condition VARCHAR2(50))";

            try (PreparedStatement pstmt = conn.prepareStatement(createSQL)) {
                pstmt.executeUpdate();
            }
        }
    }

	public void calculateAndStoreDailySummary() throws SQLException {
	    try (Connection conn = DatabaseConnection.getConnection()) {
	        LocalDate today = LocalDate.now();
	        
	        // SQL query to calculate daily aggregates
	        String aggregateSQL = "SELECT AVG(temperature) AS avg_temp, " +
	                              "MAX(temperature) AS max_temp, " +
	                              "MIN(temperature) AS min_temp, " +
	                              "condition AS dominant_condition " +
	                              "FROM weather_data_new " +
	                              "GROUP BY condition " +
	                              "ORDER BY COUNT(condition) DESC FETCH FIRST 1 ROWS ONLY";

	        // Prepare statement for aggregation
	        try (PreparedStatement aggregateStmt = conn.prepareStatement(aggregateSQL);
	             ResultSet rs = aggregateStmt.executeQuery()) {

	            if (rs.next()) {
	                // Fetch calculated aggregates
	                double averageTemperature = rs.getDouble("avg_temp");
	                double maxTemperature = rs.getDouble("max_temp");
	                double minTemperature = rs.getDouble("min_temp");
	                String dominantCondition = rs.getString("dominant_condition");

	                // Insert the calculated summary into daily_weather_summary table
	                String insertSummarySQL = "INSERT INTO daily_weather_summary " +
	                                          "(summary_date, average_temperature, max_temperature, min_temperature, dominant_condition) " +
	                                          "VALUES (?, ?, ?, ?, ?)";
	                try (PreparedStatement pstmt = conn.prepareStatement(insertSummarySQL)) {
	                    pstmt.setDate(1, java.sql.Date.valueOf(today));
	                    pstmt.setDouble(2, averageTemperature);
	                    pstmt.setDouble(3, maxTemperature);
	                    pstmt.setDouble(4, minTemperature);
	                    pstmt.setString(5, dominantCondition);
	                    pstmt.executeUpdate();
	                    
	                    System.out.println("Daily weather summary inserted for date: " + today);
	                }
	            }
	        }

	        // Truncate the weather_data table after storing the summary
	        String truncateSQL = "TRUNCATE TABLE weather_data_new";
	        try (PreparedStatement truncateStmt = conn.prepareStatement(truncateSQL)) {
	            truncateStmt.executeUpdate();
	            System.out.println("Weather data table truncated.");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        throw e;
	    }
	}

}
