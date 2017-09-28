package com.vives.milan.weatherapp;

/**
 * Created by milan on 15-May-17.
 * This class contains the logic behind the charts activity.
 */

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class graphActivity extends AppCompatActivity {

    private Calendar c = Calendar.getInstance();
    private static String mainPath;
    private String[] hLabels;
    private List<Weather> historyList;
    private List<DataPoint[]> collection;
    private int size;

    private LineGraphSeries<DataPoint> tempSeries;
    private LineGraphSeries<DataPoint> humidSeries;
    private BarGraphSeries<DataPoint> rainSeries;
    private LineGraphSeries<DataPoint> pressSeries;

    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        mainPath = getExternalFilesDir(null) + "/";

        historyList = csvParser.getWeatherHistory(mainPath); //read the CSV and save the data in a list

        GraphView tempGraph = (GraphView) findViewById(R.id.tempGraph);
        GraphView rainGraph = (GraphView) findViewById(R.id.rainGraph);
        GraphView airGraph = (GraphView) findViewById(R.id.airGraph);

        TextView sourceView = (TextView) findViewById(R.id.sourceView);

        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        sourceView.setText(this.getResources().getString(R.string.aemetSource) + " " + sharedPref.getString("lastDownloaded", "Data unavailable."));

        size = historyList.size();
        hLabels = new String[size]; //we manually set our x-labels

        collection = getPoints(historyList, size, hLabels); //the list of weather objects is converted to sets of Datapoints in order to fill the charts

        tempSeries = new LineGraphSeries<>(collection.get(0));
        humidSeries = new LineGraphSeries<>(collection.get(1));
        rainSeries = new BarGraphSeries<>(collection.get(2));
        pressSeries = new LineGraphSeries<>(collection.get(3));

        //determine setting for the temperature chart
        tempGraph.setTitle(getString(R.string.tempGraph));
        tempGraph.setTitleTextSize(70);
        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(tempGraph);
        staticLabelsFormatter.setHorizontalLabels(hLabels);
        tempGraph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
        tempSeries.setDrawDataPoints(true);
        tempSeries.setTitle(getString(R.string.tempLegend));
        tempGraph.getLegendRenderer().setVisible(true);
        tempGraph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
        tempGraph.addSeries(tempSeries);

        //determine settings for the rain/humidity chart
        rainGraph.setTitle(getString(R.string.rainGraph));
        rainGraph.setTitleTextSize(70);
        staticLabelsFormatter = new StaticLabelsFormatter(rainGraph);
        staticLabelsFormatter.setHorizontalLabels(hLabels);
        rainGraph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
        rainSeries.setSpacing(50);
        rainSeries.setDrawValuesOnTop(true);
        rainSeries.setValuesOnTopColor(Color.RED);
        rainSeries.setTitle(getString(R.string.rainLegend));
        rainSeries.setColor(Color.RED);
        humidSeries.setTitle(getString(R.string.humidLegend));
        rainGraph.getLegendRenderer().setVisible(true);
        rainGraph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
        rainGraph.getViewport().setMinY(0);
        rainGraph.getViewport().setMaxY(100);
        rainGraph.getViewport().setYAxisBoundsManual(true);
        rainGraph.addSeries(rainSeries);
        rainGraph.addSeries(humidSeries);

        //determine setting for the atmospheric pressure chart
        airGraph.setTitle(getString(R.string.airGraph));
        airGraph.setTitleTextSize(70);
        staticLabelsFormatter = new StaticLabelsFormatter(airGraph);
        staticLabelsFormatter.setHorizontalLabels(hLabels);
        airGraph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
        pressSeries.setDrawBackground(true);
        pressSeries.setTitle(getString(R.string.airLegend));
        airGraph.getLegendRenderer().setVisible(true);
        airGraph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
        airGraph.addSeries(pressSeries);


    }

    //this method converts the information from the historyList into multiple collections of DataPoints
    private List<DataPoint[]> getPoints(List<Weather> historyList, int size, String[] hLabels) {
        List<DataPoint[]> collection = new ArrayList<>();
        DataPoint[] tempPoints = new DataPoint[size]; //each graph has its own collection of DataPoints
        DataPoint[] humidPoints = new DataPoint[size];
        DataPoint[] rainPoints = new DataPoint[size];
        DataPoint[] pressPoints = new DataPoint[size];
        SimpleDateFormat sdf = new SimpleDateFormat("H");

        for(int i=0; i<size; i++) {
            Date date = c.getTime();
            String hlabel = sdf.format(date);
            if(i % 4 == 0) {hLabels[size - 1 - i] = hlabel + ":00";} //we fill in our x-labels
            tempPoints[size - 1 - i] = new DataPoint(size - 1 - i, historyList.get(i).getTemperature()); //add the DataPoints to the correct series for each graph
            humidPoints[size - 1 - i] = new DataPoint(size - 1 - i, historyList.get(i).getHumidity());
            rainPoints[size - 1 - i] = new DataPoint(size - 1 - i, historyList.get(i).getPrecipitation());
            pressPoints[size - 1 - i] = new DataPoint(size - 1 - i, historyList.get(i).getAirPressure());
            c.add(Calendar.HOUR_OF_DAY, -1);
        }
        collection.add(tempPoints); //add each series to the collection
        collection.add(humidPoints);
        collection.add(rainPoints);
        collection.add(pressPoints);
        return collection;
    }
}
