package com.vives.milan.weatherapp;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by milan on 07-May-17.
 * This class contains the logic behind the second fragment.
 */

public class SecondFragment extends Fragment {

    private SharedPreferences sharedPref;

    // the next method makes an instance of the fragment while saving all the necessary arguments
    public static SecondFragment newInstance(Weather predWeather, String location) {

        SecondFragment f = new SecondFragment();
        Bundle args = new Bundle(); // the arguments are saved in a Bundle object, which operates as a key-value pair

        args.putString("sky0006", predWeather.getSky0006());
        args.putString("sky0612", predWeather.getSky0612());
        args.putString("sky1218", predWeather.getSky1218());
        args.putString("sky1824", predWeather.getSky1824());

        args.putString("skyDay", predWeather.getSkyDay());

        args.putString("speedDay", predWeather.getWindSpeedDay());
        args.putString("dirDay", predWeather.getWindDirDay());

        args.putString("location", location);

        args.putString("maxTemp", predWeather.getMaxTemp());
        args.putString("minTemp", predWeather.getMinTemp());

        args.putString("temp00", predWeather.getTemp00());
        args.putString("temp06", predWeather.getTemp06());
        args.putString("temp12", predWeather.getTemp12());
        args.putString("temp18", predWeather.getTemp18());

        args.putString("rainDay", predWeather.getRainProbDay());

        args.putString("rainProb0006", predWeather.getRainProb0006());
        args.putString("rainProb0612", predWeather.getRainProb0612());
        args.putString("rainProb1218", predWeather.getRainProb1218());
        args.putString("rainProb1824", predWeather.getRainProb1824());

        args.putString("windDir00", predWeather.getWindDir0006());
        args.putString("windDir06", predWeather.getWindDir0612());
        args.putString("windDir12", predWeather.getWindDir1218());
        args.putString("windDir18", predWeather.getWindDir1824());

        args.putString("windSpeed00", predWeather.getWindSpeed0006());
        args.putString("windSpeed06", predWeather.getWindSpeed0612());
        args.putString("windSpeed12", predWeather.getWindSpeed1218());
        args.putString("windSpeed18", predWeather.getWindSpeed1824());
        f.setArguments(args);

        return f;
    }

    // in order to create the view for the fragment, we run the next method. Here we can call on the arguments set in the previous method
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.first_frag, container, false);

        TextView maxTempView = (TextView) v.findViewById(R.id.maxTempView);
        TextView minTempView = (TextView) v.findViewById(R.id.minTempView);

        TextView tempView00 = (TextView) v.findViewById(R.id.tempView00);
        TextView tempView06 = (TextView) v.findViewById(R.id.tempViewDay);
        TextView tempView12 = (TextView) v.findViewById(R.id.tempView12);
        TextView tempView18 = (TextView) v.findViewById(R.id.tempView18);

        TextView rainView00 = (TextView) v.findViewById(R.id.rainView00);
        TextView rainView06 = (TextView) v.findViewById(R.id.rainView06);
        TextView rainView12 = (TextView) v.findViewById(R.id.rainView12);
        TextView rainView18 = (TextView) v.findViewById(R.id.rainView18);

        TextView windView00 = (TextView) v.findViewById(R.id.windView00);
        TextView windView06 = (TextView) v.findViewById(R.id.windView06);
        TextView windView12 = (TextView) v.findViewById(R.id.windView12);
        TextView windView18 = (TextView) v.findViewById(R.id.windView18);

        TextView tempView = (TextView) v.findViewById(R.id.temperatureView);
        TextView dateView = (TextView) v.findViewById(R.id.dateView);
        TextView locationView = (TextView) v.findViewById(R.id.locationView);
        TextView speedDayView = (TextView) v.findViewById(R.id.speedView);

        TextView sourceView = (TextView) v.findViewById(R.id.sourceView);

        ImageView dirDayImage = (ImageView) v.findViewById(R.id.directionImage);

        ImageView iconSky00 = (ImageView) v.findViewById(R.id.image00);
        ImageView iconSky06 = (ImageView) v.findViewById(R.id.image06);
        ImageView iconSky12 = (ImageView) v.findViewById(R.id.image12);
        ImageView iconSky18 = (ImageView) v.findViewById(R.id.image18);

        ImageView windDir00 = (ImageView) v.findViewById(R.id.windDir00);
        ImageView windDir06 = (ImageView) v.findViewById(R.id.windDir06);
        ImageView windDir12 = (ImageView) v.findViewById(R.id.windDir12);
        ImageView windDir18 = (ImageView) v.findViewById(R.id.windDir18);

        ImageView tempIcon = (ImageView) v.findViewById(R.id.tempIcon);
        ImageView rainIcon = (ImageView) v.findViewById(R.id.rainIcon);
        ImageView windIcon = (ImageView) v.findViewById(R.id.windIcon);

        tempIcon.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.temp_icon, null));
        rainIcon.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.rain_icon, null));
        windIcon.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.wind_icon, null));

        tempView.setText(getArguments().getString("temp12") + "°C");

        speedDayView.setText(getArguments().getString("speedDay") + " km/h");

        dirDayImage.setImageDrawable(selectWindDir(getArguments().getString("dirDay")));

        locationView.setText(getArguments().getString("location"));

        sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        sourceView.setText(getActivity().getResources().getString(R.string.aemetSource) + " " + sharedPref.getString("lastDownloaded", "Data unavailable."));

        Date dt = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.DATE, 1);
        dt = c.getTime();
        String date = new SimpleDateFormat("EEEE dd/MM").format(dt);
        dateView.setText(date);

        String sky = getArguments().getString("skyDay");

        setBackground(v, sky);

        maxTempView.setText("Max: " + getArguments().getString("maxTemp") + "°C");
        minTempView.setText("Min: " + getArguments().getString("minTemp") + "°C");

        tempView00.setText(getArguments().getString("temp00") + "°C");
        tempView06.setText(getArguments().getString("temp06") + "°C");
        tempView12.setText(getArguments().getString("temp12") + "°C");
        tempView18.setText(getArguments().getString("temp18") + "°C");

        rainView00.setText(getArguments().getString("rainProb0006") + "%");
        rainView06.setText(getArguments().getString("rainProb0612") + "%");
        rainView12.setText(getArguments().getString("rainProb1218") + "%");
        rainView18.setText(getArguments().getString("rainProb1824") + "%");

        windView00.setText(getArguments().getString("windSpeed00") + " km/h");
        windView06.setText(getArguments().getString("windSpeed06") + " km/h");
        windView12.setText(getArguments().getString("windSpeed12") + " km/h");
        windView18.setText(getArguments().getString("windSpeed18") + " km/h");

        iconSky00.setImageDrawable(selectIcon(getArguments().getString("sky0006")));
        iconSky06.setImageDrawable(selectIcon(getArguments().getString("sky0612")));
        iconSky12.setImageDrawable(selectIcon(getArguments().getString("sky1218")));
        iconSky18.setImageDrawable(selectIcon(getArguments().getString("sky1824")));

        windDir00.setImageDrawable(selectWindDir(getArguments().getString("windDir00")));
        windDir06.setImageDrawable(selectWindDir(getArguments().getString("windDir06")));
        windDir12.setImageDrawable(selectWindDir(getArguments().getString("windDir12")));
        windDir18.setImageDrawable(selectWindDir(getArguments().getString("windDir18")));

        return v;
    }

    // in order to set the wind direction icon we use this method which returns the correct icon associated to the direction
    private Drawable selectWindDir(String direction) {

        Drawable draw;

        switch (direction) {

            case "N":
            case "Norte":
                draw = ResourcesCompat.getDrawable(getResources(),R.drawable.arrow_north, null);
                break;
            case "NE":
            case "Nordeste":
                draw = ResourcesCompat.getDrawable(getResources(),R.drawable.arrow_north_east, null);
                break;
            case "E":
            case "Este":
                draw = ResourcesCompat.getDrawable(getResources(),R.drawable.arrow_east, null);
                break;
            case "SE":
            case "Sudeste":
                draw = ResourcesCompat.getDrawable(getResources(),R.drawable.arrow_south_east, null);
                break;
            case "S":
            case "Sur":
                draw = ResourcesCompat.getDrawable(getResources(),R.drawable.arrow_south, null);
                break;
            case "SO":
            case "Sudoeste":
                draw = ResourcesCompat.getDrawable(getResources(),R.drawable.arrow_south_west, null);
                break;
            case "O":
            case "Oeste":
                draw = ResourcesCompat.getDrawable(getResources(),R.drawable.arrow_west, null);
                break;
            case "NO":
            case "Noroeste":
                draw = ResourcesCompat.getDrawable(getResources(),R.drawable.arrow_north_west, null);
                break;
            default:
                draw = ResourcesCompat.getDrawable(getResources(),R.drawable.no_wind, null);
        }
        return draw;
    }

    // in order to set the sky state icon we use this method which returns the correct icon associated to the state of the sky
    private Drawable selectIcon(String sky) {

        Drawable draw;

        switch (sky) {
            // clear to cloudy intervals
            case "11":
            case "12":
            case "12n":
            case "13":
            case "13n":
                draw = ResourcesCompat.getDrawable(getResources(),R.drawable.sun_icon, null);
                break;
            // clouds but no rain
            case "14":
            case "15":
            case "17":
                draw = ResourcesCompat.getDrawable(getResources(),R.drawable.clouds_icon, null);
                break;
            // rain
            case "23":
            case "24":
            case "25":
                draw = ResourcesCompat.getDrawable(getResources(),R.drawable.rain_pred_icon, null);
                break;
            // little rain
            case "43":
            case "44":
            case "45":
                draw = ResourcesCompat.getDrawable(getResources(),R.drawable.rain_icon, null);
                break;
            // thunder
            case "51":
            case "52":
            case "61":
                draw = ResourcesCompat.getDrawable(getResources(),R.drawable.thunder_icon, null);
                break;
            default:
                draw = ResourcesCompat.getDrawable(getResources(),R.drawable.clouds_sun_icon, null);
                break;
        }

        return draw;

    }

    // the background is filled with an image associated with the state of the sky. To be adaptable on different sizes we resize each image according to the screen size
    private void setBackground(View v, String sky) {

           /* adapt the image to the size of the display */
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        Bitmap bmp = null;

        switch (sky) {
            // clear to cloudy intervals
            case "11":
            case "12":
            case "12n":
            case "13":
            case "13n":
                bmp = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                        getResources(), R.drawable.clear_sky), size.x, size.y, true);
                break;
            // clouds but no rain
            case "14":
            case "15":
            case "17":
                bmp = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                        getResources(), R.drawable.clouds), size.x, size.y, true);
                break;
            // rain
            case "23":
            case "24":
            case "25":
                bmp = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                        getResources(), R.drawable.heavy_rain), size.x, size.y, true);
                break;
            // little rain
            case "43":
            case "44":
            case "45":
                bmp = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                        getResources(), R.drawable.little_rain), size.x, size.y, true);
                break;
            // thunder
            case "51":
            case "52":
                bmp = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                        getResources(), R.drawable.thunder), size.x, size.y, true);
                break;
            default:
                bmp = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                        getResources(), R.drawable.clear_sky), size.x, size.y, true);
                break;
        }


    /* fill the background ImageView with the resized image */

        LinearLayout ly = (LinearLayout) v.findViewById(R.id.first_frag);
        Drawable dr = new BitmapDrawable(getResources(), bmp);
        (ly).setBackground(dr);
    }
}