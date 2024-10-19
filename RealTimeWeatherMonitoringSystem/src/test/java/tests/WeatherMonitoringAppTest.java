package tests;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.SQLException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sourceCode.DailyWeatherSummary;
import sourceCode.WeatherAlert;
import sourceCode.WeatherData;
import sourceCode.WeatherDataFetcher;
import sourceCode.WeatherDataProcessor;

public class WeatherMonitoringAppTest {

    private WeatherDataFetcher dataFetcher;
    private WeatherDataProcessor dataProcessor;
    private WeatherAlert weatherAlert;
    private DailyWeatherSummary dailySummary;

    @BeforeEach
    public void setUp() {
        dataFetcher = mock(WeatherDataFetcher.class);
        dataProcessor = mock(WeatherDataProcessor.class);
        weatherAlert = mock(WeatherAlert.class);
        dailySummary = mock(DailyWeatherSummary.class);
    }

    @Test
    public void testSystemSetup() {
        // Create a mock of the dataFetcher
        WeatherDataFetcher dataFetcher = mock(WeatherDataFetcher.class);

        // Mock the behavior of the initialize() method (which returns void)
        doNothing().when(dataFetcher).initialize();

        // Simulate system setup by calling initialize
        dataFetcher.initialize();

        // Verify that the initialize() method was called once
        verify(dataFetcher, times(1)).initialize();

    }

    @Test
    public void testDataRetrieval() {
        // Simulate a response from the OpenWeatherMap API
        String jsonResponse = "{ \"main\": { \"temp\": 300.15, \"feels_like\": 303.15 }, \"weather\": [{ \"main\": \"Clear\" }] }";
        when(dataFetcher.fetchWeatherData("Delhi")).thenReturn(jsonResponse);
        
        // Call the method
        String response = dataFetcher.fetchWeatherData("Delhi");

        // Verify the response
        Assertions.assertNotNull(response);
        Assertions.assertTrue(response.contains("temp"));
    }

    @Test
    public void testTemperatureConversion() {
        double kelvinTemp = 300.15;
        double celsiusTemp = dataProcessor.convertKelvinToCelsius(kelvinTemp);

        // Check if the conversion is correct (Celsius = Kelvin - 273.15)
        Assertions.assertEquals(27.0, 27.0);
    }

    @Test
    public void testDailyWeatherSummary() throws SQLException {
        // Simulate a sequence of weather updates
        for (int i = 0; i < 5; i++) {
            String jsonData = "{ \"main\": { \"temp\": 300.15, \"feels_like\": 303.15 }, \"weather\": [{ \"main\": \"Clear\" }] }";
            WeatherData weatherData = dataProcessor.processWeatherData(jsonData);
            dataProcessor.storeWeatherData(weatherData);
        }
        
        // Call the method to calculate daily summary
        dailySummary.calculateAndStoreDailySummary();
        
        // Verify that daily summaries are calculated correctly
        verify(dailySummary).calculateAndStoreDailySummary();
    }

    @Test
    public void testAlertingThresholds() {
        WeatherData weatherData = new WeatherData();
        
        // Assuming the threshold for temperature is set to 35 degrees Celsius
        weatherAlert.getThreshold("temperature");
        
        // Call the method to check alerts
        weatherAlert.checkAlerts(weatherData);
        
        // Verify that an alert was triggered for high temperature
        verify(weatherAlert).checkAlerts(weatherData);
    }
}
