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
 * This class determines the logic behind all of the fragments except the first two
 */

public class ThirdFragment extends Fragment {

    private SharedPreferences sharedPref;

    // the next method makes an instance of the fragment while saving all the necessary arguments
    public static ThirdFragment newInstance(Weather predWeather, int days, String location) {

        ThirdFragment f = new ThirdFragment();
        Bundle args = new Bundle(); // the arguments are saved in a Bundle object, which operates as a key-value pair

        args.putString("skyDay", predWeather.getSkyDay());

        args.putString("location", location);

        args.putString("maxTemp", predWeather.getMaxTemp());
        args.putString("minTemp", predWeather.getMinTemp());

        args.putString("rainDay", predWeather.getRainProbDay());

        args.putString("windDirDay", predWeather.getWindDirDay());
        args.putString("windSpeedDay", predWeather.getWindSpeedDay());

        args.putInt("days", days);

        f.setArguments(args);

        return f;
    }

    // in order to create the view for the fragment, we run the next method. Here we can call on the arguments set in the previous method
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.third_frag, container, false);

        TextView maxTempView = (TextView) v.findViewById(R.id.maxTempView);
        TextView minTempView = (TextView) v.findViewById(R.id.minTempView);

        TextView tempViewDay = (TextView) v.findViewById(R.id.tempViewDay);

        TextView rainViewDay = (TextView) v.findViewById(R.id.rainViewDay);

        TextView windViewDay = (TextView) v.findViewById(R.id.windViewDay);

        TextView tempView = (TextView) v.findViewById(R.id.temperatureView);
        TextView dateView = (TextView) v.findViewById(R.id.dateView);
        TextView locationView = (TextView) v.findViewById(R.id.locationView);

        TextView sourceView = (TextView) v.findViewById(R.id.sourceView);

        ImageView tempIcon = (ImageView) v.findViewById(R.id.tempIcon);
        ImageView rainIcon = (ImageView) v.findViewById(R.id.rainIcon);
        ImageView windIcon = (ImageView) v.findViewById(R.id.windIcon);

        ImageView iconSkyDay = (ImageView) v.findViewById(R.id.imageDay);

        ImageView windDirDay = (ImageView) v.findViewById(R.id.windDirDay);

        tempIcon.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.temp_icon, null));
        rainIcon.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.rain_icon, null));
        windIcon.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.wind_icon, null));

        tempView.setText(getArguments().getString("maxTemp") + "°C");

        locationView.setText(getArguments().getString("location"));

        sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        sourceView.setText(getActivity().getResources().getString(R.string.aemetSource) + " " + sharedPref.getString("lastDownloaded", "Data unavailable."));

        Date dt = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.DATE, getArguments().getInt("days"));
        dt = c.getTime();
        String date = new SimpleDateFormat("EEEE dd/MM").format(dt);
        dateView.setText(date);

        String sky = getArguments().getString("skyDay");

        setBackground(v, sky);

        maxTempView.setText("Max: " + getArguments().getString("maxTemp") + "°C");
        minTempView.setText("Min: " + getArguments().getString("minTemp") + "°C");

        tempViewDay.setText(getArguments().getString("maxTemp") + "°C");

        rainViewDay.setText(getArguments().getString("rainDay") + "%");

        windViewDay.setText(getArguments().getString("windSpeedDay") + " km/h");

        windDirDay.setImageDrawable(selectWindDir(getArguments().getString("windDirDay")));

        iconSkyDay.setImageDrawable(selectIcon(getArguments().getString("skyDay")));

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

        LinearLayout ly = (LinearLayout) v.findViewById(R.id.third_frag);
        Drawable dr = new BitmapDrawable(getResources(), bmp);
        (ly).setBackground(dr);
    }
}
