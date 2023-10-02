package com.cesvimexico.qagenericj;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ScrollView;

import androidx.appcompat.app.AppCompatActivity;

//import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cesvimexico.qagenericj.adapters.AdapterArea;
import com.cesvimexico.qagenericj.adapters.AdapterService;
import com.cesvimexico.qagenericj.db.DBManager;
import com.cesvimexico.qagenericj.model.Area;
import com.cesvimexico.qagenericj.model.Service;
import com.google.android.material.progressindicator.LinearProgressIndicator;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//import com.cesvimexico.qagenericj.databinding.ActivityMainEvalBinding;

public class MainServices extends AppCompatActivity {

    //private AppBarConfiguration appBarConfiguration;
    //private ActivityMainEvalBinding binding;

    private LinearProgressIndicator linearPIndSections;
    private ScrollView scrollViewSections;
    private RecyclerView recyclerViewServices;
    private DBManager bdm;
    private SQLiteDatabase db;
    private String sql;
    private Cursor cursor;
    private AdapterService adaptador;
    private final int columns = 2;
    private int id_area;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_services);


        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        setContentView(R.layout.activity_main_services);
        bdm = new DBManager(MainServices.this);
        db = bdm.getWritableDatabase();

        this.id_area = getIntent().getExtras().getInt("id_area");

        recyclerViewServices = findViewById(R.id.recyclerViewServicesS);
        linearPIndSections = findViewById(R.id.linearPIndSectionsS);
        scrollViewSections = findViewById(R.id.scrollViewSectionsS);
        linearPIndSections.setVisibility(View.VISIBLE);
        scrollViewSections.setVisibility(View.GONE);

        executor.execute(new Runnable() {

            @Override
            public void run() {

                ArrayList<Service> result = new ArrayList<>();
                sql = "SELECT id_servicio, id_area, servicio, descripcion,instrucciones, status FROM qa_serv WHERE status = 'ALTA' AND id_area="+id_area;
                cursor = db.rawQuery(sql, null);
                while (cursor.moveToNext()) {

                    result.add(new Service(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5)));

                }
                adaptador = new AdapterService(MainServices.this, result);
                recyclerViewServices.setHasFixedSize(true);
                //recyclerViewKORE.setLayoutManager(new LinearLayoutManager(SectionsActivity.this, LinearLayoutManager.HORIZONTAL, false));
                recyclerViewServices.setLayoutManager(new GridLayoutManager(MainServices.this, columns, GridLayoutManager.VERTICAL, false));
                recyclerViewServices.setAdapter(adaptador);
                cursor.close();
                db.close();

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        linearPIndSections.setVisibility(View.GONE);
                        scrollViewSections.setVisibility(View.VISIBLE);

                    }
                }, 500);

            }
        });

    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == event.KEYCODE_BACK) {
//
//        }
//        return false;
//        //return super.onKeyDown(keyCode, event);
//    }


}