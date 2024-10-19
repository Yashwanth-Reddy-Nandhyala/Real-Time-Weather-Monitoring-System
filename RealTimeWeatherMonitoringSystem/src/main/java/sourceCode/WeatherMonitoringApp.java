package sourceCode;

import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;

public class WeatherMonitoringApp {
    public static void main(String[] args) {
        WeatherDataFetcher dataFetcher = new WeatherDataFetcher();
        WeatherDataProcessor dataProcessor = new WeatherDataProcessor();
        WeatherAlert weatherAlert = new WeatherAlert();
        DailyWeatherSummary dailySummary = new DailyWeatherSummary(); // Instance for daily summary calculation


        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                for (String city : WeatherDataFetcher.CITIES) {
                    String jsonData = dataFetcher.fetchWeatherData(city);
                    if (!jsonData.isEmpty()) {
                        WeatherData weatherData = dataProcessor.processWeatherData(jsonData);

                        try {
                            dataProcessor.storeWeatherData(weatherData); // Store new data
                            dataProcessor.printLastRecord(city); // Print last inserted record
                            weatherAlert.checkAlerts(weatherData); // Check alerts
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, 0, 5 * 60 * 1000); // Every 5 minutes
        
        // Timer for calculating daily summary every 24 hours
        Timer summaryTimer = new Timer();
        summaryTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    dailySummary.calculateAndStoreDailySummary();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }, 0, 24 * 60 * 60 * 1000); // Every 24 hours
    
    }
}
