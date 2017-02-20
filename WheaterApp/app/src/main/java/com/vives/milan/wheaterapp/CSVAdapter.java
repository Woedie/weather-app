package com.vives.milan.wheaterapp;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StreamCorruptedException;

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

            String nameOfFile = "data.csv";
            String location = ctx.getExternalFilesDir(null) + "/csv/";
            File file = new File(location + nameOfFile);
            Log.d(TAG, "file location: " + file.getAbsolutePath());
            Log.d(TAG, "" + file.exists());


            InputStream in = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;

            while ((line = reader.readLine()) != null) {

                String[] RowData = line.split(",");

                Weather cur = new Weather();
                cur.setDateHour(RowData[0]);
                cur.setTemperature(Integer.parseInt(RowData[1]));

                this.add(cur);
            }

            in.close();

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
