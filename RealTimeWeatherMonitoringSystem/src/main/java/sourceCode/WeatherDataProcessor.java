package sourceCode;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WeatherDataProcessor {

    public WeatherData processWeatherData(String jsonData) {
        WeatherData weatherData = new WeatherData();

        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONObject main = jsonObject.getJSONObject("main");

            double temp = main.getDouble("temp") - 273.15; // Convert from Kelvin to Celsius
            double feelsLike = main.getDouble("feels_like") - 273.15;

            weatherData.setCity(jsonObject.getString("name"));
            weatherData.setTemperature(temp);
            weatherData.setFeelsLike(feelsLike);
            weatherData.setTimestamp(new java.sql.Timestamp(System.currentTimeMillis()));

            // Extract other fields such as weather condition, if required
            JSONArray weatherArray = jsonObject.getJSONArray("weather");
            String mainCondition = weatherArray.getJSONObject(0).getString("main");
            weatherData.setCondition(mainCondition);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return weatherData;
    }
    
    
    public void storeWeatherData(WeatherData weatherData) throws SQLException {
        String insertSQL = "INSERT INTO weather_data_new (city, temperature, feels_like, condition, timestamp) "
                         + "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {

            pstmt.setString(1, weatherData.getCity());
            pstmt.setDouble(2, weatherData.getTemperature());
            pstmt.setDouble(3, weatherData.getFeelsLike());
            pstmt.setString(4, weatherData.getCondition());
            pstmt.setTimestamp(5, weatherData.getTimestamp());

            pstmt.executeUpdate();
        }
    }
    
    
    
    public void printLastRecord(String city) throws SQLException {
        String selectSQL = "SELECT * FROM weather_data_new WHERE city = ? ORDER BY timestamp DESC FETCH FIRST 1 ROWS ONLY";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(selectSQL)) {
            pstmt.setString(1, city);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    System.out.println("City: " + rs.getString("city"));
                    System.out.println("Temperature: " + rs.getDouble("temperature"));
                    System.out.println("Feels Like: " + rs.getDouble("feels_like"));
                    System.out.println("Condition: " + rs.getString("condition"));
                    System.out.println("Timestamp: " + rs.getTimestamp("timestamp"));
                }
            }
        }
    }

    public double convertKelvinToCelsius(double kelvinTemperature) {
        // Kelvin to Celsius conversion
        return kelvinTemperature - 273.15;
    }

}



    
    

	

