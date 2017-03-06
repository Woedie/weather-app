package com.vives.milan.wheaterapp;

/**
 * Created by milan on 20-Feb-17.
 */

public class Weather {

    private String dateHour;
    private float temperature;
    private int windSpeed;
    private String windDirection;
    private int gustSpeed;
    private String gustDirection;
    private int precipitation;
    private float pressure;
    private float trend;
    private int humidity;

    public int getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(int windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getDateHour() {
        return dateHour;
    }

    public void setDateHour(String dateHour) {
        this.dateHour = dateHour;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    public int getGustSpeed() {
        return gustSpeed;
    }

    public void setGustSpeed(int gustSpeed) {
        this.gustSpeed = gustSpeed;
    }

    public String getGustDirection() {
        return gustDirection;
    }

    public void setGustDirection(String gustDirection) {
        this.gustDirection = gustDirection;
    }

    public int getPrecipitation() {
        return precipitation;
    }

    public void setPrecipitation(int precipitation) {
        this.precipitation = precipitation;
    }

    public float getPressure() {
        return pressure;
    }

    public void setPressure(float pressure) {
        this.pressure = pressure;
    }

    public float getTrend() {
        return trend;
    }

    public void setTrend(float trend) {
        this.trend = trend;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }


}
