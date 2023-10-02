package com.cesvimexico.qagenericj;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.cesvimexico.qagenericj.Camera.CameraActivity;
import com.cesvimexico.qagenericj.adapters.AdapterCamp;
import com.cesvimexico.qagenericj.db.DBManager;
import com.cesvimexico.qagenericj.model.Campo;
import com.cesvimexico.qagenericj.model.EvaluacionData;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class EvaluacionForm extends AppCompatActivity {

    private int id_servicio;
    private String idE;
    private String sql;
    private String email;
    private RecyclerView recyclerViewForm;
    private FloatingActionButton buttonGuardarServ;
    private SharedPreferences preferences;
    private Cursor cursor;
    private DBManager bdm;
    private SQLiteDatabase db;
    private FirebaseFirestore dbFB;
    private String TAG = "EVALUACION";

    private String TipoServicio;

    private int id_evaluacion;
    private FloatingActionButton btn_tk_photo_cia;
    private String nombrePic;

    private Uri photoURI;
    private File photoFile;
    private Bitmap bm;

    private ImageView imageFront, imageSign;

    private FirebaseStorage storage;
    private StorageReference storageRef;
    private int id_area;

    private boolean nueva = false;
    private String front_pic, sign_eval;
    private String nombrePicPrincipal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluacion_form);


        recyclerViewForm = findViewById(R.id.recyclerViewForm);
        buttonGuardarServ = findViewById(R.id.buttonGuardarServ);
        btn_tk_photo_cia = findViewById(R.id.btn_tk_photo_eva);
        imageFront = findViewById(R.id.imageFront);
        imageSign = findViewById(R.id.imageSign);
        imageSign.setVisibility(View.GONE);


        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        preferences = getSharedPreferences("usrdata", Context.MODE_PRIVATE);
        email = preferences.getString("email", "");
        this.id_servicio = getIntent().getExtras().getInt("id_servicio");
        this.id_area = getIntent().getExtras().getInt("id_area");
        this.idE = getIntent().getExtras().getString("idE");
        this.TipoServicio = getIntent().getExtras().getString("TipoServicio");

        EvaluacionData evalData = new EvaluacionData(EvaluacionForm.this, idE);

        dbFB = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        bdm = new DBManager(EvaluacionForm.this);
        db = bdm.getWritableDatabase();

        if (savedInstanceState != null) {
            idE = savedInstanceState.getString("idE");
            nombrePic = savedInstanceState.getString("nombrePic");
            if (nombrePic != null) {
                bm = showAndScale();
                imageFront.setImageBitmap(bm);
            }
        }




        sql = "SELECT pic, sign AS total FROM `qa_zeval` WHERE id_evaluacion_fb= '" + idE + "'";
        cursor = db.rawQuery(sql, null);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                front_pic = cursor.getString(0);
                sign_eval = cursor.getString(1);
            }
        }


        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM-yyyy HH:mm", Locale.getDefault());
        Date date = new Date();
        String fecha = dateFormat.format(date);


            if(front_pic != null){
                File fileCapturado = new File(EvaluacionForm.this.getExternalFilesDir(Environment.DIRECTORY_PICTURES), front_pic);
                if(fileCapturado.exists()){
                    Bitmap bitmap = BitmapFactory.decodeFile(String.valueOf(fileCapturado));
                    if(bitmap != null){
                        imageFront.setImageBitmap(bitmap);
                        //imageFront.set
                    }
                }
            }

        if(sign_eval != null){
            File fileCapturado = new File(EvaluacionForm.this.getExternalFilesDir(Environment.DIRECTORY_PICTURES), sign_eval);
            if(fileCapturado.exists()){
                Bitmap bitmap = BitmapFactory.decodeFile(String.valueOf(fileCapturado));
                if(bitmap != null){
                    imageSign.setImageBitmap(bitmap);
                    imageSign.setVisibility(View.VISIBLE);
                }
            }
        }






        cursor = db.rawQuery(
                "SELECT id_evaluacion_fb FROM qa_zeval WHERE id_evaluacion_fb = " + idE,
                null);

        if (cursor.getCount() == 0) {
            sql = "INSERT INTO qa_zeval(id_evaluacion_fb, id_usuario, id_servicio, f_creada ) VALUES ('" + idE + "','" + email + "','" + id_servicio + "','" + fecha + "' )";
            db.execSQL(sql);
            evalData = new EvaluacionData(EvaluacionForm.this, idE);
            nueva = true;
        }

        id_evaluacion = evalData.getId_evaluacion();


        ArrayList<Campo> result = new ArrayList<>();

        cursor = db.rawQuery(
                "SELECT id_campo,id_servicio,categoria,tipo,etiqueta,mostrar,status,long,orden FROM qa_camp WHERE id_servicio = " + id_servicio + " AND status='ALTA' AND tipo<>'CANVAS' ",
                null);
        while (cursor.moveToNext()) {
            result.add(
                    new Campo(cursor.getInt(0),
                            cursor.getInt(1),
                            cursor.getString(2),
                            cursor.getString(3),
                            cursor.getString(4),
                            cursor.getInt(5),
                            cursor.getString(6),
                            cursor.getInt(7),
                            cursor.getInt(8)
                    ));
        }
        AdapterCamp adaptador = new AdapterCamp(EvaluacionForm.this, result, idE, id_evaluacion);

        recyclerViewForm.setHasFixedSize(true);
        recyclerViewForm.setLayoutManager(new LinearLayoutManager(EvaluacionForm.this, LinearLayoutManager.VERTICAL, false));
        //recyclerViewForm.setLayoutManager(new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false));
        recyclerViewForm.setAdapter(adaptador);


        buttonGuardarServ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Map<String, Object> servicio = new HashMap<>();
                String estado = "", municipio = "", lugar = "";


                cursor = db.rawQuery(
                        "SELECT nom_campo_bd, tipo, valor_cadena, valor_fecha, valor_numerico , nom_campo_bd\n" +
                                "FROM\n" +
                                "  `qa_zeval`\n" +
                                "  INNER JOIN `qa_zeval_det` ON (`qa_zeval`.`id_evaluacion` = `qa_zeval_det`.`id_evaluacion`)\n" +
                                "  INNER JOIN `qa_camp` ON (`qa_zeval`.`id_servicio` = `qa_camp`.`id_servicio`)\n" +
                                "  AND (`qa_camp`.`id_campo` = `qa_zeval_det`.`id_campo`) AND `nom_campo_bd` IS NOT NULL  AND `nom_campo_bd` <> 'null'" +
                                "WHERE\n" +
                                "  `qa_zeval`.`id_evaluacion_fb` = '" + idE + "'",
                        null);
                while (cursor.moveToNext()) {
                    String nom_campo_bd = cursor.getString(5);


                    switch (cursor.getString(1)) {
                        case "DATETIME":
                        case "DATE":
                            servicio.put(cursor.getString(0), cursor.getString(3));
                            break;
                        case "TEXT":
                        case "SELECT":
                            if (cursor.getString(0).equals("estado")) {
                                estado = cursor.getString(2);
                            } else if (cursor.getString(0).equals("municipio")) {
                                municipio = cursor.getString(2);
                            } else {
                                servicio.put(cursor.getString(0), cursor.getString(2));
                            }
                            break;
                        case "NUMB":
                            servicio.put(cursor.getString(0), cursor.getString(4));
                            break;
                    }
                }
                lugar = String.join(", ", estado, municipio);
                servicio.put("lugar", lugar);

                servicio.put("idE", idE);

                if (nueva) {
                    servicio.put("status", "CREADO");
                    servicio.put("f_crea", fecha);
                    servicio.put("avance", 0);
                }
                servicio.put("id_usuario", email);
                servicio.put("id_servicio", id_servicio);
                servicio.put("id_evaluacion", id_evaluacion);

                if (nombrePic != null) {
                    storageRef = storage.getReference();
                    StorageReference imgFront = storageRef.child("images/" + nombrePic);

                    Uri uri = Uri.fromFile(new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), nombrePic));
                    UploadTask uploadTask = imgFront.putFile(uri);
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                            StorageReference file = storageRef.child("images/" + nombrePicPrincipal);
                            file.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    //Toast.makeText(getApplicationContext(), "Se ha actualizado la fotografía", Toast.LENGTH_LONG).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    //Toast.makeText(getApplicationContext(), "Error al intentar actualizar la fotografía", Toast.LENGTH_LONG).show();
                                }
                            });


                        }
                    });
                    servicio.put("pic", nombrePic);
                }
                Task<Void> capitalCities = dbFB.collection(TipoServicio).document(idE).set(servicio, SetOptions.merge());
/*
AGREGAR UN ONFOCUS PARA QUE GUARDE EL DATO AL DAR CLIC EN EL BOTON
 */


                openActivity(true);

            }
        });


        btn_tk_photo_cia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    photoFile = createImageFile();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                enableCamera();

            }

        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2) {

            Intent data2 = data;
            nombrePic = data.getStringExtra("nombrePic");

            Log.w("okoko", "onActivityResult .......");
            bm = showAndScale();
            imageFront.setImageBitmap(bm);
        }

    }

    private Bitmap showAndScale() {
        File imgFile = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), nombrePic);
        if (imgFile.exists()) {
            bm = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            nombrePicPrincipal = nombrePic;

        }
        return bm;
    }

    private File createImageFile() throws IOException {
        nombrePic = "";
        String imageFileName = "FRONT_";
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",   /* suffix */
                getExternalFilesDir(Environment.DIRECTORY_PICTURES)  /* directory */
        );

        this.nombrePic = image.getName();
        return image;
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

                openActivity(false);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openActivity(boolean option) {
        Intent intent = new Intent(EvaluacionForm.this, EvaluacionList.class);
        intent.putExtra("id_area", this.id_area);
        intent.putExtra("id_servicio", this.id_servicio);
        if (option) {
            ProgressDialog progressBar;
            progressBar = new ProgressDialog(EvaluacionForm.this, R.style.MyAlertDialogStyle);


            progressBar.setCancelable(false);
            progressBar.setTitle("Actualizando");

            progressBar.setMessage("Espere...");
            progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressBar.setProgress(0);
            progressBar.setMax(100);
            progressBar.show();


            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressBar.incrementProgressBy(50);
                    progressBar.dismiss();
                    Toast.makeText(EvaluacionForm.this, "Los datos se han guardado con éxito!", Toast.LENGTH_SHORT).show();
                    //startActivity(intent);
                    finish();
                }
            }, 5000);
        } else {
            //startActivity(intent);
            finish();
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK) {

        }
        return false;
    }


    private void enableCamera() {
        Intent intent = new Intent(this, CameraActivity.class);
        intent.putExtra("nombrePic", nombrePic);
        intent.putExtra("tipoFoto", "principal");
        intent.putExtra("idE", idE);
        intent.putExtra("idPregunta", "");
        //startActivity(intent);

        startActivityForResult(intent, 2);
    }

}