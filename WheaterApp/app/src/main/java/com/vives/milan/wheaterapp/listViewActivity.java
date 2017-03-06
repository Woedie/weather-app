package com.vives.milan.wheaterapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class listViewActivity extends AppCompatActivity {

    CSVAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        ListView mList = (ListView)findViewById(R.id.theList);

        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                Toast.makeText(view.getContext(), Float.toString(mAdapter.getItem(pos).getTemperature()) + "°C", Toast.LENGTH_SHORT).show();
            }
        });

        mAdapter = new CSVAdapter(this, -1);

        mList.setAdapter(mAdapter);
    }
}
