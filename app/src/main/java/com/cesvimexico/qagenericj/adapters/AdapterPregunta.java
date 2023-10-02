package com.cesvimexico.qagenericj.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.core.widget.ImageViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.cesvimexico.qagenericj.Camera.CameraActivity;
import com.cesvimexico.qagenericj.R;
import com.cesvimexico.qagenericj.db.DBManager;
import com.cesvimexico.qagenericj.login.LoginActivity;
import com.cesvimexico.qagenericj.model.Campo;
import com.cesvimexico.qagenericj.model.Pregunta;
import com.cesvimexico.qagenericj.model.Respuesta;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class AdapterPregunta extends RecyclerView.Adapter<AdapterPregunta.ViewHolder> {

    private String TAG = "EVALUACION";
    private ArrayList<Pregunta> dataSet;
    private Context context;
    private String idE;
    private String sql = "";
    private DBManager bdm;
    private SQLiteDatabase db;
    private Cursor cursor;
    private int id_seccion;
    private int id_servicio;
    private int id_evaluacion;
    private View customAlertDialogView;
    private MaterialAlertDialogBuilder matAlertDiag;
    private String tipo_evaluacion;
    private String nombrePic;


    public AdapterPregunta(Context context, ArrayList<Pregunta> dataset, String idE, int id_seccion, int id_servicio) {
        this.context = context;
        this.dataSet = dataset;
        this.idE = idE;
        this.id_seccion = id_seccion;
        this.id_servicio = id_servicio;
        this.bdm = new DBManager(context);
        this.db = bdm.getWritableDatabase();

        sql = "SELECT id_evaluacion FROM `qa_zeval` WHERE `qa_zeval`.`id_evaluacion_fb`='" + idE + "'";

        cursor = db.rawQuery(sql, null);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                this.id_evaluacion = cursor.getInt(0);
            }
        }


        sql = "SELECT tipo FROM `qa_sec` WHERE `qa_sec`.`id_seccion`='" + id_seccion + "'";
        cursor = db.rawQuery(sql, null);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                this.tipo_evaluacion = cursor.getString(0);
            }
        }
    }

    @NonNull
    @Override
    public AdapterPregunta.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_pregunta, parent, false);
        return new AdapterPregunta.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPregunta.ViewHolder holder, int position) {
        boolean seleccionado = false;
        ArrayList<Respuesta> dataSetR = dataSet.get(holder.getAdapterPosition()).getRespuestas();
        holder.textCountPics.setText("0");
        holder.textCountPics.setVisibility(View.GONE);
        holder.txtComentario.setVisibility(View.GONE);
        holder.idNoPregunta.setVisibility(View.VISIBLE);
        holder.txtPregunta.setVisibility(View.VISIBLE);
        holder.txtCriterio.setVisibility(View.VISIBLE);
        holder.idNoPregunta.setText(String.valueOf(holder.getAdapterPosition() + 1) + ". ");
        holder.txtPregunta.setText(dataSet.get(holder.getAdapterPosition()).getPregunta());
        holder.txtCriterio.setText(dataSet.get(holder.getAdapterPosition()).getCriterio());
        holder.checkPregunta.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_chk_nok_24));



        //switch (tipo_evaluacion) {
        switch (tipo_evaluacion) {
            case "TEXT":
                holder.checkPregunta.setVisibility(View.VISIBLE);
                holder.switchEval.setVisibility(View.GONE);
                holder.radioG.setVisibility(View.GONE);
                holder.fActionBComment.setVisibility(View.GONE);
                holder.fActionBCamera.setVisibility(View.GONE);
                holder.txtComentario.setVisibility(View.VISIBLE);
                holder.buttonGuardarTxt.setVisibility(View.VISIBLE);

                sql = "SELECT respuesta FROM qa_zeval_resp WHERE id_pregunta = " + dataSet.get(holder.getAdapterPosition()).getId_pregunta() + "  AND id_evaluacion='" + id_evaluacion + "'";
                cursor = db.rawQuery(sql, null);
                Log.d(TAG, sql);

                if (cursor.getCount() > 0) {
                    while (cursor.moveToNext()) {

                        if (cursor.getString(0) != null && !cursor.getString(0).equals("")) {
                            holder.txtComentario.setText(cursor.getString(0));
                            holder.checkPregunta.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_chk_ok_24));
                        }
                    }
                }

                holder.buttonGuardarTxt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sql = "SELECT id_evaluacion FROM qa_zeval_resp WHERE id_pregunta = " + dataSet.get(holder.getAdapterPosition()).getId_pregunta() + "  AND id_evaluacion='" + id_evaluacion + "'";

                        cursor = db.rawQuery(sql, null);
                        if (cursor.getCount() > 0) {
                            sql = "UPDATE qa_zeval_resp SET respuesta='" + holder.txtComentario.getText().toString() + "'  WHERE id_evaluacion='" + id_evaluacion + "' AND id_pregunta = '" + dataSet.get(holder.getAdapterPosition()).getId_pregunta() + "'";
                        } else {
                            sql = "INSERT INTO qa_zeval_resp (id_evaluacion,id_pregunta, respuesta,aplica) VALUES ('" + id_evaluacion + "'," + dataSet.get(holder.getAdapterPosition()).getId_pregunta() + ",'" + holder.txtComentario.getText().toString() + "','SI')";
                        }
                        db.execSQL(sql);
                        holder.checkPregunta.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_chk_ok_24));
                        Toast.makeText(context, "Respuesta guardada con éxito", Toast.LENGTH_SHORT).show();
                    }
                });


                break;
            case "OPCIONM":
                holder.textCountPics.setVisibility(View.VISIBLE);
                holder.checkPregunta.setVisibility(View.VISIBLE);
                holder.switchEval.setVisibility(View.VISIBLE);
                holder.radioG.setVisibility(View.VISIBLE);
                holder.fActionBComment.setVisibility(View.VISIBLE);
                holder.fActionBCamera.setVisibility(View.VISIBLE);
                holder.buttonGuardarTxt.setVisibility(View.GONE);
                holder.idNoPregunta.setText(String.valueOf(holder.getAdapterPosition() + 1) + ". ");
                holder.txtPregunta.setText(dataSet.get(holder.getAdapterPosition()).getPregunta());
                holder.txtCriterio.setText(dataSet.get(holder.getAdapterPosition()).getCriterio());

                holder.txtComentario.setVisibility(View.GONE);
                holder.radioG.setVisibility(View.VISIBLE);
                holder.radioG.removeAllViews();
                holder.radioG.clearCheck();
                matAlertDiag = new MaterialAlertDialogBuilder(context);
                holder.switchEval.setChecked(true);
                RadioButton rb_sel = null;
                int indexRadioBSelected = 0;





                for (int i = 0; i < dataSetR.size(); i++) {
                    sql = "SELECT id_evaluacion FROM qa_zeval_resp WHERE id_pregunta = " + dataSet.get(holder.getAdapterPosition()).getId_pregunta() + " AND id_respuesta=" + dataSetR.get(i).getId_respuesta() + " AND id_evaluacion='" + id_evaluacion + "'";
                    cursor = db.rawQuery(sql, null);
                    while (cursor.moveToNext()) {
                        seleccionado = true;
                        indexRadioBSelected = i;
                    }

                    final RadioButton rb = new RadioButton(context);
//            dataSetR.get(i).getId_pregunta() + "-" + dataSetR.size() + ". " +
                    rb.setText(dataSetR.get(i).getRespuesta());
                    int finalI = i;
                    rb.setTextSize(14);
                    rb.setTextColor(context.getColor(R.color.gray));
                    rb.setPadding(0, 10, 0, 10);

                    if (i == indexRadioBSelected) {
                        rb_sel = rb;
                    }

                    rb.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String sql = "SELECT id_evaluacion FROM qa_zeval_resp WHERE id_pregunta = " + dataSet.get(holder.getAdapterPosition()).getId_pregunta() + " AND id_evaluacion='" + id_evaluacion + "'";
                            cursor = db.rawQuery(sql, null);
                            if (cursor.getCount() <= 0) {
                                sql = "INSERT INTO qa_zeval_resp(id_evaluacion,id_pregunta,id_respuesta, aplica) VALUES ('" + id_evaluacion + "'," + String.valueOf(dataSet.get(holder.getAdapterPosition()).getId_pregunta()) + "," + dataSetR.get(finalI).getId_respuesta() + ",'SI')";
                            } else {
                                sql = "UPDATE qa_zeval_resp SET id_respuesta = '" + dataSetR.get(finalI).getId_respuesta() + "' WHERE id_evaluacion='" + id_evaluacion + "' AND id_pregunta='" + dataSet.get(holder.getAdapterPosition()).getId_pregunta() + "'";
                            }
                            holder.checkPregunta.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_chk_ok_24));
                            db.execSQL(sql);
                        }
                    });
                    holder.radioG.addView(rb);
                }


                if (seleccionado) {
                    rb_sel.setChecked(true);
                    holder.checkPregunta.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_chk_ok_24));
                }

                String sql = "SELECT id_evaluacion FROM qa_zeval_resp WHERE id_pregunta = " + dataSet.get(holder.getAdapterPosition()).getId_pregunta() + " AND id_evaluacion='" + id_evaluacion + "' AND aplica = 'NO'";
                cursor = db.rawQuery(sql, null);

                if (cursor.getCount() > 0) {
                    holder.switchEval.setChecked(false);
                } else {
                    holder.switchEval.setChecked(true);
                }

                /*SWITCH*/
                holder.switchEval.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String check = "";
                        if (holder.switchEval.isChecked()) {
                            check = "SI";
                        } else {
                            check = "NO";
                        }
                        String sql = "SELECT id_evaluacion FROM qa_zeval_resp WHERE id_pregunta = " + dataSet.get(holder.getAdapterPosition()).getId_pregunta() + " AND id_evaluacion='" + id_evaluacion + "'";
                        cursor = db.rawQuery(sql, null);
                        if (cursor.getCount() <= 0) {
                            sql = "INSERT INTO qa_zeval_resp (id_evaluacion, id_pregunta,  aplica) VALUES ('" + id_evaluacion + "',  '" + dataSet.get(holder.getAdapterPosition()).getId_pregunta() + "'  ,'" + check + "')";
                        } else {
                            sql = "UPDATE qa_zeval_resp SET aplica = '" + check + "' WHERE id_evaluacion='" + id_evaluacion + "' AND id_pregunta='" + dataSet.get(holder.getAdapterPosition()).getId_pregunta() + "'";
                        }
                        db.execSQL(sql);
                    }
                });

                /*FLOATACTION COMMENT*/
                sql = "SELECT comentario FROM qa_zeval_resp WHERE id_pregunta = " + dataSet.get(holder.getAdapterPosition()).getId_pregunta() + " AND id_evaluacion='" + id_evaluacion + "'";
                cursor = db.rawQuery(sql, null);
                if (cursor.getCount() > 0) {
                    while (cursor.moveToNext()) {
                        if (cursor.getString(0) != null) {
                            ImageViewCompat.setImageTintList(holder.fActionBComment,
                                    ColorStateList.valueOf(context.getResources().getColor(R.color.primaryDarkColor))
                            );
                        } else {
                            ImageViewCompat.setImageTintList(holder.fActionBComment,
                                    ColorStateList.valueOf(context.getResources().getColor(R.color.secondaryColor))
                            );
                        }
                    }
                } else {
                    ImageViewCompat.setImageTintList(holder.fActionBComment,
                            ColorStateList.valueOf(context.getResources().getColor(R.color.secondaryColor))
                    );
                }

                /*FLOATACTION COMMENT*/
                holder.fActionBComment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        customAlertDialogView = LayoutInflater.from(context)
                                .inflate(R.layout.dialog_qst_comment, null, false);

                        EditText comentario_pregunta = customAlertDialogView.findViewById(R.id.comentario_pregunta);
                        String sql = "SELECT comentario FROM qa_zeval_resp WHERE id_pregunta = " + dataSet.get(holder.getAdapterPosition()).getId_pregunta() + " AND id_evaluacion='" + id_evaluacion + "'";
                        cursor = db.rawQuery(sql, null);
                        if (cursor.getCount() > 0) {
                            while (cursor.moveToNext()) {
                                comentario_pregunta.setText(cursor.getString(0));
                            }
                        } /*else {
                    sql = "INSERT INTO app_audit_resp (id_auditoria, id_pregunta, auditor) VALUES ('" + id + "', '" + dataSet.get(position).getId_pregunta() + "'  , '" + auditor + "')";
                    db.execSQL(sql);
                    ImageViewCompat.setImageTintList(holder.fActionBComment,
                            ColorStateList.valueOf(context.getResources().getColor(R.color.primaryDarkColor))
                    );
                }*/

                        matAlertDiag.setView(customAlertDialogView)
                                .setIcon(R.drawable.ic_baseline_insert_comment_24)
                                .setCancelable(false)
                                .setTitle("Comentarios")
                                .setMessage("Ingrese el comentario:")
                                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        String sql = "SELECT comentario FROM qa_zeval_resp WHERE id_pregunta = " + dataSet.get(holder.getAdapterPosition()).getId_pregunta() + " AND id_evaluacion='" + id_evaluacion + "'";
                                        cursor = db.rawQuery(sql, null);
                                        if (cursor.getCount() > 0) {
                                            sql = "UPDATE qa_zeval_resp SET comentario = '" + comentario_pregunta.getText() + "' WHERE id_evaluacion='" + id_evaluacion + "' AND id_pregunta='" + dataSet.get(holder.getAdapterPosition()).getId_pregunta() + "'";
                                        } else {
                                            sql = "INSERT INTO qa_zeval_resp (id_evaluacion, id_pregunta) VALUES ('" + id_evaluacion + "', '" + dataSet.get(holder.getAdapterPosition()).getId_pregunta() + "'  )";
                                        }
                                        db.execSQL(sql);
                                        ImageViewCompat.setImageTintList(holder.fActionBComment,
                                                ColorStateList.valueOf(context.getResources().getColor(R.color.primaryDarkColor))
                                        );
                                    }
                                })
                                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                })
                                .show();
                    }
                });


                sql = "SELECT id_evidencia FROM qa_zeval_evid WHERE id_pregunta = " + dataSet.get(holder.getAdapterPosition()).getId_pregunta() + " AND id_evaluacion='" + id_evaluacion + "'";
                Log.d(TAG, sql);
                cursor = db.rawQuery(sql, null);
                if (cursor.getCount() > 0) {
                    ImageViewCompat.setImageTintList(holder.fActionBCamera,
                            ColorStateList.valueOf(context.getResources().getColor(R.color.primaryDarkColor))
                    );
                } else {
                    ImageViewCompat.setImageTintList(holder.fActionBCamera,
                            ColorStateList.valueOf(context.getResources().getColor(R.color.secondaryColor))
                    );
                }

                holder.fActionBCamera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        File photoFile = null;
                        try {
                            photoFile = createImageFile();
                        } catch (IOException ex) {
                            // Error cuando se creo el archivo
                        }
                        if (photoFile != null) {

                            String idp = String.valueOf(dataSet.get(holder.getAdapterPosition()).getId_pregunta());

                            enableCamera(idp);

                        }
                    }
                });



                sql = "SELECT COUNT(*) as total FROM qa_zeval_evid WHERE id_pregunta = " + dataSet.get(holder.getAdapterPosition()).getId_pregunta() + " AND id_evaluacion='" + id_evaluacion + "'";
                cursor = db.rawQuery(sql, null);
                if (cursor.getCount() > 0) {
                    while (cursor.moveToNext()) {
                        Log.d(TAG, "PICS: " + String.valueOf(cursor.getInt(0)));
                        holder.textCountPics.setText(cursor.getString(0));
                    }
                }



                break;


        }


    }


    private void enableCamera(String idPr) {
        Intent intent = new Intent(context, CameraActivity.class);
        intent.putExtra("nombrePic", nombrePic);
        intent.putExtra("tipoFoto", "preguntas");
        intent.putExtra("idE", String.valueOf(id_evaluacion));
        intent.putExtra("idEval", id_evaluacion);
        intent.putExtra("idPregunta", idPr);
        context.startActivity(intent);


    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView txtPregunta;
        private final TextView txtCriterio;
        private final TextView idNoPregunta;
        private final TextView textCountPics;


        private final RadioGroup radioG;

        private final ImageView checkPregunta;
        private final FloatingActionButton fActionBCamera;
        private final FloatingActionButton fActionBComment;

        private final SwitchMaterial switchEval;

        private final EditText txtComentario;
        private final Button buttonGuardarTxt;

        public ViewHolder(View item) {
            super(item);
            txtPregunta = item.findViewById(R.id.txtPregunta);
            txtCriterio = item.findViewById(R.id.txtCriterio);
            idNoPregunta = item.findViewById(R.id.idNoPregunta);
            textCountPics = item.findViewById(R.id.textCountPics);

            radioG = item.findViewById(R.id.radioGroup);

            checkPregunta = item.findViewById(R.id.checkPregunta);
            fActionBCamera = item.findViewById(R.id.fActionBCamera);
            fActionBComment = item.findViewById(R.id.fActionBComment);

            switchEval = item.findViewById(R.id.switchEval);
            txtComentario = item.findViewById(R.id.txtComentario);
            buttonGuardarTxt = item.findViewById(R.id.buttonGuardarTxt);
        }
    }

    private File createImageFile() throws IOException {
        nombrePic = "";
        String imageFileName = "PR_" + id_evaluacion + "_";
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",   /* suffix */
                context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)  /* directory */
        );
        this.nombrePic = image.getName();
        return image;
    }
}
