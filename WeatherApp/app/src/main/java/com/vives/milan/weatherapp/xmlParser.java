package com.vives.milan.weatherapp;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by milan on 07-May-17.
 * This class is responsible for parsing the XML files and putting the information into the correct class
 */

public class xmlParser {

    // we define each of the tags we are interested in
    private static final String KEY_DIA = "dia";
    private static final String KEY_PROBPRECI = "prob_precipitacion";
    private static final String KEY_CIELO = "estado_cielo";
    private static final String KEY_TEMP = "temperatura";
    private static final String KEY_TEMP_MAX = "maxima";
    private static final String KEY_TEMP_MIN = "minima";
    private static final String KEY_DATO = "dato";
    private static final String KEY_VIENTO = "viento";
    private static final String KEY_DIR = "direccion";
    private static final String KEY_VELO = "velocidad";
    private static final String KEY_ESTACION = "estacion";
    private static final String KEY_TIPO = "tipo_polinico";
    private static final String KEY_VALOR = "valor_previsto";
    private static List<Weather> weathers;
    private static Weather curWeather;
    private static List<Pollen> pollens;
    private static Pollen curPollen;
    private static XmlPullParser xpp;

    // this class reads the XML containing the predictions, it saves the information into a list of Weather objects.
    // one object for each day
    public static List<Weather> getWeatherListFromFile(String path) {

        weathers = new ArrayList<>();
        try {

            xpp = XmlPullParserFactory.newInstance().newPullParser(); // initialize a parser

            FileInputStream fis = new FileInputStream(path + "predictions.xml");
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));

            xpp.setInput(reader); // set the correct input for the parser

            int eventType = xpp.getEventType(); // each tag returns a certain event type, the parser catches these events

            while (eventType != XmlPullParser.END_DOCUMENT) {

                String tagname;

                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        tagname = xpp.getName();
                        if (tagname.equalsIgnoreCase(KEY_DIA)) {
                            curWeather = new Weather(); // this tag is the start of a new day, so a new Weather object
                        } else if (tagname.equalsIgnoreCase(KEY_PROBPRECI)) { // this tag describes the chance of rain
                            //Because with some there is no information yet.
                            if (xpp.getAttributeCount() > 0) {
                                if (xpp.getAttributeValue(null, "periodo") != null && xpp.getAttributeValue(null, "periodo").equals("00-24")) {
                                    curWeather.setRainProbDay(xpp.nextText());
                                }
                                if (xpp.getAttributeValue(null, "periodo") != null && xpp.getAttributeValue(null, "periodo").equals("00-06")) {
                                    curWeather.setRainProb0006(xpp.nextText());
                                }
                                if (xpp.getAttributeValue(null, "periodo") != null && xpp.getAttributeValue(null, "periodo").equals("06-12")) {
                                    curWeather.setRainProb0612(xpp.nextText());
                                }
                                if (xpp.getAttributeValue(null, "periodo") != null && xpp.getAttributeValue(null, "periodo").equals("12-18")) {
                                    curWeather.setRainProb1218(xpp.nextText());
                                }
                                if (xpp.getAttributeValue(null, "periodo") != null && xpp.getAttributeValue(null, "periodo").equals("18-24")) {
                                    curWeather.setRainProb1824(xpp.nextText());
                                }
                            } else if (xpp.getAttributeCount() == 0) {
                                curWeather.setRainProbDay(xpp.nextText());
                            }

                        } else if (tagname.equalsIgnoreCase(KEY_CIELO)) { // this tag describes the state of the sky
                            //Because with some there is no information yet.
                            if (xpp.getAttributeCount() > 1) {
                                if (xpp.getAttributeValue(null, "periodo") != null && xpp.getAttributeValue(null, "periodo").equals("00-24")) {
                                    curWeather.setSkyDay(xpp.nextText());
                                }
                                if (xpp.getAttributeValue(null, "periodo") != null && xpp.getAttributeValue(null, "periodo").equals("00-06")) {
                                    curWeather.setSky0006(xpp.nextText());
                                }
                                if (xpp.getAttributeValue(null, "periodo") != null && xpp.getAttributeValue(null, "periodo").equals("06-12")) {
                                    curWeather.setSky0612(xpp.nextText());
                                }
                                if (xpp.getAttributeValue(null, "periodo") != null && xpp.getAttributeValue(null, "periodo").equals("12-18")) {
                                    curWeather.setSky1218(xpp.nextText());
                                }
                                if (xpp.getAttributeValue(null, "periodo") != null && xpp.getAttributeValue(null, "periodo").equals("18-24")) {
                                    curWeather.setSky1824(xpp.nextText());
                                }
                            } else if (xpp.getAttributeCount() == 1) {
                                curWeather.setSkyDay(xpp.nextText());
                            }
                        } else if (tagname.equalsIgnoreCase(KEY_TEMP)) { // this tag describes the temperatures
                            int eventType2 = xpp.getEventType();
                            boolean tempDone = false;
                            while (!tempDone) {
                                String tagName2 = xpp.getName();
                                switch (eventType2) { // in order to read each min and max temperature we need to stay inside the current temperature tag, therefore a second loop is needed
                                    case XmlPullParser.START_TAG:
                                        if (tagName2.equalsIgnoreCase(KEY_TEMP_MAX)) {
                                            curWeather.setMaxTemp(xpp.nextText());
                                        } else if (tagName2.equalsIgnoreCase(KEY_TEMP_MIN)) {
                                            curWeather.setMinTemp(xpp.nextText());
                                        } else if (tagName2.equalsIgnoreCase(KEY_DATO)) {
                                            //Because with some there is no information yet.
                                            if (xpp.getAttributeCount() > 0) {
                                                if (xpp.getAttributeValue(null, "hora") != null && xpp.getAttributeValue(null, "hora").equals("06")) {
                                                    curWeather.setTemp06(xpp.nextText());
                                                }
                                                if (xpp.getAttributeValue(null, "hora") != null && xpp.getAttributeValue(null, "hora").equals("12")) {
                                                    curWeather.setTemp12(xpp.nextText());
                                                }
                                                if (xpp.getAttributeValue(null, "hora") != null && xpp.getAttributeValue(null, "hora").equals("18")) {
                                                    curWeather.setTemp18(xpp.nextText());
                                                }
                                                if (xpp.getAttributeValue(null, "hora") != null && xpp.getAttributeValue(null, "hora").equals("24")) {
                                                    curWeather.setTemp00(xpp.nextText());
                                                }
                                            }
                                        }
                                        break;
                                    case XmlPullParser.END_TAG:
                                        if (tagName2.equalsIgnoreCase(KEY_TEMP)) { // once we've finished reading all of the information inside this temperature tag we can leave the loop and go back to the previous loop
                                            tempDone = true;
                                        }
                                        break;
                                }
                                eventType2 = xpp.next();
                            }
                        } else if (tagname.equalsIgnoreCase(KEY_VIENTO)) { // this tag describes the wind
                            //Because with some there is no information yet.
                            if (xpp.getAttributeCount() > 0) {
                                if (xpp.getAttributeValue(null, "periodo") != null && xpp.getAttributeValue(null, "periodo").equals("00-24")) {
                                    int eventType2 = xpp.getEventType();
                                    boolean windDone = false;
                                    while (!windDone) {
                                        String tagName2 = xpp.getName();
                                        switch (eventType2) {
                                            case XmlPullParser.START_TAG:
                                                if (tagName2.equalsIgnoreCase(KEY_DIR)) {
                                                    curWeather.setWindDirDay(xpp.nextText());
                                                } else if (tagName2.equalsIgnoreCase(KEY_VELO)) {
                                                    curWeather.setWindSpeedDay(xpp.nextText());
                                                }
                                                break;
                                            case XmlPullParser.END_TAG:
                                                if (tagName2.equalsIgnoreCase(KEY_VIENTO)) {
                                                    windDone = true;
                                                }
                                                break;
                                        }
                                        eventType2 = xpp.next();
                                    }
                                }
                                if (xpp.getAttributeValue(null, "periodo") != null && xpp.getAttributeValue(null, "periodo").equals("00-06")) {
                                    int eventType2 = xpp.getEventType();
                                    boolean windDone = false;
                                    while (!windDone) {
                                        String tagName2 = xpp.getName();
                                        switch (eventType2) {
                                            case XmlPullParser.START_TAG:
                                                if (tagName2.equalsIgnoreCase(KEY_DIR)) {
                                                    curWeather.setWindDir0006(xpp.nextText());
                                                } else if (tagName2.equalsIgnoreCase(KEY_VELO)) {
                                                    curWeather.setWindSpeed0006(xpp.nextText());
                                                }
                                                break;
                                            case XmlPullParser.END_TAG:
                                                if (tagName2.equalsIgnoreCase(KEY_VIENTO)) {
                                                    windDone = true;
                                                }
                                                break;
                                        }
                                        eventType2 = xpp.next();
                                    }
                                }
                                if (xpp.getAttributeValue(null, "periodo") != null && xpp.getAttributeValue(null, "periodo").equals("06-12")) {
                                    int eventType2 = xpp.getEventType();
                                    boolean windDone = false;
                                    while (!windDone) {
                                        String tagName2 = xpp.getName();
                                        switch (eventType2) {
                                            case XmlPullParser.START_TAG:
                                                if (tagName2.equalsIgnoreCase(KEY_DIR)) {
                                                    curWeather.setWindDir0612(xpp.nextText());
                                                } else if (tagName2.equalsIgnoreCase(KEY_VELO)) {
                                                    curWeather.setWindSpeed0612(xpp.nextText());
                                                }
                                                break;
                                            case XmlPullParser.END_TAG:
                                                if (tagName2.equalsIgnoreCase(KEY_VIENTO)) {
                                                    windDone = true;
                                                }
                                                break;
                                        }
                                        eventType2 = xpp.next();
                                    }
                                }
                                if (xpp.getAttributeValue(null, "periodo") != null && xpp.getAttributeValue(null, "periodo").equals("12-18")) {
                                    int eventType2 = xpp.getEventType();
                                    boolean windDone = false;
                                    while (!windDone) {
                                        String tagName2 = xpp.getName();
                                        switch (eventType2) {
                                            case XmlPullParser.START_TAG:
                                                if (tagName2.equalsIgnoreCase(KEY_DIR)) {
                                                    curWeather.setWindDir1218(xpp.nextText());
                                                } else if (tagName2.equalsIgnoreCase(KEY_VELO)) {
                                                    curWeather.setWindSpeed1218(xpp.nextText());
                                                }
                                                break;
                                            case XmlPullParser.END_TAG:
                                                if (tagName2.equalsIgnoreCase(KEY_VIENTO)) {
                                                    windDone = true;
                                                }
                                                break;
                                        }
                                        eventType2 = xpp.next();
                                    }
                                }
                                if (xpp.getAttributeValue(null, "periodo") != null && xpp.getAttributeValue(null, "periodo").equals("18-24")) {
                                    int eventType2 = xpp.getEventType();
                                    boolean windDone = false;
                                    while (!windDone) {
                                        String tagName2 = xpp.getName();
                                        switch (eventType2) {
                                            case XmlPullParser.START_TAG:
                                                if (tagName2.equalsIgnoreCase(KEY_DIR)) {
                                                    curWeather.setWindDir1824(xpp.nextText());
                                                } else if (tagName2.equalsIgnoreCase(KEY_VELO)) {
                                                    curWeather.setWindSpeed1824(xpp.nextText());
                                                }
                                                break;
                                            case XmlPullParser.END_TAG:
                                                if (tagName2.equalsIgnoreCase(KEY_VIENTO)) {
                                                    windDone = true;
                                                }
                                                break;
                                        }
                                        eventType2 = xpp.next();
                                    }
                                }
                            } else if (xpp.getAttributeCount() == 0) {
                                int eventType2 = xpp.getEventType();
                                boolean windDone = false;
                                while (!windDone) {
                                    String tagName2 = xpp.getName();
                                    switch (eventType2) {
                                        case XmlPullParser.START_TAG:
                                            if (tagName2.equalsIgnoreCase(KEY_DIR)) {
                                                curWeather.setWindDirDay(xpp.nextText());
                                            } else if (tagName2.equalsIgnoreCase(KEY_VELO)) {
                                                curWeather.setWindSpeedDay(xpp.nextText());
                                            }
                                            break;
                                        case XmlPullParser.END_TAG:
                                            if (tagName2.equalsIgnoreCase(KEY_VIENTO)) {
                                                windDone = true;
                                            }
                                            break;
                                    }
                                    eventType2 = xpp.next();
                                }
                            }
                        }

                        break;

                    case XmlPullParser.END_TAG:
                        tagname = xpp.getName();
                        if (tagname.equalsIgnoreCase(KEY_DIA)) {
                            weathers.add(curWeather);
                        }
                        break;

                    default:
                        break;
                }

                eventType = xpp.next();
            }

        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
        return weathers; // when all is read we return the list of Weather objects
    }

    // this class reads the XML containing information about the pollen and saves it into a list of Pollen objects
    // one object for each location
    public static List<Pollen> getPollenListFromFile(String path) {

        pollens = new ArrayList<>();

        try {

            xpp = XmlPullParserFactory.newInstance().newPullParser();


            xpp.setInput(new FileInputStream(path + "pollen.xml"), "utf-8");

            int eventType = xpp.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {

                String tagname;

                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        tagname = xpp.getName();
                        if (tagname.equalsIgnoreCase(KEY_ESTACION)) {
                            curPollen = new Pollen();
                            curPollen.setLocation(xpp.getAttributeValue(null, "nombre"));
                        }
                        else if (tagname.equalsIgnoreCase(KEY_TIPO)) {
                            if (xpp.getAttributeValue(null, "nombre") != null && xpp.getAttributeValue(null, "nombre").equals("Acer (ARCE)")) {

                                curPollen.setAcer(getPollenValue(xpp));

                            } else if (xpp.getAttributeValue(null, "nombre") != null && xpp.getAttributeValue(null, "nombre").equals("Aesculus (CASTAÑO DE INDIAS)")) {

                                curPollen.setAesculus(getPollenValue(xpp));

                            } else if (xpp.getAttributeValue(null, "nombre") != null && xpp.getAttributeValue(null, "nombre").equals("Alnus (ALISO)")) {

                                curPollen.setAlnus(getPollenValue(xpp));

                            } else if (xpp.getAttributeValue(null, "nombre") != null && xpp.getAttributeValue(null, "nombre").equals("Apiaceae")) {

                                curPollen.setApiaceae(getPollenValue(xpp));

                            } else if (xpp.getAttributeValue(null, "nombre") != null && xpp.getAttributeValue(null, "nombre").equals("Asteraceae (MARGARITA)")) {

                                curPollen.setAsteraceae(getPollenValue(xpp));

                            } else if (xpp.getAttributeValue(null, "nombre") != null && xpp.getAttributeValue(null, "nombre").equals("Betula (ABEDUL)")) {

                                curPollen.setBetula(getPollenValue(xpp));

                            } else if (xpp.getAttributeValue(null, "nombre") != null && xpp.getAttributeValue(null, "nombre").equals("Brassicaceae (BERZA, COLZA)")) {

                                curPollen.setBrassicaceae(getPollenValue(xpp));

                            } else if (xpp.getAttributeValue(null, "nombre") != null && xpp.getAttributeValue(null, "nombre").equals("Chenopodiaceae (CENIZO)")) {

                                curPollen.setChenopodiaceae(getPollenValue(xpp));

                            } else if (xpp.getAttributeValue(null, "nombre") != null && xpp.getAttributeValue(null, "nombre").equals("Cupressaceae (CIPRÉS, ENEBRO, SABINA)")) {

                                curPollen.setCupressaceae(getPollenValue(xpp));

                            } else if (xpp.getAttributeValue(null, "nombre") != null && xpp.getAttributeValue(null, "nombre").equals("Cyperaceae")) {

                                curPollen.setCyperaceae(getPollenValue(xpp));

                            } else if (xpp.getAttributeValue(null, "nombre") != null && xpp.getAttributeValue(null, "nombre").equals("Echium (VIBORERA)")) {

                                curPollen.setEchium(getPollenValue(xpp));

                            } else if (xpp.getAttributeValue(null, "nombre") != null && xpp.getAttributeValue(null, "nombre").equals("Ericaceae (BREZO)")) {

                                curPollen.setEricaceae(getPollenValue(xpp));

                            } else if (xpp.getAttributeValue(null, "nombre") != null && xpp.getAttributeValue(null, "nombre").equals("Fabaceae (GUISANTES, ALUBIAS)")) {

                                curPollen.setFabaceae(getPollenValue(xpp));

                            } else if (xpp.getAttributeValue(null, "nombre") != null && xpp.getAttributeValue(null, "nombre").equals("Fagus (HAYA)")) {

                                curPollen.setFagus(getPollenValue(xpp));

                            } else if (xpp.getAttributeValue(null, "nombre") != null && xpp.getAttributeValue(null, "nombre").equals("Fraxinus (FRESNO)")) {

                                curPollen.setFraxinus(getPollenValue(xpp));

                            } else if (xpp.getAttributeValue(null, "nombre") != null && xpp.getAttributeValue(null, "nombre").equals("Galium (AMOR DE HORTELANO, LAPA)")) {

                                curPollen.setGalium(getPollenValue(xpp));

                            } else if (xpp.getAttributeValue(null, "nombre") != null && xpp.getAttributeValue(null, "nombre").equals("Juglans (NOGAL)")) {

                                curPollen.setJuglans(getPollenValue(xpp));

                            } else if (xpp.getAttributeValue(null, "nombre") != null && xpp.getAttributeValue(null, "nombre").equals("Juncaceae (JUNCO)")) {

                                curPollen.setJuncaceae(getPollenValue(xpp));

                            } else if (xpp.getAttributeValue(null, "nombre") != null && xpp.getAttributeValue(null, "nombre").equals("Mercurialis (MERCURIAL)")) {

                                curPollen.setMercurialis(getPollenValue(xpp));

                            } else if (xpp.getAttributeValue(null, "nombre") != null && xpp.getAttributeValue(null, "nombre").equals("Morus (MORAL)")) {

                                curPollen.setMorus(getPollenValue(xpp));

                            } else if (xpp.getAttributeValue(null, "nombre") != null && xpp.getAttributeValue(null, "nombre").equals("Myrtaceae (EUCALIPTO)")) {

                                curPollen.setMyrtaceae(getPollenValue(xpp));

                            } else if (xpp.getAttributeValue(null, "nombre") != null && xpp.getAttributeValue(null, "nombre").equals("Olea (OLIVO)")) {

                                curPollen.setOlea(getPollenValue(xpp));

                            } else if (xpp.getAttributeValue(null, "nombre") != null && xpp.getAttributeValue(null, "nombre").equals("Pinus (PINO)")) {

                                curPollen.setPinus(getPollenValue(xpp));

                            } else if (xpp.getAttributeValue(null, "nombre") != null && xpp.getAttributeValue(null, "nombre").equals("Plantago (LLANTEN)")) {

                                curPollen.setPlantago(getPollenValue(xpp));

                            } else if (xpp.getAttributeValue(null, "nombre") != null && xpp.getAttributeValue(null, "nombre").equals("Platanus (PLATANO DE SOMBRA)")) {

                                curPollen.setPlatanus(getPollenValue(xpp));

                            } else if (xpp.getAttributeValue(null, "nombre") != null && xpp.getAttributeValue(null, "nombre").equals("Poaceae (GRAMINEAS)")) {

                                curPollen.setPoaceae(getPollenValue(xpp));

                            } else if (xpp.getAttributeValue(null, "nombre") != null && xpp.getAttributeValue(null, "nombre").equals("Populus (CHOPO, ALAMO)")) {

                                curPollen.setPopulus(getPollenValue(xpp));

                            } else if (xpp.getAttributeValue(null, "nombre") != null && xpp.getAttributeValue(null, "nombre").equals("Quercus (ENCINA, ROBLE)")) {

                                curPollen.setQuercus(getPollenValue(xpp));

                            } else if (xpp.getAttributeValue(null, "nombre") != null && xpp.getAttributeValue(null, "nombre").equals("Rosaceae (ROSA, ZARZAMORA)")) {

                                curPollen.setRosaceae(getPollenValue(xpp));

                            } else if (xpp.getAttributeValue(null, "nombre") != null && xpp.getAttributeValue(null, "nombre").equals("Rumex (ACEDERA)")) {

                                curPollen.setRumex(getPollenValue(xpp));

                            } else if (xpp.getAttributeValue(null, "nombre") != null && xpp.getAttributeValue(null, "nombre").equals("Salix (SAUCE)")) {

                                curPollen.setSalix(getPollenValue(xpp));

                            } else if (xpp.getAttributeValue(null, "nombre") != null && xpp.getAttributeValue(null, "nombre").equals("Sambucus (SAUCO)")) {

                                curPollen.setSambucus(getPollenValue(xpp));

                            } else if (xpp.getAttributeValue(null, "nombre") != null && xpp.getAttributeValue(null, "nombre").equals("Taraxacum (DIENTE DE LEÓN)")) {

                                curPollen.setTaraxacum(getPollenValue(xpp));

                            } else if (xpp.getAttributeValue(null, "nombre") != null && xpp.getAttributeValue(null, "nombre").equals("Tilia (TILO)")) {

                                curPollen.setTilia(getPollenValue(xpp));

                            } else if (xpp.getAttributeValue(null, "nombre") != null && xpp.getAttributeValue(null, "nombre").equals("Ulmus (OLMO)")) {

                                curPollen.setUlmus(getPollenValue(xpp));

                            } else if (xpp.getAttributeValue(null, "nombre") != null && xpp.getAttributeValue(null, "nombre").equals("Urticaceae (ORTIGA, PARIETARIA, PELOSILLA)")) {

                                curPollen.setUrticaceae(getPollenValue(xpp));

                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        tagname = xpp.getName();
                        if (tagname != null && tagname.equalsIgnoreCase(KEY_ESTACION)) {
                            pollens.add(curPollen);
                        }
                        break;

                    default:
                        break;
                }
                eventType = xpp.next();
            }
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
        return pollens;
    }

    public static String getPollenValue (XmlPullParser xpp){
        int eventType2;
        String value = null;
        try {
            eventType2 = xpp.getEventType();
            boolean pollenDone = false;
            while (!pollenDone) {
                String tagName2 = xpp.getName();
                switch (eventType2) {
                    case XmlPullParser.START_TAG:
                        if (tagName2.equalsIgnoreCase(KEY_VALOR)) {
                            value = xpp.nextText();
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (tagName2.equalsIgnoreCase(KEY_TIPO)) {
                            pollenDone = true;
                        }
                        break;
                }
                eventType2 = xpp.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return value;
    }

}