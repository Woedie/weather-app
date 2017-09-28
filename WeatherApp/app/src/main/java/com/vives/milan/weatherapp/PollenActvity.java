package com.vives.milan.weatherapp;

/**
 * Created by milan on 15-May-17.
 * This class describes the logic behind the pollen activity.
 * This activity gives an overview of the pollen and their status.
 */

import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class PollenActvity extends AppCompatActivity {

    String overallStatus = "UNKNOWN";

    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pollen);

        String mainPath = getExternalFilesDir(null) + "/";

        List<Pollen> pollenList = new xmlParser().getPollenListFromFile(mainPath); // read the data from the XML file

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        String location = sharedPref.getString("pollenLocation", "Valladolid"); // decide the correct location from the shared preferences

        fillImages(pollenList, location);
    }

    // this method fills all of the images describing the status of each type of pollen
    private void fillImages(List<Pollen> pollenList, String location) {

        TextView dateView = (TextView) findViewById(R.id.dateView);
        ImageView overallImage = (ImageView) findViewById(R.id.overallImage);
        ImageView acerImage = (ImageView) findViewById(R.id.imageAcer);
        ImageView aesculusImage = (ImageView) findViewById(R.id.imageAesculus);
        ImageView alnusImage = (ImageView) findViewById(R.id.imageAlnus);
        ImageView apiaceaeImage = (ImageView) findViewById(R.id.imageApiaceae);
        ImageView asteraceaeImage = (ImageView) findViewById(R.id.imageAsteraceae);
        ImageView betulaImage = (ImageView) findViewById(R.id.imageBetula);
        ImageView brassicaceaeImage = (ImageView) findViewById(R.id.imageBrassicaceae);
        ImageView chenopodiaceaeImage = (ImageView) findViewById(R.id.imageChenopodiaceae);
        ImageView cupressaceaeImage = (ImageView) findViewById(R.id.imageCupressaceae);
        ImageView cyperaceaeImage = (ImageView) findViewById(R.id.imageCyperaceae);
        ImageView echiumImage = (ImageView) findViewById(R.id.imageEchium);
        ImageView ericaceaeImage = (ImageView) findViewById(R.id.imageEricaceae);
        ImageView fabaceaeImage = (ImageView) findViewById(R.id.imageFabaceae);
        ImageView fagusImage = (ImageView) findViewById(R.id.imageFagus);
        ImageView fraxinusImage = (ImageView) findViewById(R.id.imageFraxinus);
        ImageView galiumImage = (ImageView) findViewById(R.id.imageGalium);
        ImageView juglansImage = (ImageView) findViewById(R.id.imageJuglans);
        ImageView juncaceaeImage = (ImageView) findViewById(R.id.imageJuncaceae);
        ImageView mercurialisImage = (ImageView) findViewById(R.id.imageMercurialis);
        ImageView morusImage = (ImageView) findViewById(R.id.imageMorus);
        ImageView myrtaceaeImage = (ImageView) findViewById(R.id.imageMyrtaceae);
        ImageView oleaImage = (ImageView) findViewById(R.id.imageOlea);
        ImageView pinusImage = (ImageView) findViewById(R.id.imagePinus);
        ImageView plantagoImage = (ImageView) findViewById(R.id.imagePlantago);
        ImageView platanusImage = (ImageView) findViewById(R.id.imagePlatanus);
        ImageView poaceaeImage = (ImageView) findViewById(R.id.imagePoaceae);
        ImageView populusImage = (ImageView) findViewById(R.id.imagePopulus);
        ImageView quercusImage = (ImageView) findViewById(R.id.imageQuercus);
        ImageView rosaceaeImage = (ImageView) findViewById(R.id.imageRosaceae);
        ImageView rumexImage = (ImageView) findViewById(R.id.imageRumex);
        ImageView salixImage = (ImageView) findViewById(R.id.imageSalix);
        ImageView sambucusImage = (ImageView) findViewById(R.id.imageSambucus);
        ImageView taraxumImage = (ImageView) findViewById(R.id.imageTaraxum);
        ImageView tiliaImage = (ImageView) findViewById(R.id.imageTillia);
        ImageView ulmusImage = (ImageView) findViewById(R.id.imageUlmus);
        ImageView urticaceaeImage = (ImageView) findViewById(R.id.imageUrticaceae);

        Calendar c = Calendar.getInstance();
        String date = new SimpleDateFormat("EEEE dd/MM").format(c.getTime());
        dateView.setText(date);

        TextView sourceView = (TextView) findViewById(R.id.sourceView);

        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        sourceView.setText(this.getResources().getString(R.string.pollenSource) + " " + sharedPref.getString("lastDownloaded", "Data unavailable."));

        for (int i = 0; i < pollenList.size(); i++){
            if(pollenList.get(i).getLocation().equalsIgnoreCase(location)){

                acerImage.setImageDrawable(selectState(pollenList.get(i).getAcer()));
                aesculusImage.setImageDrawable(selectState(pollenList.get(i).getAesculus()));
                alnusImage.setImageDrawable(selectState(pollenList.get(i).getAlnus()));
                apiaceaeImage.setImageDrawable(selectState(pollenList.get(i).getApiaceae()));
                asteraceaeImage.setImageDrawable(selectState(pollenList.get(i).getAsteraceae()));
                betulaImage.setImageDrawable(selectState(pollenList.get(i).getBetula()));
                brassicaceaeImage.setImageDrawable(selectState(pollenList.get(i).getBrassicaceae()));
                chenopodiaceaeImage.setImageDrawable(selectState(pollenList.get(i).getChenopodiaceae()));
                cupressaceaeImage.setImageDrawable(selectState(pollenList.get(i).getCupressaceae()));
                cyperaceaeImage.setImageDrawable(selectState(pollenList.get(i).getCyperaceae()));
                echiumImage.setImageDrawable(selectState(pollenList.get(i).getEchium()));
                ericaceaeImage.setImageDrawable(selectState(pollenList.get(i).getEricaceae()));
                fabaceaeImage.setImageDrawable(selectState(pollenList.get(i).getFabaceae()));
                fagusImage.setImageDrawable(selectState(pollenList.get(i).getFagus()));
                fraxinusImage.setImageDrawable(selectState(pollenList.get(i).getFraxinus()));
                galiumImage.setImageDrawable(selectState(pollenList.get(i).getGalium()));
                juglansImage.setImageDrawable(selectState(pollenList.get(i).getJuglans()));
                juncaceaeImage.setImageDrawable(selectState(pollenList.get(i).getJuncaceae()));
                mercurialisImage.setImageDrawable(selectState(pollenList.get(i).getMercurialis()));
                morusImage.setImageDrawable(selectState(pollenList.get(i).getMorus()));
                myrtaceaeImage.setImageDrawable(selectState(pollenList.get(i).getMyrtaceae()));
                oleaImage.setImageDrawable(selectState(pollenList.get(i).getOlea()));
                pinusImage.setImageDrawable(selectState(pollenList.get(i).getPinus()));
                plantagoImage.setImageDrawable(selectState(pollenList.get(i).getPlantago()));
                platanusImage.setImageDrawable(selectState(pollenList.get(i).getPlatanus()));
                poaceaeImage.setImageDrawable(selectState(pollenList.get(i).getPoaceae()));
                populusImage.setImageDrawable(selectState(pollenList.get(i).getPopulus()));
                quercusImage.setImageDrawable(selectState(pollenList.get(i).getQuercus()));
                rosaceaeImage.setImageDrawable(selectState(pollenList.get(i).getRosaceae()));
                rumexImage.setImageDrawable(selectState(pollenList.get(i).getRumex()));
                salixImage.setImageDrawable(selectState(pollenList.get(i).getSalix()));
                sambucusImage.setImageDrawable(selectState(pollenList.get(i).getSambucus()));
                taraxumImage.setImageDrawable(selectState(pollenList.get(i).getTaraxacum()));
                tiliaImage.setImageDrawable(selectState(pollenList.get(i).getTilia()));
                ulmusImage.setImageDrawable(selectState(pollenList.get(i).getUlmus()));
                urticaceaeImage.setImageDrawable(selectState(pollenList.get(i).getUrticaceae()));

                overallImage.setImageDrawable(selectState(overallStatus));

            }
        }
    }

    // each status has a different image to display, this function returns the correct image according to each state
    // it also determines the overall pollen state, which is the maximum between all of the different types of pollen
    private Drawable selectState(String state) {

        Drawable draw;

        if (state == null){
            draw = ResourcesCompat.getDrawable(getResources(),R.drawable.white, null);
        }
        else{
            switch (state) {

                case "BAJO":
                    draw = ResourcesCompat.getDrawable(getResources(),R.drawable.green, null);
                    if(!overallStatus.equals("MODERADO") && !overallStatus.equals("ALTO")){
                        overallStatus = "BAJO";
                    }
                    break;
                case "MODERADO":
                    draw = ResourcesCompat.getDrawable(getResources(),R.drawable.orange, null);
                    if(!overallStatus.equals("ALTO")){
                        overallStatus = "MODERADO";
                    }
                    break;
                case "ALTO":
                    draw = ResourcesCompat.getDrawable(getResources(),R.drawable.red, null);
                    overallStatus = "ALTO";
                    break;
                default:
                    draw = ResourcesCompat.getDrawable(getResources(),R.drawable.white, null);
                    overallStatus = "UNKNOWN";
                    break;
            }
        }
        return draw;
    }

}
