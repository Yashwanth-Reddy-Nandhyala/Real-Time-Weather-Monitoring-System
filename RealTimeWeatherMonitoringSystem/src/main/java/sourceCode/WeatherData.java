package sourceCode;

import java.sql.Timestamp;

public class WeatherData {
    private String city;
    private double temperature; // Current temperature in Celsius
    private double feelsLike; // Perceived temperature in Celsius
    private String condition; // Main weather condition (e.g., Rain, Clear)
    private Timestamp timestamp; // Timestamp of the data retrieval
    
    public WeatherData() {
    }

    // Constructor
    public WeatherData(String city, float temperature, float feelsLike, String condition, Timestamp timestamp) {
        this.city = city;
        this.temperature = temperature;
        this.feelsLike = feelsLike;
        this.condition = condition;
        this.timestamp = timestamp;
    }

    // Getters
    public String getCity() {
        return city;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getFeelsLike() {
        return feelsLike; // This is the method you asked for
    }

    public String getCondition() {
        return condition;
    }

    public Timestamp getTimestamp() {
        return timestamp;  // Should return java.sql.Timestamp
    }
    
    public void setCity(String city) {
        this.city = city;
    }
    
    public void setTemperature(double temperature) {
        this.temperature =  temperature;
    }
    
    public void setCondition(String condition) {
        this.condition = condition;
    }
    
    public void setFeelsLike(double feelsLike) {
        this.feelsLike =  feelsLike;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = (Timestamp) timestamp;
    }
}
