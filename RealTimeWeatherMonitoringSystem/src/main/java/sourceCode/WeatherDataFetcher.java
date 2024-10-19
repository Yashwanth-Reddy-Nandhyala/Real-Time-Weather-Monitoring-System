package sourceCode;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;

public class WeatherDataFetcher {
    public static final String API_KEY = "f9e337d6907a7c14ad7e04de9a9ca175";
    public static final String[] CITIES = {"Delhi", "Mumbai", "Chennai", "Bangalore", "Kolkata", "Hyderabad"};

    public String fetchWeatherData(String city) {
        try {
            String urlString = String.format("http://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s", city, API_KEY);
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public void initialize() {
        // Initialization logic here, such as database connection or setup configurations
        try {
            Connection conn = DatabaseConnection.getConnection();
            System.out.println("System successfully initialized. Database connection established.");
            
        } catch (SQLException e) {
            System.err.println("Failed to initialize the system.");
            e.printStackTrace();
        }
		
    }
    
   

}
