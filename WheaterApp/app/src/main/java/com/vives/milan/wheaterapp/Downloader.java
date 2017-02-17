package com.vives.milan.wheaterapp;

/**
 * Created by milan on 17-Feb-17.
 */

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;


import android.util.Log;

public class Downloader {

    private final String PATH = "/storage/emulated/0/Android/data/com.vives.milan.mathsucks/";  //put the downloaded file here


    public void DownloadFromUrl() {  //this is the downloader method
        try {
            URL url = new URL("http://www.aemet.es/es/eltiempo/observacion/ultimosdatos_castilla-y-leon_datos-horarios.csv?k=cle&datos=det&w=0&f=temperatura&x=h24"); //you can write here any link
            File file = new File("data.csv");

            long startTime = System.currentTimeMillis();
            Log.d("ImageManager", "download begining");
            Log.d("ImageManager", "download url:" + url);
            Log.d("ImageManager", "downloaded file name:" + "data.csv");
            Log.d("ImageManager", "downloaded PATH:" + PATH);
                        /* Open a connection to that URL. */
            URLConnection ucon = url.openConnection();

                        /*
                         * Define InputStreams to read from the URLConnection.
                         */
            InputStream is = ucon.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);

                        /*
                         * Read bytes to the Buffer until there is nothing more to read(-1).
                         */
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            byte[] data = new byte[50];
            int current = 0;
            while((current = bis.read(data,0,data.length)) != -1){
                buffer.write(data,0,current);
            }

                        /* Convert the Bytes read to a String. */
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(buffer.toByteArray());
            fos.close();
            Log.d("ImageManager", "download ready in"
                    + ((System.currentTimeMillis() - startTime) / 1000)
                    + " sec");

        } catch (IOException e) {
            Log.d("ImageManager", "Error: " + e);
        }

    }
}
