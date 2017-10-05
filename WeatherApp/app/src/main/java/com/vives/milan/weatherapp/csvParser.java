package com.vives.milan.weatherapp;

import com.opencsv.CSVParser;
import com.opencsv.CSVReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by milan on 07-May-17.
 * This class is used for parsing CSV files.
 */

public class csvParser {

    private static CSVReader reader;
    private static Weather cur;
    private static String[] line;
    private static List<Weather> historyList;

    //this method reads a CSV and returns one Weather object containing the information from the CSV file
    public Weather getWeatherFromFile(String path) {
        cur = new Weather();
        try {
            //we make a reader and give along the file we want it to read, the 4 stands for the 4 lines we skip at the beginning
            reader = new CSVReader(new FileReader(path + "24hdata.csv"), CSVParser.DEFAULT_SEPARATOR, CSVParser.DEFAULT_QUOTE_CHARACTER, 4);
            String[] line; //we save each bit of information in a string, these string are collectd in a string array
            line = reader.readNext();
            if (line[1].equals("")) {
                line = reader.readNext();
            }
            cur.setTemperature(getValue(line[1])); //the first value in the string array determines the temperature
            cur.setPrecipitation(getValue(line[6]));
            cur.setWindSpeedCsv(Integer.parseInt(line[2]));
            cur.setWindDirectionCsv(line[3]);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cur; //after the line is fully read, we return the weather object
    }

    //this class reads a CSV and returns a list of Weather objects containing the information from the CSV file. One object for each hour
    public static List<Weather> getWeatherHistory(String path) {
        historyList = new ArrayList<>();

        try {
            reader = new CSVReader(new FileReader(path + "24hdata.csv"), CSVParser.DEFAULT_SEPARATOR, CSVParser.DEFAULT_QUOTE_CHARACTER, 4);

            while ((line = reader.readNext()) != null) {
                cur = new Weather();
                String [] dateHour = line[0].split(" ");
                String [] hourMinute = dateHour[1].split(":");
                cur.setHour(Integer.parseInt(hourMinute[0]));
//                cur.setTemperature(Float.parseFloat(line[1]));
//                cur.setPrecipitation(Float.parseFloat(line[6]));
//                cur.setAirPressure(Float.parseFloat(line[7]));
//                cur.setHumidity(Integer.parseInt(line[9]));
                cur.setTemperature(getValue(line[1]));
                cur.setPrecipitation(getValue(line[6]));
                cur.setAirPressure(getValue(line[7]));
                cur.setHumidity(Integer.parseInt(line[9]));
                historyList.add(cur); //when we've finished reading one line which equals one hour, we add this information to the list and the loop starts again
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return historyList; //after the file is fully read, we return the list of Weather objects
    }
    static float getValue(String value){

        float v = 0;

        if (value == ""){

            v = 0.0f; //choose an error code

        }else{

            v = Float.parseFloat(value);
        }

        return v;

    }

}
