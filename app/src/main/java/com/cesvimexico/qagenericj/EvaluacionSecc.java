package com.cesvimexico.qagenericj;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ScrollView;

import com.cesvimexico.qagenericj.adapters.AdapterArea;
import com.cesvimexico.qagenericj.adapters.AdapterSeccion;
import com.cesvimexico.qagenericj.db.DBManager;
import com.cesvimexico.qagenericj.model.Area;
import com.cesvimexico.qagenericj.model.EvaluacionData;
import com.cesvimexico.qagenericj.model.Seccion;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EvaluacionSecc extends AppCompatActivity {

    private RecyclerView recyclerViewSecc;
    private LinearProgressIndicator linearPIndSections;
    private ScrollView scrollViewSections;

    private DBManager bdm;
    private SQLiteDatabase db;
    private String sql;
    private Cursor cursor;
    private AdapterSeccion adaptador;
    private String idE;
    private int id_servicio;
    private int id_area;
    private int id_evaluacion;
   // private FirebaseFirestore dbFB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluacion_secc);

      //  dbFB = FirebaseFirestore.getInstance();
        bdm = new DBManager(EvaluacionSecc.this);
        db = bdm.getWritableDatabase();

        recyclerViewSecc = findViewById(R.id.recyclerViewSecc);

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        this.id_servicio = getIntent().getExtras().getInt("id_servicio");
        this.id_area = getIntent().getExtras().getInt("id_area");
        this.idE = getIntent().getExtras().getString("idE");

        EvaluacionData evaData = new EvaluacionData(EvaluacionSecc.this,idE);
        this.id_evaluacion = evaData.getId_evaluacion();


        executor.execute(new Runnable() {
            @Override
            public void run() {
                ArrayList<Seccion> result = new ArrayList<>();
                sql = "SELECT id_seccion, id_servicio, instrucciones, nombre FROM qa_sec WHERE id_servicio = '"+id_servicio+"' AND status = 'ALTA' ORDER BY orden ASC";
                cursor = db.rawQuery(sql, null);
                while (cursor.moveToNext()) {
                    result.add(new Seccion(cursor.getInt(0), cursor.getInt(1), cursor.getString(2),cursor.getString(3)));
                }
                adaptador = new AdapterSeccion(EvaluacionSecc.this, result, idE, id_evaluacion);
                recyclerViewSecc.setHasFixedSize(true);
                recyclerViewSecc.setLayoutManager(new LinearLayoutManager(EvaluacionSecc.this, LinearLayoutManager.VERTICAL, false));
                recyclerViewSecc.setAdapter(adaptador);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //linearPIndSections.setVisibility(View.GONE);
                        //scrollViewSections.setVisibility(View.VISIBLE);
                    }
                }, 500);
            }
        });
    }

    @Override
    protected void onStart() {
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
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK) {

        }
        return false;
    }

}