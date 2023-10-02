package com.cesvimexico.qagenericj;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.cesvimexico.qagenericj.Camera.CameraActivity;
import com.cesvimexico.qagenericj.db.DBManager;
import com.cesvimexico.qagenericj.Http.Api;
import com.cesvimexico.qagenericj.Http.ApiFotos;
import com.cesvimexico.qagenericj.model.Evaluacion;

import com.cesvimexico.qagenericj.model.EvaluacionData;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.google.android.material.badge.BadgeDrawable;

public class EvaluacionList extends AppCompatActivity {

    private final String TAG = "EVALUACION";
    private String TipoServicio = "servicio";
    private FirebaseFirestore dbFb;
    private FirestoreRecyclerAdapter adapter;
    RecyclerView firestoreList;
    private RecyclerView recyclerView;

    private SharedPreferences preferences;

    private String email;
    private int id_area;
    private int id_servicio;
    private FirebaseStorage storage;
    private StorageReference storageRef;

    private ProgressBar progressCircEnvLE;
    private TextView textLoading;
    ProgressDialog progressDialog;
    TextView textViewTitulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluacion_list);
        try {
            dbFb = FirebaseFirestore.getInstance();

            FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                    .setPersistenceEnabled(true)
                    .build();
            dbFb.setFirestoreSettings(settings);


            storage = FirebaseStorage.getInstance();
            storageRef = storage.getReference();
            //StorageReference storageReference = FirebaseStorage.getInstance().getReference();

            recyclerView = findViewById(R.id.rvEvaluacionesList);

            this.id_area = getIntent().getExtras().getInt("id_area");
            this.id_servicio = getIntent().getExtras().getInt("id_servicio");
            this.TipoServicio = getIntent().getExtras().getString("tipo_servicio");

            preferences = PreferenceManager.getDefaultSharedPreferences(this);
            preferences = getSharedPreferences("usrdata", Context.MODE_PRIVATE);
            email = preferences.getString("email", "");

            textViewTitulo = findViewById(R.id.textView);

            textViewTitulo.setText("Servicios en proceso");

            Query query = dbFb.collection(TipoServicio)
                    .whereEqualTo("id_usuario", email) /// email
                    .whereEqualTo("id_servicio", id_servicio) //
                    .whereIn("status", Arrays.asList("CREADO", "INICIADO", "TERMINADO"));


            FirestoreRecyclerOptions<Evaluacion> options = new FirestoreRecyclerOptions.Builder<Evaluacion>().setQuery(query, Evaluacion.class).build();

            inicializarAdapter(options);

            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            adapter.startListening();
            recyclerView.setAdapter(adapter);


        } catch (
                Exception ex) {
            Log.d(TAG, ex.getMessage());
        }



        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                String dato  = "";
                dato  = item.toString();
                Query query = null;
                if(dato.equals("En proceso")){
                    textViewTitulo.setText("Servicios en proceso");
                     query = dbFb.collection(TipoServicio)
                            .whereEqualTo("id_usuario", email) /// email
                            .whereEqualTo("id_servicio", id_servicio) //
                            .whereIn("status", Arrays.asList("CREADO", "INICIADO", "TERMINADO"))
                            .orderBy("idE", Query.Direction.DESCENDING);

                }else{
                    textViewTitulo.setText("Servicios enviados");
                    query = dbFb.collection(TipoServicio)
                            .whereEqualTo("id_usuario", email)
                            .whereEqualTo("id_servicio", id_servicio)
                            .whereEqualTo("status", "ENVIADO")
                            .orderBy("idE", Query.Direction.DESCENDING);
                }
     // .whereIn("status", Arrays.asList("CREADO", "INICIADO", "TERMINADO"));

                adapter = null;
                FirestoreRecyclerOptions<Evaluacion> options = new FirestoreRecyclerOptions.Builder<Evaluacion>().setQuery(query, Evaluacion.class).build();

                inicializarAdapter(options);

                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(EvaluacionList.this));
                adapter.startListening();
                recyclerView.setAdapter(adapter);




                return true;
            }
        });


    }

    public void inicializarAdapter(FirestoreRecyclerOptions<Evaluacion> options){

        adapter = new FirestoreRecyclerAdapter<Evaluacion, EvaluacionViewHolder>(options) {
            @NonNull
            @Override
            public EvaluacionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_evaluacion, parent, false);
                return new EvaluacionViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull EvaluacionViewHolder holder, int position, @NonNull Evaluacion model) {
                holder.idE.setText(model.getIdE());
                holder.evaluado.setText(model.getEvaluado());
                holder.avance.setText(String.valueOf(model.getAvance()) + " %");
                //holder.avance.setText("100%");


                if(model.getStatus().equals("CREADO")){
                    holder.textView15LE.setText("Iniciará: ");
                    holder.f_ini.setText(model.getF_ini());
                }
                else{
                    holder.textView15LE.setText("Inició: ");
                    holder.f_ini.setText(model.getF_ini_eval());
                }

                //holder.f_fin.setText(model.getF_fin());
                holder.empresa.setText(model.getEmpresa());
                holder.btn_Firmar.setVisibility(View.GONE);
                holder.btn_Firmar.setEnabled(true);

                holder.btn_Firmar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(EvaluacionList.this, Signature.class);
                        intent.putExtra("idE", model.getIdE());
                        intent.putExtra("id_area", id_area);
                        intent.putExtra("id_servicio", id_servicio);
                        intent.putExtra("TipoServicio", TipoServicio);
                        startActivity(intent);
                    }
                });

                holder.evalua_cardLE.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (!model.getStatus().equals("ENVIADO")){
                            Intent intent = new Intent(EvaluacionList.this, EvaluacionForm.class);
                            intent.putExtra("id_servicio", model.getId_servicio());
                            intent.putExtra("idE", model.getIdE());
                            intent.putExtra("id_area", id_area);
                            intent.putExtra("id_servicio", id_servicio);
                            intent.putExtra("TipoServicio", TipoServicio);


                            startActivity(intent);

                        }



                    }
                });


                EvaluacionData evData = new EvaluacionData(EvaluacionList.this, model.getIdE());
                Map<String, Object> servicio = new HashMap<>();
                int avance = evData.calPrcAvance();

                servicio.put("avance", avance);
                Log.d(TAG, "aVANCE EN FB: " + avance);

                if (avance >= 10) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM-yyyy HH:mm", Locale.getDefault());
                    Date date = new Date();
                    String fecha = dateFormat.format(date);

                    //FirebaseFirestore dbFBL;
                    DBManager bdm;
                    SQLiteDatabase db;

                    bdm = new DBManager(EvaluacionList.this);
                    db = bdm.getWritableDatabase();
                    JSONObject JSONDataServ = new JSONObject();

                    String sql = "SELECT `sign` FROM `qa_zeval` WHERE `qa_zeval`.`id_evaluacion_fb` = '" + model.getIdE() + "'  ";
                    Log.d(TAG, sql);
                    Cursor cursor = db.rawQuery(sql, null);
                    if (cursor.getCount() > 0) {
                        while (cursor.moveToNext()) {
                            if (cursor.getString(0) == null) {
                                holder.btn_Firmar.setVisibility(View.VISIBLE);
                            } else {
                                holder.btn_Firmar.setEnabled(false);
                            }
                        }
                    }

                        /*
                        Esta fecha indica cuando se completo el 100%
                         */
                    if(model.getF_fin_eval()==null || model.getF_fin_eval().equals("")){

                        servicio.put("f_fin_eval", fecha );
                    }


                }
                dbFb.collection(TipoServicio).document(model.getIdE()).set(servicio, SetOptions.merge());



                switch (model.getStatus()) {
                    case "CREADO":
                        holder.btn_enviarLE.setText("INICIAR");

                        holder.btn_enviarLE.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                new MaterialAlertDialogBuilder(EvaluacionList.this)
                                        .setIcon(R.drawable.ic_baseline_assignment_24)
                                        .setTitle("Iniciar")
                                        .setMessage("Esta seguro que desea iniciar el servicio o evaluación?")
                                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                                FirebaseFirestore dbFBL;
                                                DBManager bdm;
                                                SQLiteDatabase db;
                                                dbFBL = FirebaseFirestore.getInstance();
                                                bdm = new DBManager(EvaluacionList.this);
                                                db = bdm.getWritableDatabase();
                                                Map<String, Object> servicio = new HashMap<>();
                                                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM-yyyy HH:mm", Locale.getDefault());
                                                Date date = new Date();
                                                String fecha = dateFormat.format(date);
                                                servicio.put("f_ini_eval", fecha);
                                                servicio.put("status", "INICIADO");
                                                dbFBL.collection(TipoServicio).document(model.getIdE()).set(servicio, SetOptions.merge());
                                                String sql = "UPDATE qa_zeval SET status='INICIADO' WHERE id_evaluacion_fb = '" + model.getIdE() + "'";
                                                db.execSQL(sql);
                                                Intent intent = new Intent(EvaluacionList.this, EvaluacionSecc.class);
                                                intent.putExtra("idE", model.getIdE());
                                                intent.putExtra("id_area", id_area);
                                                intent.putExtra("id_servicio", model.getId_servicio());
                                                startActivity(intent);

                                            }
                                        })
                                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                //Log.d("MainActivity", "Aborting mission...");
                                            }
                                        })
                                        .show();

                            }
                        });


                        break;
                    case "INICIADO":
                        holder.btn_enviarLE.setText("CONTINUAR");

                        holder.btn_enviarLE.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(EvaluacionList.this, EvaluacionSecc.class);
                                intent.putExtra("idE", model.getIdE());
                                intent.putExtra("id_area", id_area);
                                intent.putExtra("id_servicio", model.getId_servicio());
                                startActivity(intent);

                            }
                        });

                        break;
                    case "TERMINADO":
                        holder.btn_enviarLE.setText("ENVIAR");
                        holder.btn_Firmar.setVisibility(View.VISIBLE);
                        holder.btn_Firmar.setEnabled(false);
                        holder.btn_enviarLE.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {


                                if (model.getSign() == null || model.getSign().equals("")) {
                                    new MaterialAlertDialogBuilder(EvaluacionList.this)
                                            .setIcon(R.drawable.ic_baseline_assignment_late_24)
                                            .setCancelable(false)
                                            .setTitle("Firma")
                                            .setMessage("Es necesaria la firma de la persona evaluada antes de enviar la información.")
                                            .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                }
                                            })
                                            .show();
                                    return;
                                }else{
                                    SincronizarValidaConexion(evData);
                                }




                            }
                        });


                        break;
                    case "ENVIADO":
                        holder.btn_enviarLE.setText("VER REPORTE");
                        holder.btn_Firmar.setVisibility(View.GONE);
                        holder.btn_enviarLE.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String codAcces = model.getCode_access();
                                String URL = "";
                                if(id_servicio == 1){
                                    URL = "https://appweb.cesvimexico.com.mx/LevInfoCesvi/assets/ScripWeb/reportPDF/PDFPruebaMajeno.php?code_acces="+codAcces;
                                }else if(id_servicio == 3){
                                    URL = "https://appweb.cesvimexico.com.mx/LevInfoCesvi/assets/ScripWeb/reportPDF/PDFEvaluacionBAJAJ.php?code_acces="+codAcces;
                                }

                                Uri uri = Uri.parse(URL);
                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                startActivity(intent);
                            }
                        });
                        break;
                }


                holder.evalua_cardLE.setOnLongClickListener(new View.OnLongClickListener() {


                    @Override
                    public boolean onLongClick(View v) {

                        new MaterialAlertDialogBuilder(EvaluacionList.this)
                                .setIcon(R.drawable.ic_baseline_delete_sweep_24)
                                .setCancelable(false)
                                .setTitle("¿Desea borrar el servicio?")
                                .setMessage("Sí borra este servicio, la información relacionada será borrada del dispositivo y no se podrá recuperar, ¿desea continuar?")
                                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        dbFb.collection(TipoServicio).document(model.getIdE())
                                                .delete()
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Log.d(TAG, "DocumentSnapshot successfully deleted!");
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Log.w(TAG, "Error deleting document", e);
                                                    }
                                                });
                                    }
                                })
                                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //Log.d("MainActivity", "Aborting mission...");
                                    }
                                })
                                .show();
                        return false;
                    }
                });




                String nombrePic = model.getPic();
                if(nombrePic != null){
                    File fileCapturado = new File(EvaluacionList.this.getExternalFilesDir(Environment.DIRECTORY_PICTURES), nombrePic);
                    if(fileCapturado.exists()){
                        Bitmap bitmap = BitmapFactory.decodeFile(String.valueOf(fileCapturado));
                        if(bitmap != null){
                            holder.imgFrontLE.setImageBitmap(bitmap);
                        }
                    }
                }







            }

            @Override
            public void onError(FirebaseFirestoreException e) {
                Log.d(TAG, e.getMessage());
            }
        }

        ;
    }


    public void SincronizarValidaConexion (EvaluacionData evData){
        ConnectivityManager cm =
                (ConnectivityManager) EvaluacionList.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean isMetered = cm.isActiveNetworkMetered();
        if (isMetered) {
            new MaterialAlertDialogBuilder(EvaluacionList.this)
                    .setIcon(R.drawable.ic_baseline_wifi_off_24)
                    .setCancelable(false)
                    .setTitle("Wi-Fi")
                    .setMessage("Se recomienda tener conexión Wifi para subir la información, ¿Desea subir la información con los datos de su celular?.")
                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            SincronizarValidaConfirmacin(evData);
                        }
                    }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog,
                                            int which) {
                            //Log.d("MainActivity", "Aborting mission...");
                        }
                    })
                    .show();
            return;
        }else{
            SincronizarValidaConfirmacin(evData);
        }
    }

    public void SincronizarValidaConfirmacin (EvaluacionData evData){


        new MaterialAlertDialogBuilder(EvaluacionList.this)
                .setIcon(R.drawable.ic_baseline_assignment_24)

                .setTitle("Terminar")
                .setMessage("Esta seguro que desea TERMINAR el servicio o evaluación?")
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SincronizarEnviaDatos(evData);
                    }
                }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        //Log.d("MainActivity", "Aborting mission...");
                    }
                })
                .show();
    }

    public void SincronizarEnviaDatos (EvaluacionData evData){
        progressDialog = new ProgressDialog(EvaluacionList.this);
        progressDialog = new ProgressDialog(EvaluacionList.this, R.style.MyAlertDialogStyle);
        progressDialog.setMax(100);
        progressDialog.isShowing();
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Enviando información...");
        progressDialog.setTitle("Espere");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.show();
        new Thread(
                new Runnable() {
                    @SuppressLint("Range")
                    @Override
                    public void run() {
                        FirebaseFirestore dbFBL;
                        DBManager bdm;
                        SQLiteDatabase db;
                        //progressCircEnvLE.setVisibility(View.VISIBLE);
                        bdm = new DBManager(EvaluacionList.this);
                        db = bdm.getWritableDatabase();
                        JSONObject JSONDataServ = new JSONObject();
                        String idServicio = "";
                        try {
                            String SqlDGral = "SELECT    `id_evaluacion`,  `qa_zeval`.`id_evaluacion_fb`, `qa_zeval`.`pic`, `qa_zeval`.`sign`,    `qa_zeval`.`id_servicio`,    `qa_zeval`.`id_usuario`, `qa_zeval`.`lugar`   FROM    `qa_zeval`  WHERE    `qa_zeval`.`id_evaluacion` = '" + evData.getId_evaluacion() + "'  ";
                            Cursor cursor = db.rawQuery(SqlDGral, null);
                            String IdEvaluacion = "";
                            while (cursor.moveToNext()) {
                                IdEvaluacion = cursor.getString(cursor.getColumnIndex("id_evaluacion"));
                                JSONDataServ.put("id_evaluacion_fb", cursor.getString(cursor.getColumnIndex("id_evaluacion_fb")));
                                JSONDataServ.put("id_servicio", cursor.getString(cursor.getColumnIndex("id_servicio")));
                                JSONDataServ.put("id_usuario", cursor.getString(cursor.getColumnIndex("id_usuario")));
                                JSONDataServ.put("pic", cursor.getString(cursor.getColumnIndex("pic")));
                                JSONDataServ.put("sign", cursor.getString(cursor.getColumnIndex("sign")));
                                JSONDataServ.put("lugar", cursor.getString(cursor.getColumnIndex("lugar")));
                                idServicio = cursor.getString(cursor.getColumnIndex("id_servicio"));
                            }
                            cursor.close();
                            progressDialog.incrementProgressBy(20);
                            Thread.sleep(1000);
                            String SqlDetalle = "SELECT    `qa_zeval_det`.`id_campo`,   `qa_camp`.`tipo`,   `qa_zeval_det`.`valor_numerico`,   `qa_zeval_det`.`valor_fecha`,   `qa_zeval_det`.`valor_cadena` FROM   `qa_zeval_det`   INNER JOIN `qa_camp` ON (`qa_zeval_det`.`id_campo` = `qa_camp`.`id_campo`) WHERE   `qa_zeval_det`.`id_evaluacion` = '" + IdEvaluacion + "' ";
                            cursor = db.rawQuery(SqlDetalle, null);
                            JSONArray JSONDataServDet = new JSONArray();
                            while (cursor.moveToNext()) {
                                JSONObject Detalle = new JSONObject();
                                Detalle.put("id_campo", cursor.getString(cursor.getColumnIndex("id_campo")));
                                Detalle.put("tipo", cursor.getString(cursor.getColumnIndex("tipo")));
                                if (cursor.getString(cursor.getColumnIndex("tipo")).equals("NUMB")) {
                                    Detalle.put("valor", cursor.getString(cursor.getColumnIndex("valor_numerico")));
                                } else if (cursor.getString(cursor.getColumnIndex("tipo")).equals("SELECT") || cursor.getString(cursor.getColumnIndex("tipo")).equals("TEXT")) {
                                    Detalle.put("valor", cursor.getString(cursor.getColumnIndex("valor_cadena")));
                                } else {
                                    Detalle.put("valor", cursor.getString(cursor.getColumnIndex("valor_fecha")));
                                }
                                JSONDataServDet.put(Detalle);
                            }
                            cursor.close();
                            progressDialog.incrementProgressBy(20);
                            Thread.sleep(1000);
                            JSONDataServ.put("datos", JSONDataServDet);
                            String SqlResp = "SELECT    `qa_pre`.`id_pregunta`,   `qa_zeval_resp`.`id_respuesta`,   `qa_zeval_resp`.`respuesta`,  CASE WHEN `qa_rsp`.`respuesta` IS NULL THEN `qa_zeval_resp`.`respuesta` ELSE `qa_rsp`.`respuesta` END AS data_cat_respuesta,   `qa_zeval_resp`.`aplica`,   COALESCE(`qa_zeval_resp`.`comentario`, '') as comentario FROM   `qa_pre`  INNER JOIN `qa_sec` ON (`qa_pre`.`id_seccion` = `qa_sec`.`id_seccion`)  LEFT OUTER JOIN `qa_zeval_resp` ON (`qa_pre`.`id_pregunta` = `qa_zeval_resp`.`id_pregunta`) AND  ( `qa_zeval_resp`.`id_evaluacion` = '" + IdEvaluacion + "')   LEFT OUTER JOIN `qa_rsp` ON (`qa_zeval_resp`.`id_respuesta` = `qa_rsp`.`id_respuesta`)  where qa_sec.`id_servicio` = '"+idServicio+"'      ";
                            cursor = db.rawQuery(SqlResp, null);
                            JSONArray JSONDataServResp = new JSONArray();

                            while (cursor.moveToNext()) {
                                JSONObject Respuesta = new JSONObject();
                                Respuesta.put("id_pregunta", cursor.getString(cursor.getColumnIndex("id_pregunta")));
                                Respuesta.put("id_respuesta", cursor.getString(cursor.getColumnIndex("id_respuesta")));
                                Respuesta.put("respuesta", cursor.getString(cursor.getColumnIndex("data_cat_respuesta")));
                                Respuesta.put("aplica", cursor.getString(cursor.getColumnIndex("aplica")));
                                Respuesta.put("comentario", cursor.getString(cursor.getColumnIndex("comentario")));
                                //Respuesta.put("fotos", JSONDataServFotos);
                                JSONDataServResp.put(Respuesta);
                            }
                            cursor.close();
                            progressDialog.incrementProgressBy(20);
                            Thread.sleep(1000);
                            JSONDataServ.put("respuestas", JSONDataServResp);
                            JSONArray JSONDataServFotos = new JSONArray();
                            String sqlEvidencia = "SELECT     `qa_zeval`.`id_evaluacion_fb`,    `qa_zeval_evid`.`id_pregunta`,    `qa_zeval_evid`.`evidencia`  FROM    `qa_zeval_evid`    INNER JOIN `qa_zeval` ON (`qa_zeval_evid`.`id_evaluacion` = `qa_zeval`.`id_evaluacion`)  WHERE    `qa_zeval_evid`.`id_evaluacion` = '" + IdEvaluacion + "'  ";
                            cursor = db.rawQuery(sqlEvidencia, null);
                            while (cursor.moveToNext()) {
                                JSONObject Fotos = new JSONObject();
                                Fotos.put("id_evaluacion_fb", cursor.getString(cursor.getColumnIndex("id_evaluacion_fb")));
                                Fotos.put("id_pregunta", cursor.getString(cursor.getColumnIndex("id_pregunta")));
                                Fotos.put("evidencia", cursor.getString(cursor.getColumnIndex("evidencia")));
                                JSONDataServFotos.put(Fotos);
                            }
                            JSONDataServ.put("evidencia", JSONDataServFotos);
                            cursor.close();
                            db.close();
                            progressDialog.incrementProgressBy(20);
                            Thread.sleep(1000);
                            new AsyncTaskSincr(JSONDataServ, evData.getId_evaluacion(), evData.getIdE()).execute();
                        } catch (
                                JSONException e) {    // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (
                                InterruptedException e) {
                            throw new RuntimeException(e);
                        }

                    }
                }
        ).start();
    }



    private static class EvaluacionViewHolder extends RecyclerView.ViewHolder {
        private TextView idE;
        private TextView evaluado;
        private TextView avance;
        private TextView f_ini;
        //private TextView f_fin;
        private TextView empresa;
        private TextView textView15LE;
        private MaterialCardView evalua_cardLE;
        private Button btn_enviarLE;
        private Button btn_Firmar;
        private ImageView imgFrontLE;

        public EvaluacionViewHolder(@NonNull View itemView) {
            super(itemView);
            idE = itemView.findViewById(R.id.idE);
            evaluado = itemView.findViewById(R.id.clienteLE);
            avance = itemView.findViewById(R.id.avanceLE);
            f_ini = itemView.findViewById(R.id.f_iniLE);
            //f_fin = itemView.findViewById(R.id.f_finLE);
            empresa = itemView.findViewById(R.id.empresaLE);
            evalua_cardLE = itemView.findViewById(R.id.evalua_cardLE);
            btn_enviarLE = itemView.findViewById(R.id.btn_enviarLE);
            btn_Firmar = itemView.findViewById(R.id.buttonFirmar);
            imgFrontLE = itemView.findViewById(R.id.imgFrontLE);
            textView15LE = itemView.findViewById(R.id.textView15LE);

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(adapter != null){
            adapter.startListening();
        }

    }

  /*  @Override
    protected void onResume() {
        super.onResume();
        if(adapter != null){
            adapter.startListening();
        }

    }*/


    @Override
    protected void onStop() {
        super.onStop();
       adapter.stopListening();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_serv_eval, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.addevaluacion:
                new MaterialAlertDialogBuilder(EvaluacionList.this)
                        .setIcon(R.drawable.ic_baseline_wb_twilight_24)
                        .setCancelable(false)
                        .setTitle("Nuevo servicio")
                        .setMessage("¿Desea registrar un nuevo servicio?")
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Long id_evaluacion_fb = System.currentTimeMillis() / 1000;
                                String idE = Long.toString(id_evaluacion_fb);


                                Intent intent = new Intent(EvaluacionList.this, EvaluacionForm.class);
                                intent.putExtra("id_servicio", id_servicio);
                                intent.putExtra("idE", idE);
                                intent.putExtra("TipoServicio", TipoServicio);



                                startActivity(intent);


                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Log.d("MainActivity", "Aborting mission...");
                            }
                        })
                        .show();


                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class AsyncTaskSincr extends AsyncTask<String, Float, Integer> {
        JSONObject JSONDataSv = new JSONObject();
        private int id_evaluacion;
        private String idE;
        private String respuesta;

        public AsyncTaskSincr(JSONObject JSOND, int id_evaluacion, String idE) {
            JSONDataSv = JSOND;
            this.id_evaluacion = id_evaluacion;
            this.idE = idE;

        }

        @SuppressLint("Range")
        protected void onPreExecute() {


            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("json", JSONDataSv.toString()));

            String Router = "";
            if(!TipoServicio.equals("servicio")){
                Router = "/"+TipoServicio;
            }

            String Res[] = Api.QueryWebServiceExecute(nameValuePairs, Router);
            respuesta = Res[0];




            DBManager bdm;
            SQLiteDatabase db;
            bdm = new DBManager(EvaluacionList.this);
            db = bdm.getWritableDatabase();
            String sqlEvidencia = "SELECT     `qa_zeval`.`id_evaluacion_fb`,    `qa_zeval_evid`.`id_pregunta`,    `qa_zeval_evid`.`evidencia`  FROM    `qa_zeval_evid`    INNER JOIN `qa_zeval` ON (`qa_zeval_evid`.`id_evaluacion` = `qa_zeval`.`id_evaluacion`)  WHERE    `qa_zeval_evid`.`id_evaluacion` = '" + id_evaluacion + "'  ";
            Cursor cursor = db.rawQuery(sqlEvidencia, null);
            String id_evaluacion_fb = "";
            while (cursor.moveToNext()) {
                id_evaluacion_fb = cursor.getString(cursor.getColumnIndex("id_evaluacion_fb"));
                String id_pregunta = cursor.getString(cursor.getColumnIndex("id_pregunta"));
                String evidencia = cursor.getString(cursor.getColumnIndex("evidencia"));
                String ReslFoto = funcSubirDoctos(id_evaluacion_fb, id_pregunta, evidencia, EvaluacionList.this);

            }

            String sql = "SELECT id_evaluacion_fb,   pic, sign FROM qa_zeval WHERE id_evaluacion='" + id_evaluacion + "'";
            cursor = db.rawQuery(sql, null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    id_evaluacion_fb = cursor.getString(cursor.getColumnIndex("id_evaluacion_fb"));
                    funcSubirDoctos(id_evaluacion_fb, "0", cursor.getString(cursor.getColumnIndex("pic")), EvaluacionList.this);
                    funcSubirDoctos(id_evaluacion_fb, "0", cursor.getString(cursor.getColumnIndex("sign")), EvaluacionList.this);
                }
            }


            cursor.close();
            db.close();

            Log.d("onPreExecute AsyncTaskSincr", Res[1]);
        }

        protected Integer doInBackground(String... Urls) {


            // dialog.setProgress(0);
            return null;
        }

        protected void onPostExecute(Integer bytes) {
            Log.d("EVALUACION", "TERMINE");
            progressDialog.incrementProgressBy(20);
            
            
            if(!respuesta.equals("Error al Consumir Webservice")){
                FirebaseFirestore dbFBL = FirebaseFirestore.getInstance();
                DBManager bdm = new DBManager(EvaluacionList.this);
                SQLiteDatabase db = bdm.getWritableDatabase();
                Map<String, Object> servicio = new HashMap<>();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM-yyyy HH:mm", Locale.getDefault());
                Date date = new Date();
                String fecha = dateFormat.format(date);
                servicio.put("f_envio", fecha);
                servicio.put("code_access", respuesta);
                servicio.put("status", "ENVIADO");
                dbFBL.collection(TipoServicio).document(idE).set(servicio, SetOptions.merge());
                String sql = "UPDATE qa_zeval SET status='ENVIADO', cod_acceso = '"+respuesta+"' WHERE id_evaluacion_fb = '" + idE + "'";
                db.execSQL(sql);
            }else{
                Toast.makeText(EvaluacionList.this, "Favor de revisar su conexión a internet, e intentelo nuevamente", Toast.LENGTH_SHORT).show();
            }
            
            
            progressDialog.dismiss();
        }
    }

    private String funcSubirDoctos(String id_evaluacion_fb, String id_pregunta, String evidencia, Context con) {

        String path = getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString() + "/" + evidencia;

        File imgFile = new File(path);
        if (imgFile.exists()) {
            Bitmap bm = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            try {
                if (bm != null) {
                    FileOutputStream ostream = new FileOutputStream(imgFile);
                    float rs = (float) 600 / bm.getWidth();
                    bm = Bitmap.createScaledBitmap(bm, (int) ((int) bm.getWidth() * rs), (int) ((int) bm.getHeight() * rs), true);
                    bm.compress(Bitmap.CompressFormat.JPEG, 100, ostream);
                    ostream.close();
                }
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        String serviceUrl = "https://apis.cesvimexico.com.mx/LevInfoCesvi/public/fileupload.php?id_evaluacion_fb=" + id_evaluacion_fb + "&id_pregunta=" + id_pregunta + "&evidencia=" + evidencia;
        //String serviceUrl = "https://apis.cesvimexico.com.mx/LevInfoCesvi/public/sincronizacion/fileupload";

        String bandera = "";

        try {
            FileInputStream fis = new FileInputStream(path);
            ApiFotos htfu = new ApiFotos(serviceUrl, path, con);
            bandera = htfu.doStart(fis, id_evaluacion_fb);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            bandera = e.toString();
        }


        return bandera;
    }

}