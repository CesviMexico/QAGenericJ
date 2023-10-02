package com.cesvimexico.qagenericj.login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.cesvimexico.qagenericj.EvaluacionList;
import com.cesvimexico.qagenericj.Http.Api;
import com.cesvimexico.qagenericj.R;
import com.cesvimexico.qagenericj.db.DBManager;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
public class DataUserActivity extends AppCompatActivity {

    private SharedPreferences preferences;
    private String user;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_user);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        preferences = getSharedPreferences("usrdata", Context.MODE_PRIVATE);
        user = preferences.getString("email", "");

        TextView txtAuditorDatos = findViewById(R.id.txtUser);
        Button btnCerrarDatos = findViewById(R.id.btnCerrarDatos);

        TextView textViewVersion = findViewById(R.id.textViewVersion);

        try {
            PackageInfo paquete = this.getPackageManager().getPackageInfo(getPackageName(), 0);
            String versionDeAplicacion = paquete.versionName;
            textViewVersion.setText("Versión app: " + versionDeAplicacion);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


        txtAuditorDatos.setText(user.toString());
        btnCerrarDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();

            }
        });

        Button buttonUpdateBD = findViewById(R.id.buttonUpdateBD);
        buttonUpdateBD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //progressDialog = new ProgressDialog(DataUserActivity.this);


                new AsyncTaskSincr().execute();
            }
        });
    }

    private class AsyncTaskSincr extends AsyncTask<String, Float, Integer> {

        String respuesta;

        public AsyncTaskSincr() {

        }

        protected void onPreExecute() {

            progressDialog = new ProgressDialog(DataUserActivity.this, R.style.MyAlertDialogStyle);
            progressDialog.setMax(100);
            progressDialog.isShowing();
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Enviando información...");
            progressDialog.setTitle("Espere");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.show();



            Log.d("onPreExecute AsyncTaskSincr", "");
        }

        protected Integer doInBackground(String... Urls) {
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("parametros", user));
            String Res[] = Api.QueryWebServiceExeGral(nameValuePairs, "SincronizacionBD");
            respuesta = Res[0];

            // dialog.setProgress(0);
            return null;
        }

        protected void onPostExecute(Integer bytes) {
            Log.d("EVALUACION", "TERMINE");
            progressDialog.incrementProgressBy(20);

            DBManager bdm = new DBManager(DataUserActivity.this);
            SQLiteDatabase db = bdm.getWritableDatabase();

            JSONArray jsonArray = null;
            try {
                jsonArray = new JSONArray("["+respuesta+"]");
                JSONArray jsonArrayDataInfo = new JSONArray( jsonArray.getJSONObject(0).getString("dataInfo"));
                for (int i = 0; i < jsonArrayDataInfo.length(); i++) {
                    JSONObject jsonObject = jsonArrayDataInfo.getJSONObject(i);
                    String tabla = jsonObject.getString("table");
                    JSONArray jsonArrayTabla = jsonObject.getJSONArray("dataInfo");

                    Iterator<String> iter = jsonArrayTabla.getJSONObject(i).keys(); //This should be the iterator you want.
                    String Cadena = "";
                    while(iter.hasNext()){
                        String key = iter.next();
                        key = " `"+key+"` ";
                        if(Cadena.equals("")){
                            Cadena = key;
                        }else{
                            Cadena = Cadena +" , "+ key;
                        }
                    }

                    String ValueSql = "";
                    for (int y = 0; y < jsonArrayTabla.length(); y++) {
                        JSONObject jsonObjectReg = jsonArrayTabla.getJSONObject(y);
                        String ValueReg = "";
                       Iterator<String> iter2 = jsonArrayTabla.getJSONObject(i).keys(); //This should be the iterator you want.
                        while(iter2.hasNext()){
                            String key = iter2.next();
                            String col = "'"+jsonObjectReg.getString(key)+"'";
                            if(ValueReg.equals("")){
                                ValueReg = col ;
                            }else{
                                ValueReg = ValueReg +" , "+ col;
                            }
                        }

                        ValueSql = ValueSql + "("+ ValueReg+")";
                        if(y == (jsonArrayTabla.length() -1)){
                            ValueSql = ValueSql +";";
                        }else{
                            ValueSql = ValueSql +",";
                        }
                    }
                    String SqlDelet = "DELETE FROM  "+ tabla;
                    db.execSQL(SqlDelet);
                    String Sql  = " INSERT INTO `"+tabla+"` ( "+ Cadena + ") VALUES "+ ValueSql;
                    db.execSQL(Sql);

                }

                db.close();
                bdm.close();

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }


            progressDialog.dismiss();
        }
    }


}