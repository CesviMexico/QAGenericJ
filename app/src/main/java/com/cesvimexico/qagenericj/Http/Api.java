package com.cesvimexico.qagenericj.Http;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;



import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Retrofit;

public class Api {

//    Retrofit retrofit = new Retrofit.Builder()
//            .baseUrl("https://gma.cesvimexico.com.mx/auditsvcc/api/")
//            .build();


    private static final String PathWs = "https://apis.cesvimexico.com.mx/LevInfoCesvi/public/sincronizacion";
   // private static final String PathWs = "http://10.0.132.106/LevInfoCesvi/Version1_Portal/backend/public/sincronizacion";
    private static final MediaType MEDIA_TYPE_PLAINTEXT = MediaType.parse("text/plain; charset=utf-8");


    public static String[] QueryWebServiceExecute(List<NameValuePair> nameValuePairList, String Router ){
        String Resultado = "";
        HttpClient httpcliente = new DefaultHttpClient();
        String urlSer = PathWs+Router;
        HttpPost httppost = new HttpPost(urlSer);
        HttpResponse httpResponse;

        try {
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairList));
            try {
                httpResponse = httpcliente.execute(httppost);
                HttpEntity httpEntity = httpResponse.getEntity();
                InputStream inputStream = httpEntity.getContent();
                Resultado = getConvertStreamToString(inputStream);

            } catch (IOException e) {
                e.printStackTrace();
                Resultado = "Error al Consumir Webservice";
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Resultado = "Error al Consumir Webservice";
        }
        String Res[] = {Resultado, Resultado};
        return Res;
    }


    public static String[] QueryWebServiceExeGral(List<NameValuePair> nameValuePairList, String Url){
        String Resultado = "";
        HttpClient httpcliente = new DefaultHttpClient();
        String path = PathWs+"/"+Url;
        HttpPost httppost = new HttpPost(path);
        HttpResponse httpResponse;
        try {
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairList));
            try {
                httpResponse = httpcliente.execute(httppost);
                HttpEntity httpEntity = httpResponse.getEntity();
                InputStream inputStream = httpEntity.getContent();
                Resultado = getConvertStreamToString(inputStream);

            } catch (IOException e) {
                e.printStackTrace();
                Resultado = "Error al Consumir Webservice";
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Resultado = "Error al Consumir Webservice";
        }
        String Res[] = {Resultado, Resultado};
        return Res;
    }



    private static String getConvertStreamToString(InputStream inputStream){

        if(inputStream != null){
            StringBuilder sb = new StringBuilder();
            String line;

            try {
                BufferedReader reader= new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                while ((line = reader.readLine())!= null){
                    sb.append(line).append("\n");
                }

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return  "";
            } catch (IOException e) {
                e.printStackTrace();
                return "";
            }

            finally{
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return sb.toString();

        }else{
            return  "";
        }
    }

    /* Consumir WebService
    public static String[] getRequest(List<NameValuePair> nameValuePairs) {
        String Result = "ok";
        String resultado = "";
        HttpClient httpClient = new DefaultHttpClient();


        HttpPost post = new HttpPost(PathWs);
        try {

            post.setHeader(HTTP.CONTENT_TYPE,  "text/plain;charset=UTF-8");
            post.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            HttpResponse resp = httpClient.execute(post);
            int StatusCode = resp.getStatusLine().getStatusCode();
            if (StatusCode == 200) {
                resultado = EntityUtils.toString(resp.getEntity());
                Log.w("StatusCode", resultado);
            } else {
                resultado = "Error al Consulta WS: error:" + StatusCode + " - " + resp.getStatusLine().getReasonPhrase();
                Result = "no";
            }
        } catch (Exception ex) {
            resultado = "ServicioRest Error" + ex;
            Result = "no";
        }
        String Res[] = {Result, resultado};
        return Res;
    }
*/
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String OkHttpClientPostBitmap(String id, String tipo, String name, Bitmap bitmatDtector) {
        OkHttpClient client = new OkHttpClient();
        String Resultado = "";
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(1, TimeUnit.MINUTES) // connect timeout
                .writeTimeout(1, TimeUnit.MINUTES) // write timeout
                .readTimeout(1, TimeUnit.MINUTES); // read timeout

        client = builder.build();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmatDtector.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        Request request = null;
        try {
            request = new Request.Builder()
                    .url(PathWs + "img.php?id=" + id + "&tipo=" + tipo + "&name=" + name)
                    .post(RequestBody.create(MEDIA_TYPE_PLAINTEXT, byteArray))
                    .build();
            Response response = client.newCall(request).execute();
            Resultado = response.body().string();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Resultado = e.getMessage();
        } catch (IOException e) {
            e.printStackTrace();
            Resultado = e.getMessage();
        }
        return Resultado;
    }
}
