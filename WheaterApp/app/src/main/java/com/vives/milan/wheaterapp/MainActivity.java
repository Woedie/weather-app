package com.vives.milan.wheaterapp;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    String weatherURL = "http://www.aemet.es/es/eltiempo/observacion/ultimosdatos_2422_datos-horarios.csv?k=cle&l=2422&datos=det&w=0&f=temperatura&x=h24";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(weatherURL));
        request.setTitle("File download.");
        request.setDescription("File is being downloaded....");
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN);

//      In case you want to generate a name for the file.
//                  String nameOfFile = URLUtil.guessFileName(weatherURL, null,
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

                unregisterReceiver(this);
            }
        };

        registerReceiver(onComplete, new IntentFilter(
                DownloadManager.ACTION_DOWNLOAD_COMPLETE));

    }

    public void onGraphClick(View view) {

        Toast.makeText(this, "Graph loading.", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, graphActivity.class);
        startActivity(intent);

    }

    public void onListViewClick(View view) {

        Toast.makeText(this, "listView loading.", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, listViewActivity.class);
        startActivity(intent);

    }
}

    /* This is another, older method of downloading the file.

    public class MyTask extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... params) {

            try {
                URL myUrl = new URL(weatherURL);

                HttpURLConnection connection = (HttpURLConnection)myUrl.openConnection();
                connection.setDoOutput(true);
                connection.setRequestMethod("GET");
                connection.connect();

                String destination = Environment.getDataDirectory().toString();

                File rootDirectory = new File(Environment.getDataDirectory(), "My csv");

                Log.d(TAG, "doInBackground: " + destination);

                if(!rootDirectory.exists()){
                    rootDirectory.mkdirs();
                }

                String nameOfFile = URLUtil.guessFileName(weatherURL, null,
                        MimeTypeMap.getFileExtensionFromUrl(weatherURL));

                File file = new File(rootDirectory, "hereIam");
                file.createNewFile();

                InputStream inputStream = connection.getInputStream();

                FileOutputStream output = new FileOutputStream(file);

                byte [] buffer = new byte[1024];
                int byteCount = 0;

                while ((byteCount = inputStream.read(buffer)) > 0) {
                    output.write(buffer, 0, byteCount);
                }

                output.close();

                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                intent.setData(Uri.fromFile(file));
                getApplicationContext().sendBroadcast(intent);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(getApplicationContext(), "Completed.", Toast.LENGTH_SHORT).show();
        }
    }
*/

