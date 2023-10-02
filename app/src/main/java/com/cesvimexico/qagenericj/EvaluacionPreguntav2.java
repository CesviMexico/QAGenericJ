package com.cesvimexico.qagenericj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.cesvimexico.qagenericj.db.DBManager;
import com.cesvimexico.qagenericj.model.EvaluacionData;
import com.cesvimexico.qagenericj.ui.main.EvaluacionPreguntav2Fragment;

public class EvaluacionPreguntav2 extends AppCompatActivity {

    private int id_servicio;

    private int id_area;
    private String idE;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluacion_preguntav2);


        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        preferences = getSharedPreferences("usrdata", Context.MODE_PRIVATE);

        this.id_servicio = getIntent().getExtras().getInt("id_servicio");
        this.idE = getIntent().getExtras().getString("idE");
        EvaluacionData evalData = new EvaluacionData(this, idE);
        this.id_area = evalData.getId_area();
        int id_seccion = getIntent().getExtras().getInt("id_seccion");

        // dbFB = FirebaseFirestore.getInstance();
        DBManager bdm = new DBManager(this);

        SQLiteDatabase db = bdm.getWritableDatabase();

        String sql = "SELECT `nombre` FROM `qa_sec` WHERE `id_seccion` = '" + id_seccion + "' AND `id_servicio` = '" + id_servicio + "'";
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            setTitle(cursor.getString(0));
        }


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, EvaluacionPreguntav2Fragment.newInstance())
                    .commitNow();
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
        /*Intent intent = new Intent(this, EvaluacionSecc.class);
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
}