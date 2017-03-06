package com.vives.milan.wheaterapp;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.opencsv.CSVReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by milan on 20-Feb-17.
 */

public class CSVAdapter extends ArrayAdapter<Weather>{

    private static final String TAG = "CSVAdapter";

    Context ctx;

    public CSVAdapter(Context context, int textViewResourceId) {

        super(context, textViewResourceId);

        this.ctx = context;

        loadArrayFromFile();
    }

    private void loadArrayFromFile(){

        try {

            CSVReader reader = new CSVReader(new FileReader(ctx.getExternalFilesDir(null)+"/csv/data.csv"));
            String[] line;
            int iteration = 0;
            while((line=reader.readNext())!=null) {
                if (!(iteration == 4)) {
                    iteration++;
                    continue;
                }
                System.out.println("Weather: Date= " + line[0] + ", Temp= " + line[1]);
                Weather cur = new Weather();
                cur.setDateHour(line[0]);
                cur.setTemperature(Float.parseFloat(line[1]));
                this.add(cur);
            }

            /* A way of reading the file without using opencsv. BUT it counts the "" with it caused problems with the floats.


            String nameOfFile = "data.csv";
            String location = ctx.getExternalFilesDir(null) + "/csv/";
            File file = new File(location + nameOfFile);
            Log.d(TAG, "file location: " + file.getAbsolutePath());
            Log.d(TAG, "" + file.exists());


            InputStream in = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            int iteration = 0;

            while ((line = reader.readLine()) != null) {

                if(!(iteration == 4)) {
                    iteration++;
                    continue;
                }

                String[] RowData = line.split(",");

                Weather cur = new Weather();
                cur.setDateHour(RowData[0]);
                cur.setTemperature(Float.parseFloat(RowData[1]));

                this.add(cur);
            }

            in.close();*/

        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public View getView(final int pos, View convertView, final ViewGroup parent) {

        TextView mView = (TextView) convertView;

        if (null == mView) {
            mView = new TextView(parent.getContext());
            mView.setTextSize(28);
        }

        mView.setText(getItem(pos).getDateHour());

        return mView;
    }
}
