package com.cesvimexico.qagenericj.Http;

import android.content.Context;
import android.util.Log;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by JAIME VICENCIO on 30/10/2017.
 */

public class ApiFotos {
    URL connectURL;
    String responseString;
    String fileName;
    byte[] dataToServer;
    String Pat1 ;
    Context context;

    public ApiFotos(String urlString, String fileName, Context con){
        try{
            context = con;
            connectURL = new URL(urlString);
            Pat1 = urlString;
        }catch(Exception ex){
            Log.i("URL FORMATION", "MALFORMATED URL");
        }

        this.fileName = fileName;
    }


    public String doStart(FileInputStream stream, String idEvaluacion){
        fileInputStream = stream;
        String bandera =   thirdTry(idEvaluacion);
        // uploadMultipart( idEvaluacion);
        //  String bandera = "ok";
        bandera = "ok";
        return bandera;
    }

    FileInputStream fileInputStream = null;

    String thirdTry(String IdEvaluacion) {

        String BanSubidaFoto = "";
        String existingFileName = fileName;

        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        String Tag="3rd";
        try
        {
            //------------------ CLIENT REQUEST

            Log.e(Tag,"Starting to bad things");

            // PHP Service connection
            HttpURLConnection conn = (HttpURLConnection) connectURL.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");


            conn.setRequestProperty("Connection", "Keep-Alive");

            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary="+boundary);

            DataOutputStream dos = new DataOutputStream( conn.getOutputStream() );

            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\";filename=\"" + existingFileName +"\"" + lineEnd);
            dos.writeBytes(lineEnd);

            Log.e(Tag,"Headers are written");

            int bytesAvailable = fileInputStream.available();
            int maxBufferSize = 2024;
            int bufferSize = Math.min(bytesAvailable, maxBufferSize);
            byte[] buffer = new byte[bufferSize];

            int bytesRead = fileInputStream.read(buffer, 0, bufferSize);

            while (bytesRead > 0)
            {
                dos.write(buffer, 0, bufferSize);
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            }

            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

            // Close input stream
            Log.e(Tag,"File is written");
            fileInputStream.close();
            dos.flush();

            InputStream is = conn.getInputStream();
            // retrieve the response from server
            int ch;

            StringBuffer b =new StringBuffer();
            while( ( ch = is.read() ) != -1 ){
                b.append( (char)ch );
            }
            String s=b.toString();
            Log.i("Response", s);
            dos.close();



            if(s.equals("ok")){
                BanSubidaFoto = "ok";
            }

            return BanSubidaFoto;

        }
        catch (MalformedURLException ex)
        {
            Log.e(Tag, "error: " + ex.getMessage(), ex);
        }

        catch (IOException ioe)
        {
            String res = ioe.getMessage();
            Log.e(Tag, "error: " + ioe.getMessage(), ioe);
        }

        return BanSubidaFoto;
    }



}
