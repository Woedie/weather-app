package com.vives.milan.wheaterapp;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;
import com.opencsv.CSVReader;
import java.io.FileReader;
import java.io.IOException;

public class graphActivity extends AppCompatActivity {

    String weatherURL = "http://www.aemet.es/es/eltiempo/observacion/ultimosdatos_2422_datos-horarios.csv?k=cle&l=2422&datos=det&w=0&f=temperatura&x=h24";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        GraphView graph = (GraphView) findViewById(R.id.graph);

        try {
            CSVReader reader = new CSVReader(new FileReader(getExternalFilesDir(null)+"/csv/data.csv"));
            String[] line;
            int iteration = 0;
            int count = 23;
            DataPoint[] points = new DataPoint[24];
            while((line=reader.readNext())!=null) {
                if (!(iteration == 4)) {
                    iteration++;
                    continue;
                }
                System.out.println("Weather: Date= " + line[0] + ", Temp= " + line[1]);
                Weather cur = new Weather();
                cur.setDateHour(line[0]);
                cur.setTemperature(Float.parseFloat(line[1]));
                System.out.println("WeatherClass: Date = " + cur.getDateHour() + ", Temp = " + cur.getTemperature());
                points[count] = new DataPoint(count, cur.getTemperature());
                System.out.println("Points: "+ count + ", " + cur.getTemperature());
                count--;
            }
            LineGraphSeries<DataPoint> series = new LineGraphSeries<>(points);
            series.setDrawDataPoints(true);
            series.setDrawBackground(true);
            series.setOnDataPointTapListener(new OnDataPointTapListener() {
                @Override
                public void onTap(Series series, DataPointInterface dataPoint) {
                    Toast.makeText(graphActivity.this, "" + dataPoint, Toast.LENGTH_SHORT).show();
                }
            });


            graph.getViewport().setXAxisBoundsManual(true);
            graph.getViewport().setMinX(0);
            graph.getViewport().setMaxX(23);

            // enable scaling and scrolling
            graph.getViewport().setScalable(true);
            graph.getViewport().setScalableY(true);
            graph.setTitle("Temperature");
            graph.addSeries(series);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
