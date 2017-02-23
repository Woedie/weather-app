package com.vives.milan.wheaterapp;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    String weatherURL = "http://www.aemet.es/es/eltiempo/observacion/ultimosdatos_2422_datos-horarios.csv?k=cle&l=2422&datos=det&w=0&f=temperatura&x=h24";
    //String weatherURL = "https://www.w3schools.com/css/trolltunga.jpg";

    CSVAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Button btnTrad = (Button) findViewById(R.id.btnTrad);
        Button btnDM = (Button) findViewById(R.id.btnDM);
        final ListView mList = (ListView)findViewById(R.id.theList);

//        btnTrad.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//                new MyTask().execute();
//            }
//        });

        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                Toast.makeText(view.getContext(), mAdapter.getItem(pos).getTemperature(), Toast.LENGTH_SHORT).show();
            }
        });

        btnDM.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(weatherURL));
                request.setTitle("File download.");
                request.setDescription("File is being downloaded....");
                request.allowScanningByMediaScanner();
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN);

//                String nameOfFile = URLUtil.guessFileName(weatherURL, null,
//                        MimeTypeMap.getFileExtensionFromUrl(weatherURL));

                String nameOfFile = "data.csv";
                String location = getExternalFilesDir(null) + "/csv/";
                final File file = new File(location + nameOfFile);

                Log.d(TAG, "file location: " + location);
                Log.d(TAG, file.getName());
                Log.d(TAG, "" + file.exists());

                if (file.exists()) {
                    file.delete();
                    Log.d(TAG, "file deleted.");
                }

                request.setDestinationInExternalFilesDir(MainActivity.this, "/csv", nameOfFile);

                DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                manager.enqueue(request);

                BroadcastReceiver onComplete = new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        Log.d(TAG, "file downloaded.");
                        Log.d(TAG, "After download:" + file.exists());

                        mAdapter = new CSVAdapter(MainActivity.this, -1);

                        mList.setAdapter(mAdapter);

                        unregisterReceiver(this);
                    }
                };

                registerReceiver(onComplete, new IntentFilter(
                        DownloadManager.ACTION_DOWNLOAD_COMPLETE));

            }
        });
    }

//    public class MyTask extends AsyncTask<Void, Void, Void> {
//
//
//        @Override
//        protected Void doInBackground(Void... params) {
//
//            try {
//                URL myUrl = new URL(weatherURL);
//
//                HttpURLConnection connection = (HttpURLConnection)myUrl.openConnection();
//                connection.setDoOutput(true);
//                connection.setRequestMethod("GET");
//                connection.connect();
//
//                String destination = Environment.getDataDirectory().toString();
//
//                File rootDirectory = new File(Environment.getDataDirectory(), "My csv");
//
//                Log.d(TAG, "doInBackground: " + destination);
//
//                if(!rootDirectory.exists()){
//                    rootDirectory.mkdirs();
//                }
//
//                String nameOfFile = URLUtil.guessFileName(weatherURL, null,
//                        MimeTypeMap.getFileExtensionFromUrl(weatherURL));
//
//                File file = new File(rootDirectory, "hereIam");
//                file.createNewFile();
//
//                InputStream inputStream = connection.getInputStream();
//
//                FileOutputStream output = new FileOutputStream(file);
//
//                byte [] buffer = new byte[1024];
//                int byteCount = 0;
//
//                while ((byteCount = inputStream.read(buffer)) > 0) {
//                    output.write(buffer, 0, byteCount);
//                }
//
//                output.close();
//
//                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//                intent.setData(Uri.fromFile(file));
//                getApplicationContext().sendBroadcast(intent);
//
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
//            Toast.makeText(getApplicationContext(), "Completed.", Toast.LENGTH_SHORT).show();
//        }
//    }
}

