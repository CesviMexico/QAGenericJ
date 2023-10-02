package com.cesvimexico.qagenericj;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.cesvimexico.qagenericj.db.DBManager;
import com.cesvimexico.qagenericj.model.EvaluacionData;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Signature extends AppCompatActivity {


    Button buttonLimp;
    Button buttonGrd;
    Button buttonCncl;
    ImageView imageViewFirma;
    private boolean permission;
    private String nombreSign;
    private SgnCanvas vw;
    private Cursor cursor;
    private DBManager bdm;
    private SQLiteDatabase db;

    private String idE;

    private String TipoServicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signature);

        bdm = new DBManager(Signature.this);
        db = bdm.getWritableDatabase();

        this.idE = getIntent().getExtras().getString("idE");
        this.TipoServicio = getIntent().getExtras().getString("TipoServicio");

        EvaluacionData evaData = new EvaluacionData(Signature.this, idE);

//        canvas = new SgnCanvas(this, null);
//        setContentView(canvas);

        buttonLimp = findViewById(R.id.buttonLimp);
        buttonGrd = findViewById(R.id.buttonGrd);
        buttonCncl = findViewById(R.id.buttonCncl);
        imageViewFirma = findViewById(R.id.imageViewFirma);

        ConstraintLayout cl = findViewById(R.id.clFirma);
        vw = new SgnCanvas(this, null);
        cl.addView(vw);

        buttonLimp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cl.removeAllViews();
                vw = new SgnCanvas(Signature.this, null);
                cl.addView(vw);
            }
        });
        buttonGrd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    createSignatureFile();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

//                if (ContextCompat.checkSelfPermission(getApplicationContext(),
//                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                        != PackageManager.PERMISSION_GRANTED) {
//                    // No explanation needed, we can request the permission.
//                    ActivityCompat.requestPermissions(Signature.this,
//                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
//                            10);
//
//                } else {
//                    permission = true;
//                }

                File imgFile = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), nombreSign);
                if (imgFile.exists()) {
                    Bitmap bm = vw.getBitmapFromView(vw);
                    try {
                        if (bm != null) {

                            FileOutputStream ostream = new FileOutputStream(imgFile);
                            float rs = (float) 600 / bm.getWidth();
                            bm = Bitmap.createScaledBitmap(bm, (int) ((int) bm.getWidth() * rs), (int) ((int) bm.getHeight() * rs), true);
                            bm.compress(Bitmap.CompressFormat.JPEG, 100, ostream);

                            String sql = "UPDATE qa_zeval SET sign='" + nombreSign + "', `status`='TERMINADO' WHERE id_evaluacion_fb='" + idE + "'";
                            db.execSQL(sql);
                            Map<String, Object> servicio = new HashMap<>();
                            servicio.put("sign", nombreSign);
                            servicio.put("status", "TERMINADO");
                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM-yyyy HH:mm", Locale.getDefault());
                            Date date = new Date();
                            String fecha = dateFormat.format(date);
                            servicio.put("f_fin", fecha);

                            ostream.flush();
                            ostream.close();


                            FirebaseFirestore dbFB;
                            dbFB = FirebaseFirestore.getInstance();
                            dbFB.collection(TipoServicio).document(evaData.getIdE()).set(servicio, SetOptions.merge());



                            finish();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        buttonCncl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void createSignatureFile() throws IOException {
        nombreSign = "";
        String imageFileName = "SIGN_";
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",   /* suffix */
                getExternalFilesDir(Environment.DIRECTORY_PICTURES)  /* directory */
        );

        this.nombreSign = image.getName();

    }

}