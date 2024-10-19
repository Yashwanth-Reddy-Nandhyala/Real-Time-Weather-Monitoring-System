package sourceCode;

public class WeatherAlert {
    private static final double TEMP_THRESHOLD = 35.0; // Example threshold for alerts
    private double lastTemperature = 0;
    
    private static final double DEFAULT_TEMPERATURE_THRESHOLD = 35.0; // Celsius
    private static final double DEFAULT_FEELS_LIKE_THRESHOLD = 40.0;  // Celsius

    // Method to retrieve a temperature threshold (can be further customized to retrieve other types of thresholds)
    public double getThreshold(String thresholdType) {
        if ("temperature".equalsIgnoreCase(thresholdType)) {
            return DEFAULT_TEMPERATURE_THRESHOLD;
        } else if ("feels_like".equalsIgnoreCase(thresholdType)) {
            return DEFAULT_FEELS_LIKE_THRESHOLD;
        } else {
            throw new IllegalArgumentException("Unknown threshold type: " + thresholdType);
        }
    }

    public void checkAlerts(WeatherData weatherData) {
        if (weatherData.getTemperature() > TEMP_THRESHOLD && lastTemperature > TEMP_THRESHOLD) {
            System.out.println("Alert: High temperature in " + weatherData.getCity() + ": " + weatherData.getTemperature() + "°C");
        }
        lastTemperature = weatherData.getTemperature();
    }
    
}
