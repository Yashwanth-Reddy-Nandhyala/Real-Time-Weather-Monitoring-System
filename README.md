# Real-Time-Weather-Monitoring-System
Overview
This is a real-time weather monitoring system that retrieves and processes weather data from the OpenWeatherMap API. The system gathers weather data from major metros in India and provides daily summaries, including temperature averages, minimums, maximums, and the dominant weather condition. Alerts are triggered if specific thresholds, such as temperature exceeding a defined limit, are breached. The system operates continuously, fetching and storing weather data every 5 minutes, and truncates old data after calculating daily summaries every 24 hours.

Key Features
Fetch real-time weather data from OpenWeatherMap API every 5 minutes.
Aggregate daily weather data, including temperature averages, min/max values, and dominant weather conditions.
Configurable alerting mechanism based on temperature thresholds.
Automatic daily data truncation and summary generation.
Stores weather data and daily summaries in an Oracle database.
System Requirements
Java Development Kit (JDK) 11+: Ensure that you have at least JDK 11 installed.
Oracle Database 11g or later: Used to store weather data and daily summaries.
Maven: For managing dependencies and building the project.
OpenWeatherMap API key: Required for fetching real-time weather data. You can sign up for a free API key here.
Build Instructions
Clone the repository:

bash
Copy code
git clone <repository-url>
cd weather-monitoring-system
Install dependencies: Ensure you have Maven installed on your system. To install dependencies, run:

bash
Copy code
mvn clean install
Database setup:

Install Oracle Database (or run it in Docker or Podman). Example Docker command:
bash
Copy code
docker run -d -p 1521:1521 --name oracledb -e ORACLE_PWD=yourpassword container-registry.oracle.com/database/express:latest
Create the required tables for the application:
sql
Copy code
CREATE TABLE IF NOT EXISTS weather_data_new (
    city VARCHAR2(50),
    temperature FLOAT,
    feels_like FLOAT,
    timestamp TIMESTAMP,
    main VARCHAR2(50)
);
CREATE TABLE IF NOT EXISTS daily_weather_summary (
    summary_date DATE PRIMARY KEY,
    average_temperature FLOAT,
    max_temperature FLOAT,
    min_temperature FLOAT,
    dominant_condition VARCHAR2(50)
);
Add your OpenWeatherMap API key:

In the WeatherDataFetcher class, add your API key:
java
Copy code
private static final String API_KEY = "your_openweathermap_api_key";
Run the application:

You can run the application directly using:
bash
Copy code
mvn exec:java -Dexec.mainClass="sourceCode.WeatherMonitoringApp"
Optional: You can also run the application in Docker or Podman:

Build the Docker image:
bash
Copy code
docker build -t weather-monitoring .
Run the container:
bash
Copy code
docker run -d weather-monitoring
Design Choices
Java and JDBC: Used for the core logic and database connectivity to Oracle.
Timer-based scheduling: The application continuously fetches weather data at fixed intervals using java.util.Timer.
Daily Aggregation: The system calculates daily weather summaries using SQL aggregate functions.
Alert Mechanism: Alerts are configurable and check whether the temperature exceeds a user-defined threshold.
Database for Persistence: Oracle Database is used to store real-time weather data and daily summaries. This decision was based on its robust transaction handling and scalability for future improvements.
Dependencies
Oracle JDBC Driver: You need to include Oracle JDBC drivers to connect to the Oracle database.
Gson: For parsing JSON responses from the OpenWeatherMap API.
JUnit 5: Used for testing.
Test Instructions
To run the unit tests, execute:

bash
Copy code
mvn test
