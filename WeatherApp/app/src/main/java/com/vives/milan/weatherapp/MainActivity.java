package com.vives.milan.weatherapp;

/**
 * Created by milan on 15-April-17.
 * This class is called when the application is booted, it downloads the files and initializes the different fragments.
 */

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends FragmentActivity {

    private int downloadsDone = 0;
    private String defaultCsv = "http://www.aemet.es/es/eltiempo/observacion/ultimosdatos_2422_datos-horarios.csv?k=cle&l=2422&datos=det&w=0&f=temperatura&x=h24";
    private String defaultXml = "http://www.aemet.es/xml/municipios/localidad_47186.xml";
    private String pollenURL = "http://opendata.jcyl.es/ficheros/inpo/polen_actual.xml";
    private String defaultLocation = "Valladolid";
    private long csvReference;
    private long predReference;
    private long pollenReference;
    private BroadcastReceiver onComplete = null;
    private ViewPager pager;
    private SharedPreferences sharedPref;
    private String mainPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        mainPath = getExternalFilesDir(null) + "/";

        sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        pager = (ViewPager) findViewById(R.id.viewPager); // the pager is responsible for managing the layout of each fragment

        // check whether there is a valid internet connection available
        if(checkConnection()) {

            String csvURL = sharedPref.getString("csvURL", defaultCsv); // we load the URL from the shared preferences based on the desired location
            String xmlURL = sharedPref.getString("xmlURL", defaultXml);

            downloader(mainPath, csvURL, "24hdata.csv", "csv"); // initialize a downloader for each file
            downloader(mainPath, xmlURL, "predictions.xml", "predictions");
            downloader(mainPath, pollenURL, "pollen.xml", "pollen");

            // the receiver collects the broadcasts noting that a download has finished
            onComplete = new BroadcastReceiver() {

                @Override
                public void onReceive(Context context, Intent intent) {

                    //check if the broadcast message is for our Enqueued download
                    long referenceId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                    if (csvReference == referenceId) {
                        startUp(onComplete);
                    } else if (predReference == referenceId){
                        startUp(onComplete);
                    } else if (pollenReference == referenceId){
                        startUp(onComplete);
                    }
                }
            };


            registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

        }
        else {
            // if no connection is available use already available data
            Toast.makeText(this, "No internet connection, using known data.", Toast.LENGTH_SHORT).show();
            pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        }

    }

    // after each download this method is called, when all downloads have finished the fragments can be built
    private void startUp(BroadcastReceiver onComplete) {
        if (downloadsDone == 2){
            pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
            downloadsDone = 0;
            pager.getAdapter().notifyDataSetChanged();
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("lastDownloaded", setDownloadDate());
            editor.commit(); // commit changes
            unregisterReceiver(onComplete);
        }
        else {downloadsDone++;}
    }

    private String setDownloadDate() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("MMMM d '@' HH:mm");
        return format.format(c.getTime());
    }

    // this method check whether there is a valid internet connection available
    private boolean checkConnection() {

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        if (activeNetwork != null) {
            //we are connected to a network
            return true;
        } else
            return false;
    }

    // this method handles each download and saves the id's in order for the receiver to know which download has finished
    private void downloader(final String mainPath, String URL, String fileName, String fileType) {

        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(URL));
        request.setTitle("File download.");
        request.setDescription("File is being downloaded....");
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN);

        final File file = new File(mainPath + fileName);

        if (file.exists()) {
            file.delete(); // if the file already exists, delete it and update it with a new one in order to save place
        }

        request.setDestinationInExternalFilesDir(MainActivity.this, "", fileName);

        // enqueue each download and save its id
        DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        if (fileType.equals("csv")) {
            csvReference = manager.enqueue(request);
        } else if (fileType.equals("predictions")) {
            predReference = manager.enqueue(request);
        } else if (fileType.equals("pollen")) {
            pollenReference = manager.enqueue(request);
        }
    }

    // while the viewpager manages the layouts of each fragment, the adapter manages the handling of the fragments
    private class MyPagerAdapter extends FragmentStatePagerAdapter {

        List<Weather> weatherlist = new xmlParser().getWeatherListFromFile(mainPath); // parse our files and pass along the necessary information to each fragment

        Weather csvWeather = new csvParser().getWeatherFromFile(mainPath);

        String location = sharedPref.getString("location", defaultLocation); // read the current location from the shared preferences

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int pos) {
            switch(pos) {

                // initialize each fragment and passa long the necessary information
                case 0: return FirstFragment.newInstance(weatherlist.get(0), csvWeather, location);
                case 1: return SecondFragment.newInstance(weatherlist.get(1), location);
                case 2: return ThirdFragment.newInstance(weatherlist.get(2), 2, location);
                case 3: return ThirdFragment.newInstance(weatherlist.get(3), 3, location);
                case 4: return ThirdFragment.newInstance(weatherlist.get(4), 4, location);
                case 5: return ThirdFragment.newInstance(weatherlist.get(5), 5, location);
                case 6: return ThirdFragment.newInstance(weatherlist.get(6), 6, location);
                default: return ThirdFragment.newInstance(weatherlist.get(5), 5, location);
        }
        }

        @Override
        public int getCount() {
            return 7;
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public void restoreState(Parcelable state, ClassLoader loader) {

        }
    }
}