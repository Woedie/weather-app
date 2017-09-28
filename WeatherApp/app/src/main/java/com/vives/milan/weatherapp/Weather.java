package com.vives.milan.weatherapp;

/**
 * Created by milan on 07-May-17.
 * This class is used to hold all of the information concerning the weather status.
 */

public class Weather {

    private int hour;

    private float temperature;
    private String maxTemp;
    private String minTemp;
    private String temp06;
    private String temp12;
    private String temp18;
    private String temp00;

    private int windSpeedCsv;
    private String windDirectionCsv;
    private String windDirDay;
    private String windDir0006;
    private String windDir0612;
    private String windDir1218;
    private String windDir1824;
    private String windSpeedDay;
    private String windSpeed0006;
    private String windSpeed0612;
    private String windSpeed1218;
    private String windSpeed1824;

    private String rainProbDay;
    private String rainProb0006;
    private String rainProb0612;
    private String rainProb1218;
    private String rainProb1824;

    private String skyDay;
    private String sky0006;
    private String sky0612;
    private String sky1218;
    private String sky1824;

    private float precipitation;
    private int humidity;
    private float airPressure;

    // these getters and setters make the data from this class available through the entire application
    public void setHour(int hour) {
        this.hour = hour;
    }

    public String getWindDirDay() {
        return windDirDay;
    }

    public void setWindDirDay(String windDirDay) {
        this.windDirDay = windDirDay;
    }

    public String getWindDir0006() {
        return windDir0006;
    }

    public void setWindDir0006(String windDir0006) {
        this.windDir0006 = windDir0006;
    }

    public String getWindDir0612() {
        return windDir0612;
    }

    public void setWindDir0612(String windDir0612) {
        this.windDir0612 = windDir0612;
    }

    public String getWindDir1218() {
        return windDir1218;
    }

    public void setWindDir1218(String windDir1218) {
        this.windDir1218 = windDir1218;
    }

    public String getWindDir1824() {
        return windDir1824;
    }

    public void setWindDir1824(String windDir1824) {
        this.windDir1824 = windDir1824;
    }

    public String getWindSpeedDay() {
        return windSpeedDay;
    }

    public void setWindSpeedDay(String windSpeedDay) {
        this.windSpeedDay = windSpeedDay;
    }

    public String getWindSpeed0006() {
        return windSpeed0006;
    }

    public void setWindSpeed0006(String windSpeed0006) {
        this.windSpeed0006 = windSpeed0006;
    }

    public String getWindSpeed0612() {
        return windSpeed0612;
    }

    public void setWindSpeed0612(String windSpeed0612) {
        this.windSpeed0612 = windSpeed0612;
    }

    public String getWindSpeed1218() {
        return windSpeed1218;
    }

    public void setWindSpeed1218(String windSpeed1218) {
        this.windSpeed1218 = windSpeed1218;
    }

    public String getWindSpeed1824() {
        return windSpeed1824;
    }

    public void setWindSpeed1824(String windSpeed1824) {
        this.windSpeed1824 = windSpeed1824;
    }

    public String getTemp06() {
        return temp06;
    }

    public void setTemp06(String temp06) {
        this.temp06 = temp06;
    }

    public String getTemp12() {
        return temp12;
    }

    public void setTemp12(String temp12) {
        this.temp12 = temp12;
    }

    public String getTemp18() {
        return temp18;
    }

    public void setTemp18(String temp18) {
        this.temp18 = temp18;
    }

    public String getTemp00() {
        return temp00;
    }

    public void setTemp00(String temp00) {
        this.temp00 = temp00;
    }

    public String getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(String maxTemp) {
        this.maxTemp = maxTemp;
    }

    public String getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(String minTemp) {
        this.minTemp = minTemp;
    }

    public String getRainProbDay() {
        return rainProbDay;
    }

    public void setRainProbDay(String rainProbDay) {
        this.rainProbDay = rainProbDay;
    }

    public String getRainProb0006() {
        return rainProb0006;
    }

    public void setRainProb0006(String rainProb0006) {
        this.rainProb0006 = rainProb0006;
    }

    public String getRainProb0612() {
        return rainProb0612;
    }

    public void setRainProb0612(String rainProb0612) {
        this.rainProb0612 = rainProb0612;
    }

    public String getRainProb1218() {
        return rainProb1218;
    }

    public void setRainProb1218(String rainProb1218) {
        this.rainProb1218 = rainProb1218;
    }

    public String getRainProb1824() {
        return rainProb1824;
    }

    public void setRainProb1824(String rainProb1824) {
        this.rainProb1824 = rainProb1824;
    }

    public String getSkyDay() {
        return skyDay;
    }

    public void setSkyDay(String skyDay) {
        this.skyDay = skyDay;
    }

    public String getSky0006() {
        return sky0006;
    }

    public void setSky0006(String sky0006) {
        this.sky0006 = sky0006;
    }

    public String getSky0612() {
        return sky0612;
    }

    public void setSky0612(String sky0612) {
        this.sky0612 = sky0612;
    }

    public String getSky1218() {
        return sky1218;
    }

    public void setSky1218(String sky1218) {
        this.sky1218 = sky1218;
    }

    public String getSky1824() {
        return sky1824;
    }

    public void setSky1824(String sky1824) {
        this.sky1824 = sky1824;
    }

    public int getWindSpeedCsv() {
        return windSpeedCsv;
    }

    public void setWindSpeedCsv(int windSpeedCsv) {
        this.windSpeedCsv = windSpeedCsv;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public String getWindDirectionCsv() {
        return windDirectionCsv;
    }

    public void setWindDirectionCsv(String windDirectionCsv) {this.windDirectionCsv = windDirectionCsv;}

    public float getPrecipitation() {
        return precipitation;
    }

    public void setPrecipitation(float precipitation) {
        this.precipitation = precipitation;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public float getAirPressure() {
        return airPressure;
    }

    public void setAirPressure(float airPressure) {
        this.airPressure = airPressure;
    }
}

