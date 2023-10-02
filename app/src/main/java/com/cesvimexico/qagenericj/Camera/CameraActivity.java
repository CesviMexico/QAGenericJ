package com.cesvimexico.qagenericj.Camera;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.util.Size;
import android.view.Display;
import android.view.OrientationEventListener;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cesvimexico.qagenericj.EvaluacionForm;
import com.cesvimexico.qagenericj.R;
import com.cesvimexico.qagenericj.db.DBManager;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.gms.tasks.CancellationTokenSource;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.common.util.concurrent.ListenableFuture;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class CameraActivity extends AppCompatActivity {

    private PreviewView previewView;
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    ProcessCameraProvider cameraProvider;
    private TextView textView;
    private EditText TextDescripcion;
    private Button BtnCapturar, BtnSalir, BtnAceptarRegresar, BtnELiminar;
    private ProgressBar progressBar, progressBarUpload;

    ImageAnalysis imageAnalysis;

    ImageCapture imageCapture;

    ImageView imageView;

    FrameLayout containerVPreview, container;
    static File fileCapturado;

    String nombreFile = "";
    String Orientacion = "";

    String nombrePic = "";
    String idE = "";
    String idEval = "";
    String idPregunta = "";
    String tipoFoto = "";

    int width = 0, height = 0;

    Bitmap BitmapImgCapture = null;

    private DBManager bdm;
    private SQLiteDatabase db;


    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        BtnCapturar = findViewById(R.id.BtnCapturar);
        BtnSalir = findViewById(R.id.BtnSalir);
        BtnAceptarRegresar = findViewById(R.id.BtnAceptarRegresar);
        BtnELiminar = findViewById(R.id.BtnELiminar);
        progressBar = findViewById(R.id.progressBar);
        progressBarUpload = findViewById(R.id.progressBarUpload);

        imageView = findViewById(R.id.imageView);

        containerVPreview = findViewById(R.id.containerVPreview);
        container = findViewById(R.id.container);

        previewView = findViewById(R.id.previewView);

        textView = findViewById(R.id.orientation);
        TextDescripcion = findViewById(R.id.TextDescripcion);

        Bundle bundle = getIntent().getExtras();

        nombrePic = getIntent().getExtras().getString("nombrePic");
        idE = getIntent().getExtras().getString("idE");
        idEval = getIntent().getExtras().getString("idEval");
        tipoFoto = getIntent().getExtras().getString("tipoFoto");
        idPregunta = getIntent().getExtras().getString("idPregunta");

        bdm = new DBManager(this);

        cameraProviderFuture = ProcessCameraProvider.getInstance(this);
        inicializarCamara();

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        height = size.y;

        BtnCapturar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressBar.setVisibility(View.VISIBLE);

                SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyyMMddHHmmss", Locale.US);
                //nombrePic = mDateFormat.format(new Date())+ ".jpg";
                Orientacion = textView.getText().toString();

                //fileCapturado = new File(getBatchDirectoryName(IdAsignacion), nombrePic);
                fileCapturado = new File(CameraActivity.this.getExternalFilesDir(Environment.DIRECTORY_PICTURES), nombrePic);

                final ImageCapture.OutputFileOptions outputFileOptions = new ImageCapture.OutputFileOptions.Builder(fileCapturado).build();

                imageCapture.takePicture(outputFileOptions, ContextCompat.getMainExecutor(CameraActivity.this), new ImageCapture.OnImageSavedCallback() {
                    @Override
                    public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                        Log.w("onImageSaved", "Inicia guaradar : " + getDateTime());
                        cameraProvider.unbindAll();

                        //////
                       /*
                        containerVPreview.setVisibility(View.VISIBLE);
                        container.setVisibility(View.GONE);
                        BtnCapturar.setVisibility(View.GONE);
*/


                        //BitmapImgCapture = reduceBitmap(getApplicationContext() , String.valueOf(Uri.fromFile(fileCapturado)), width, height, Integer.parseInt(Orientacion));
                        BitmapImgCapture = reduceBitmap(getApplicationContext(), String.valueOf(Uri.fromFile(fileCapturado)), width, height, Integer.parseInt(Orientacion));
                        //imageView.setImageBitmap(BitmapImgCapture);

                        Log.w("onImageSaved", "setea imageView: " + getDateTime());

                        db = bdm.getWritableDatabase();

                        if (tipoFoto.equals("principal")) {

                            String sql = "SELECT pic FROM qa_zeval WHERE id_evaluacion_fb='" + idE + "'";
                            Cursor cursor = db.rawQuery(sql, null);
                            if (cursor.getCount() > 0) {
                                while (cursor.moveToNext()) {
                                    sql = "UPDATE qa_zeval SET pic='" + nombrePic + "' WHERE id_evaluacion_fb='" + idE + "'";
                                    db.execSQL(sql);


                                    fusedLocationClient = LocationServices.getFusedLocationProviderClient(CameraActivity.this);


                                    CancellationTokenSource cancellationTokenSource = new CancellationTokenSource();

                                    if (ActivityCompat.checkSelfPermission(CameraActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(CameraActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                        // TODO: Consider calling
                                        //    ActivityCompat#requestPermissions
                                        // here to request the missing permissions, and then overriding
                                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                        //                                          int[] grantResults)
                                        // to handle the case where the user grants the permission. See the documentation
                                        // for ActivityCompat#requestPermissions for more details.
                                        //return;
                                    }
                                    fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, cancellationTokenSource.getToken())
                                            .addOnSuccessListener(CameraActivity.this, new OnSuccessListener<Location>() {
                                                @Override
                                                public void onSuccess(Location location) {
                                                    // Got last known location. In some rare situations this can be null.
                                                    if (location != null) {
                                                        //do your thing
                                                        SQLiteDatabase db2 = bdm.getWritableDatabase();
                                                        String coordenadas = location.getLatitude()+","+ location.getLongitude();
                                                        String sql = "UPDATE qa_zeval SET lugar='" + coordenadas + "' WHERE id_evaluacion_fb='" + idE + "'";
                                                        db2.execSQL(sql);
                                                        db2.close();

                                                    }
                                                    Log.w("getCurrentLocation", "No current location could be found");
                                                }
                                            });



                                }
                            }
                        } else {
                            String sql = "INSERT INTO qa_zeval_evid (id_evaluacion, id_pregunta, evidencia) VALUES ('" + idE + "',  '" + idPregunta + "' ,'" + nombrePic + "')";
                            db.execSQL(sql);
                        }

                        db.close();

                        try {
                            if (BitmapImgCapture != null) {
                                FileOutputStream ostream = new FileOutputStream(fileCapturado);
                                float rs = (float) 600 / BitmapImgCapture.getWidth();
                                BitmapImgCapture = Bitmap.createScaledBitmap(BitmapImgCapture, (int) ((int) BitmapImgCapture.getWidth() * rs), (int) ((int) BitmapImgCapture.getHeight() * rs), true);
                                BitmapImgCapture.compress(Bitmap.CompressFormat.JPEG, 100, ostream);
                                ostream.close();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                        Intent intent = new Intent();
                        intent.putExtra("nombrePic", nombrePic);

                        setResult(2, intent);
                        finish();//finishing activity


                    }

                    @Override
                    public void onError(@NonNull ImageCaptureException error) {
                        error.printStackTrace();
                        Toast.makeText(CameraActivity.this, "Image save FAILED!!!!", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                });


            }
        });


        BtnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /* ManagerSQLite ManagerBD = new ManagerSQLite(getApplicationContext());
                SQLiteDatabase BD = ManagerBD.getWritableDatabase();

                ContentValues Values = new ContentValues();
                Values.put("id_orden", idOrden);
                Values.put("id_asignacion", IdAsignacion);
                Values.put("evidencia", nombrePic);
                Values.put("descripcion", TextDescripcion.getText().toString());
                Values.put("orientacion", Orientacion);
                Values.put("uploaded", "no");

                long res = BD.insert("bit_evidencia", null, Values);

                BD.close();
                ManagerBD.close();
                */

                //new UploadEvidencia().execute("salir", nombrePic);

                db = bdm.getWritableDatabase();

                if (tipoFoto.equals("principal")) {

                    String sql = "SELECT pic FROM qa_zeval WHERE id_evaluacion_fb='" + idE + "'";
                    Cursor cursor = db.rawQuery(sql, null);
                    if (cursor.getCount() > 0) {
                        while (cursor.moveToNext()) {
                            sql = "UPDATE qa_zeval SET pic='" + nombrePic + "' WHERE id_evaluacion_fb='" + idE + "'";
                            db.execSQL(sql);

                            fusedLocationClient = LocationServices.getFusedLocationProviderClient(CameraActivity.this);


                            CancellationTokenSource cancellationTokenSource = new CancellationTokenSource();
                            if (ActivityCompat.checkSelfPermission(CameraActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(CameraActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                // TODO: Consider calling
                                //    ActivityCompat#requestPermissions
                                // here to request the missing permissions, and then overriding
                                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                //                                          int[] grantResults)
                                // to handle the case where the user grants the permission. See the documentation
                                // for ActivityCompat#requestPermissions for more details.
                              //  return;
                            }
                            fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, cancellationTokenSource.getToken())
                                    .addOnSuccessListener(CameraActivity.this, new OnSuccessListener<Location>() {
                                        @Override
                                        public void onSuccess(Location location) {
                                            // Got last known location. In some rare situations this can be null.
                                            if (location != null) {
                                                //do your thing
                                                String coordenadas = location.getLatitude()+","+ location.getLongitude();
                                                String sql = "UPDATE qa_zeval SET lugar='" + coordenadas + "' WHERE id_evaluacion_fb='" + idE + "'";
                                                db.execSQL(sql);
                                            }
                                            Log.w("getCurrentLocation", "No current location could be found");
                                        }
                                    });








                        }
                    }

                }else{
                    String sql = "INSERT INTO qa_zeval_evid (id_evaluacion, id_pregunta, evidencia) VALUES ('" + idE + "',  '" + idPregunta + "' ,'" + nombrePic + "')";
                    db.execSQL(sql);
                }

                db.close();

                try {
                    if (BitmapImgCapture != null) {
                        FileOutputStream ostream = new FileOutputStream(fileCapturado);
                        float rs = (float) 600 / BitmapImgCapture.getWidth();
                        BitmapImgCapture = Bitmap.createScaledBitmap(BitmapImgCapture, (int) ((int) BitmapImgCapture.getWidth() * rs), (int) ((int) BitmapImgCapture.getHeight() * rs), true);
                        BitmapImgCapture.compress(Bitmap.CompressFormat.JPEG, 100, ostream);
                        ostream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }


                Intent intent=new Intent();
                intent.putExtra("nombrePic",nombrePic);

                setResult(2,intent);
                finish();//finishing activity
            }
        });

        BtnAceptarRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*
                ManagerSQLite ManagerBD = new ManagerSQLite(getApplicationContext());
                SQLiteDatabase BD = ManagerBD.getWritableDatabase();

                ContentValues Values = new ContentValues();
                Values.put("id_orden", idOrden);
                Values.put("id_asignacion", IdAsignacion);
                Values.put("evidencia", nombrePic);
                Values.put("descripcion", TextDescripcion.getText().toString());
                Values.put("orientacion", Orientacion);
                Values.put("uploaded", "no");

                long res = BD.insert("bit_evidencia", null, Values);

                BD.close();
                ManagerBD.close();


               */

                inicializarCamara();

                //new UploadEvidencia().execute("regresar", nombrePic);
            }
        });



        BtnELiminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fileCapturado.delete();
                containerVPreview.setVisibility(View.GONE);
                container.setVisibility(View.VISIBLE);
                BtnCapturar.setVisibility(View.VISIBLE);
                inicializarCamara();
            }
        });



    }




    public void inicializarCamara(){
        cameraProviderFuture.addListener(new Runnable() {
            @Override
            public void run() {
                try {
                    ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                    bindImageAnalysis(cameraProvider);



                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, ContextCompat.getMainExecutor(this));
    }


    public static Bitmap reduceBitmap(Context contexto, String uri, int maxAncho, int maxAlto, int rotacion) {

        int rotacionAPlicr = 0;
        if(rotacion > 315 || rotacion < 45){   ///// vertical
            rotacionAPlicr = 90;

        }else if(rotacion > 45 && rotacion < 135){
            rotacionAPlicr = 180;

        }else if(rotacion > 135 && rotacion < 225){
            rotacionAPlicr = 270;

        }else if(rotacion > 225 && rotacion < 315){
            rotacionAPlicr = 0;
        }


        try {
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(contexto.getContentResolver()
                    .openInputStream(Uri.parse(uri)), null, options);
            options.inSampleSize = (int) Math.max(
                    Math.ceil(options.outWidth / maxAncho),
                    Math.ceil(options.outHeight / maxAlto));
            options.inJustDecodeBounds = false;
            Bitmap BitmapOrig = BitmapFactory.decodeStream(contexto.getContentResolver().openInputStream(Uri.parse(uri)), null, options);
            Matrix matrix = new Matrix();
            matrix.postRotate(rotacionAPlicr);
            Bitmap rotatedBitmap = Bitmap.createBitmap(BitmapOrig, 0, 0, BitmapOrig.getWidth(), BitmapOrig.getHeight(), matrix, true);






            return rotatedBitmap;
        } catch (FileNotFoundException e) {
            Toast.makeText(contexto, "Fichero/recurso no encontrado",
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        cameraProvider.unbindAll();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cameraProvider.unbindAll();
    }

    public String getDateTime(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }




    private void bindImageAnalysis(@NonNull ProcessCameraProvider cameraProvider) {
        this.cameraProvider = cameraProvider;
        imageAnalysis = new ImageAnalysis.Builder().setTargetResolution(new Size(1280, 720))
                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST).build();
        imageAnalysis.setAnalyzer(ContextCompat.getMainExecutor(this), new ImageAnalysis.Analyzer() {
            @Override
            public void analyze(@NonNull ImageProxy image) {
                image.close();
            }
        });
        OrientationEventListener orientationEventListener = new OrientationEventListener(this) {
            @Override
            public void onOrientationChanged(int orientation) {
                textView.setText(Integer.toString(orientation));
            }
        };
        orientationEventListener.enable();
        Preview preview = new Preview.Builder().build();
        CameraSelector cameraSelector = new CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK).build();
        preview.setSurfaceProvider(previewView.createSurfaceProvider());



        imageCapture =
                new ImageCapture.Builder()
                        .setTargetRotation(Surface.ROTATION_90) //// tiene que ir en 1 para qeu siemrpe tome esa orientacion independeintemente del telefono
                        .build();



        //cameraProvider.bindToLifecycle((LifecycleOwner)this, cameraSelector, imageAnalysis, preview);
        cameraProvider.bindToLifecycle((LifecycleOwner)this, cameraSelector, imageCapture, imageAnalysis, preview);



    }





}