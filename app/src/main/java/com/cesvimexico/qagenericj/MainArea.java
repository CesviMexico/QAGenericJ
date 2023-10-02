package com.cesvimexico.qagenericj;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.cesvimexico.qagenericj.adapters.AdapterArea;
import com.cesvimexico.qagenericj.db.DBManager;
import com.cesvimexico.qagenericj.login.DataUserActivity;
import com.cesvimexico.qagenericj.login.LoginActivity;
import com.cesvimexico.qagenericj.model.Area;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.progressindicator.LinearProgressIndicator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ScrollView;
import android.widget.Toast;

//import androidx.navigation.ui.AppBarConfiguration;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cesvimexico.qagenericj.databinding.ActivityMainServicesBinding;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainArea extends AppCompatActivity {

    //private AppBarConfiguration appBarConfiguration;
    private ActivityMainServicesBinding binding;
    private LinearProgressIndicator linearPIndSections;
    private ScrollView scrollViewSections;
    private RecyclerView recyclerViewServices;
    private DBManager bdm;
    private SQLiteDatabase db;
    private String sql;
    private Cursor cursor;
    private AdapterArea adaptador;
    private final int columns = 3;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_services, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.rbd:

                return true;

            case R.id.upDataB:


                return true;
            case R.id.close_session:

                new MaterialAlertDialogBuilder(MainArea.this)

                        .setIcon(R.drawable.ic_baseline_account_circle_24)
                        .setTitle("Cerrar sesión")
                        .setMessage("Si no cuenta con internet no podrá iniciar sesión nuevamente.")
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                SharedPreferences preferences;
                                preferences = PreferenceManager.getDefaultSharedPreferences(MainArea.this);
                                preferences = getSharedPreferences("usrdata", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();
                                //editor.putString("uid", currentUser.getUid());
                                editor.putString("email", "");
                                editor.apply();

                                Intent intent = new Intent(MainArea.this, LoginActivity.class);
                                startActivity(intent);
                                //dbSql.close();
                                //bdm.close();
                                finish();

                                Intent mainActivity = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(mainActivity);
                                MainArea.this.finish();
                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();


                return true;
            case R.id.misdatos:
                Intent intent = new Intent(MainArea.this, DataUserActivity.class);
                startActivity(intent);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void upDateProcess(SQLiteDatabase db) {
        //bdm.onUpgrade(db, 1, 0);
        Toast.makeText(getApplicationContext(), "La base de datos ha sido actualizada", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1 /* El codigo que puse a mi request */: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // aqui ya tengo permisos
                } else {
                    // aqui no tengo permisos
                }
                return;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();StrictMode.setThreadPolicy(policy);



        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        bdm = new DBManager(MainArea.this);


        ActivityCompat.requestPermissions(MainArea.this,
                new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA

                }, /* Este codigo es para identificar tu request */ 1);


        //bdm.onUpgrade( db, 1,0);

        recyclerViewServices = findViewById(R.id.recyclerViewServices);
        linearPIndSections = findViewById(R.id.linearPIndSections);
        scrollViewSections = findViewById(R.id.scrollViewSections);
        linearPIndSections.setVisibility(View.VISIBLE);
        scrollViewSections.setVisibility(View.GONE);




    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            //dbSql.close();
            //cursor.close();
            //bdm.close();
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //ProDialog.show();
        //progressBar.setProgress(0);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        db = bdm.getWritableDatabase();

        ArrayList<Area> result = new ArrayList<>();
        sql = "SELECT id_area, area, status FROM qa_area WHERE status = 'ALTA' ";
        cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {

            result.add(new Area(cursor.getInt(0), cursor.getString(1), cursor.getString(2)));
        }
        adaptador = new AdapterArea(MainArea.this, result);
        recyclerViewServices.setHasFixedSize(true);
        //recyclerViewKORE.setLayoutManager(new LinearLayoutManager(SectionsActivity.this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewServices.setLayoutManager(new GridLayoutManager(MainArea.this, columns, GridLayoutManager.VERTICAL, false));
        recyclerViewServices.setAdapter(adaptador);
        cursor.close();

        linearPIndSections.setVisibility(View.GONE);
        scrollViewSections.setVisibility(View.VISIBLE);


      /*  ExecutorService executor = Executors.newSingleThreadExecutor();
       Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(new Runnable() {

            @Override
            public void run() {
                ArrayList<Area> result = new ArrayList<>();
                sql = "SELECT id_area, area, status FROM qa_area WHERE status = 'ALTA' ";
                cursor = db.rawQuery(sql, null);
                while (cursor.moveToNext()) {

                    result.add(new Area(cursor.getInt(0), cursor.getString(1), cursor.getString(2)));
                }
                adaptador = new AdapterArea(MainArea.this, result);
                recyclerViewServices.setHasFixedSize(true);
                //recyclerViewKORE.setLayoutManager(new LinearLayoutManager(SectionsActivity.this, LinearLayoutManager.HORIZONTAL, false));
                recyclerViewServices.setLayoutManager(new GridLayoutManager(MainArea.this, columns, GridLayoutManager.VERTICAL, false));
                recyclerViewServices.setAdapter(adaptador);
                cursor.close();


                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        linearPIndSections.setVisibility(View.GONE);
                        scrollViewSections.setVisibility(View.VISIBLE);
                    }
                }, 500);

            }
        }); */

    }

    @Override
    protected void onStop() {
        super.onStop();
        db.close();
    }
}