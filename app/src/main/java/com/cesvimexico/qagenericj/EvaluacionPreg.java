package com.cesvimexico.qagenericj;

import static java.lang.Thread.sleep;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.cesvimexico.qagenericj.adapters.AdapterPregunta;
import com.cesvimexico.qagenericj.adapters.AdapterSeccion;
import com.cesvimexico.qagenericj.db.DBManager;
import com.cesvimexico.qagenericj.model.EvaluacionData;
import com.cesvimexico.qagenericj.model.Pregunta;
import com.cesvimexico.qagenericj.model.Respuesta;
import com.cesvimexico.qagenericj.model.Seccion;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EvaluacionPreg extends AppCompatActivity {

    private int id_servicio;
    private int id_seccion;
    private int id_evaluacion;
    private int id_area;
    private String idE;
    private String sql;
    private String email;
    private SharedPreferences preferences;
    private Cursor cursor;
    private Cursor cursorR;
    private DBManager bdm;
    private SQLiteDatabase db;
   // private FirebaseFirestore dbFB;
    private String TAG = "EVALUACION";

    private RecyclerView recyclerViewPreg;
    private AdapterPregunta adaptador;

    ExecutorService executor;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluacion_preguntas);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        preferences = getSharedPreferences("usrdata", Context.MODE_PRIVATE);
        email = preferences.getString("email", "");
        this.id_servicio = getIntent().getExtras().getInt("id_servicio");
        this.id_seccion = getIntent().getExtras().getInt("id_seccion");
        this.idE = getIntent().getExtras().getString("idE");

        EvaluacionData evalData = new EvaluacionData(EvaluacionPreg.this, idE);
        this.id_area = evalData.getId_area();
        this.id_evaluacion = evalData.getId_evaluacion();

        recyclerViewPreg = findViewById(R.id.recyclerViewPreg);

        executor = Executors.newSingleThreadExecutor();
        handler = new Handler(Looper.getMainLooper());

       // dbFB = FirebaseFirestore.getInstance();
        bdm = new DBManager(EvaluacionPreg.this);

        db = bdm.getWritableDatabase();


        sql = "SELECT `nombre` FROM `qa_sec` WHERE `id_seccion` = '" + id_seccion + "' AND `id_servicio` = '" + id_servicio + "'";
        cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            setTitle(cursor.getString(0));
        }

//        scrollViewSections = findViewById(R.id.scrollViewSections);
//        linearPIndSections.setVisibility(View.VISIBLE);
//        scrollViewSections.setVisibility(View.GONE);

        executor.execute(new Runnable() {

            @Override
            public void run() {
                ArrayList<Pregunta> result = new ArrayList<>();
                sql = "SELECT \n" +
                        "  `qa_pre`.`id_pregunta`,\n" +
                        "  `qa_crit`.`criterio`,\n" +
                        "  `qa_pre`.`pregunta`,\n" +
                        "  `qa_pre`.`tipo`,\n" +
                        "  `qa_pre`.`puntaje`\n" +
                        "FROM\n" +
                        "  `qa_pre`\n" +
                        "  INNER JOIN `qa_crit` ON (`qa_pre`.`id_criterio` = `qa_crit`.`id_criterio`)\n" +
                        "  INNER JOIN `qa_sec` ON (`qa_pre`.`id_seccion` = `qa_sec`.`id_seccion`)\n" +
                        "WHERE\n" +
                        "  `qa_pre`.`id_seccion` = '" + id_seccion + "' AND \n" +
                        "  `qa_pre`.`status` = 'ALTA' AND \n" +
                        "  `qa_sec`.`id_servicio` = '" + id_servicio + "'";
                cursor = db.rawQuery(sql, null);
                while (cursor.moveToNext()) {

                    int id_resp_default = 0;
                    ArrayList<Respuesta> resultR = new ArrayList<>();
                    /*RESPUESTAS POR PREGUNTAS*/
                    String sql = "SELECT id_respuesta, id_pregunta, respuesta, puntaje, `default` FROM `qa_rsp` WHERE `qa_rsp`.`id_pregunta` = '" + cursor.getInt(0) + "' AND `qa_rsp`.`status` = 'ALTA'";
                    cursorR = db.rawQuery(sql, null);

                    while (cursorR.moveToNext()) {
                        resultR.add(new Respuesta(cursorR.getInt(0), cursorR.getInt(1), cursorR.getString(2), cursorR.getInt(3)));

                        if (cursorR.getString(4) != null && cursorR.getString(4).equals("*")) {
                            id_resp_default = cursorR.getInt(0);
                        }

                    }
                    result.add(new Pregunta(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), resultR,  cursor.getInt(4) ));
                    if (!cursor.getString(3).equals("TX")) {
                        sql = "SELECT id_evdo_resp  FROM `qa_zeval_resp` WHERE `id_pregunta` = '" + cursor.getInt(0) + "' AND `id_evaluacion`='" + id_evaluacion + "' ";
                        cursorR = db.rawQuery(sql, null);
                        if (cursorR.getCount() <= 0) {

                            String Aplica = "SI";
                            if(cursor.getInt(4) == 0){
                                Aplica = "NO";
                            }

                            sql = "INSERT INTO qa_zeval_resp (id_evaluacion,id_pregunta,id_respuesta, aplica) VALUES ('" + id_evaluacion + "'," + cursor.getInt(0) + ",'" + id_resp_default + "','"+Aplica+"')";
                            db.execSQL(sql);
                        }
                        cursorR.close();
                    }
                }

                db.close();


                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adaptador = new AdapterPregunta(EvaluacionPreg.this, result, idE, id_seccion, id_servicio);
                        recyclerViewPreg.setHasFixedSize(true);
                        recyclerViewPreg.setLayoutManager(new LinearLayoutManager(EvaluacionPreg.this, LinearLayoutManager.VERTICAL, false));
                        recyclerViewPreg.setAdapter(adaptador);
                    }
                }, 1000);

            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        if(adaptador != null){
            adaptador.notifyDataSetChanged();
        }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_serv_eval_back, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.continuar_evaluacionEF:

                openActivity();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openActivity() {
      /*  Intent intent = new Intent(EvaluacionPreg.this, EvaluacionSecc.class);
        intent.putExtra("id_area", this.id_area);
        intent.putExtra("id_servicio", this.id_servicio);
        intent.putExtra("idE", this.idE);
        startActivity(intent);*/
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK) {

        }
        return false;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        this.adaptador.notifyDataSetChanged();
        if (requestCode == 7) {

            if (resultCode == RESULT_OK) {
                //Get the result from SecondActivity
                this.adaptador.notifyDataSetChanged();
            }

        } else if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                //Get the result from ThirdActivity
            }
        }
    }
}
