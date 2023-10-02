package com.cesvimexico.qagenericj.adapters;

import static androidx.camera.core.CameraX.getContext;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Environment;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.widget.ImageViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.cesvimexico.qagenericj.Camera.CameraActivity;
import com.cesvimexico.qagenericj.R;
import com.cesvimexico.qagenericj.model.PreguntaRespuesta;
import com.cesvimexico.qagenericj.model.Respuesta;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AdapterPreguntaResp extends RecyclerView.Adapter<AdapterPreguntaResp.ViewHolder>{
    private LayoutInflater inflador;
    private List<PreguntaRespuesta> ListaPreguntas;
    Context ContextUso;
    protected View.OnClickListener onClickListener;
    private View customAlertDialogView;
    private MaterialAlertDialogBuilder matAlertDiag;
    private String nombrePic;
    private int id_evaluacion;

    public void setOnClickListener(View.OnClickListener onClickListener){
        this.onClickListener = onClickListener;
    }

    public AdapterPreguntaResp(List<PreguntaRespuesta> lista, Context contextUso) {
        ListaPreguntas = lista;
        ContextUso = contextUso;
        matAlertDiag = new MaterialAlertDialogBuilder(contextUso);
        inflador = (LayoutInflater) contextUso.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        id_evaluacion = lista.get(0).getIdEvaluacion();
    }

    public void recargarAdapter(List<PreguntaRespuesta> lista){
        ListaPreguntas = lista;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflador.inflate(R.layout.list_item_pregunta, parent, false);
        v.setOnClickListener(onClickListener);
        return new ViewHolder(v);

    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.txtPregunta.setText(ListaPreguntas.get(holder.getAdapterPosition()).getPregunta());
        holder.idNoPregunta.setText(ListaPreguntas.get(holder.getAdapterPosition()).getOrden()+". ");
        holder.txtCriterio.setText(ListaPreguntas.get(holder.getAdapterPosition()).getCriterio());


        ArrayList<Respuesta> dataSetR = ListaPreguntas.get(holder.getAdapterPosition()).getRespuestas();

        holder.radioG.removeAllViews();
        holder.radioG.clearCheck();

        if(ListaPreguntas.get(holder.getAdapterPosition()).getTipo().equals("RM")){/// opcion multiple
            holder.radioG.setVisibility(View.VISIBLE);
            holder.txtComentario.setVisibility(View.GONE);
            holder.buttonGuardarTxt.setVisibility(View.GONE);

            for (int i = 0; i < dataSetR.size(); i++) {
                final RadioButton rb = new RadioButton(ContextUso);
//            dataSetR.get(i).getId_pregunta() + "-" + dataSetR.size() + ". " +
                rb.setText(dataSetR.get(i).getRespuesta());
                int finalI = i;
                rb.setTextSize(14);
                rb.setTextColor(ContextUso.getColor(R.color.gray));
                rb.setPadding(0, 10, 0, 10);
                holder.radioG.addView(rb);

                rb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                       int respuSelect = dataSetR.get(finalI).getId_respuesta();
                        ListaPreguntas.get(holder.getAdapterPosition()).setIdRespuesta(respuSelect, ContextUso);
                        ListaPreguntas.get(holder.getAdapterPosition()).setRespuesta(dataSetR.get(finalI).getRespuesta(), ContextUso);
                        holder.checkPregunta.setImageDrawable(ContextUso.getResources().getDrawable(R.drawable.ic_chk_ok_24));
                    }
                });

                if(ListaPreguntas.get(holder.getAdapterPosition()).getIdRespuesta() == dataSetR.get(i).getId_respuesta()){
                    rb.setChecked(true);
                }else {
                    rb.setChecked(false);
                }

            }

        }else{///// cuando son tipo text o texto numeros
            if(ListaPreguntas.get(holder.getAdapterPosition()).getTipo().equals("TX")){
                holder.txtComentario.setInputType(InputType.TYPE_CLASS_TEXT);
            }else if(ListaPreguntas.get(holder.getAdapterPosition()).getTipo().equals("TX_NUM")){
                holder.txtComentario.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL);
            }
            holder.radioG.setVisibility(View.GONE);
            holder.txtComentario.setVisibility(View.VISIBLE);
            holder.buttonGuardarTxt.setVisibility(View.GONE);
            holder.txtComentario.setText(ListaPreguntas.get(holder.getAdapterPosition()).getRespuesta());


            holder.txtComentario.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

                @Override
                public void afterTextChanged(Editable editable) {
                    String valor = editable.toString();
                    ListaPreguntas.get(holder.getAdapterPosition()).setRespuesta(valor, ContextUso);
                    ListaPreguntas.get(holder.getAdapterPosition()).setIdRespuesta(0, ContextUso);
                    holder.checkPregunta.setImageDrawable(ContextUso.getResources().getDrawable(R.drawable.ic_chk_ok_24));
                }
            });




            holder.buttonGuardarTxt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ListaPreguntas.get(holder.getAdapterPosition()).setRespuesta(holder.txtComentario.getText().toString(), ContextUso);
                    ListaPreguntas.get(holder.getAdapterPosition()).setIdRespuesta(0, ContextUso);
                    holder.checkPregunta.setImageDrawable(ContextUso.getResources().getDrawable(R.drawable.ic_chk_ok_24));

                }
            });

        }

        /*FLOATACTION COMMENT*/

        holder.fActionBComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customAlertDialogView = LayoutInflater.from(ContextUso)
                        .inflate(R.layout.dialog_qst_comment, null, false);

                EditText comentario_pregunta = customAlertDialogView.findViewById(R.id.comentario_pregunta);
                comentario_pregunta.setText(ListaPreguntas.get(holder.getAdapterPosition()).getComentario());

                matAlertDiag.setView(customAlertDialogView)
                        .setIcon(R.drawable.ic_baseline_insert_comment_24)
                        .setCancelable(false)
                        .setTitle("Comentarios")
                        .setMessage("Ingrese el comentario:")
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ListaPreguntas.get(holder.getAdapterPosition()).setComentario(comentario_pregunta.getText().toString(), ContextUso);
                                ImageViewCompat.setImageTintList(holder.fActionBComment,
                                        ColorStateList.valueOf(ContextUso.getResources().getColor(R.color.primaryDarkColor)));
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


        if(ListaPreguntas.get(holder.getAdapterPosition()).getIdRespuesta() > 0 ||
                (ListaPreguntas.get(holder.getAdapterPosition()).getRespuesta() != null && !ListaPreguntas.get(holder.getAdapterPosition()).getRespuesta().equals("")) ){
            holder.checkPregunta.setImageDrawable(ContextUso.getResources().getDrawable(R.drawable.ic_chk_ok_24));
        }else{
            holder.checkPregunta.setImageDrawable(ContextUso.getResources().getDrawable(R.drawable.ic_chk_nok_24));
        }

        if (ListaPreguntas.get(holder.getAdapterPosition()).getComentario() != null){
            if(!ListaPreguntas.get(holder.getAdapterPosition()).getComentario().equals("")){
                ImageViewCompat.setImageTintList(holder.fActionBComment,
                        ColorStateList.valueOf(ContextUso.getResources().getColor(R.color.primaryDarkColor)));
            }else{
                ImageViewCompat.setImageTintList(holder.fActionBComment,
                        ColorStateList.valueOf(ContextUso.getResources().getColor(R.color.secondaryColor)));
            }
        }else{
            ImageViewCompat.setImageTintList(holder.fActionBComment,
                    ColorStateList.valueOf(ContextUso.getResources().getColor(R.color.secondaryColor)));
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
                    String idp = String.valueOf(ListaPreguntas.get(holder.getAdapterPosition()).getId_pregunta());
                    enableCamera(idp);

                }
            }
        });

        holder.textCountPics.setText(String.valueOf(ListaPreguntas.get(holder.getAdapterPosition()).getNoEvidencias()));

        if(ListaPreguntas.get(holder.getAdapterPosition()).getNoEvidencias() > 0){
            ImageViewCompat.setImageTintList(holder.fActionBCamera,
                    ColorStateList.valueOf(ContextUso.getResources().getColor(R.color.primaryDarkColor))
            );
        }else{
            ImageViewCompat.setImageTintList(holder.fActionBCamera,
                    ColorStateList.valueOf(ContextUso.getResources().getColor(R.color.secondaryColor))
            );
        }

    }

    private void enableCamera(String idPr) {
        Intent intent = new Intent(ContextUso, CameraActivity.class);
        intent.putExtra("nombrePic", nombrePic);
        intent.putExtra("tipoFoto", "preguntas");
        intent.putExtra("idE", String.valueOf(id_evaluacion));
        intent.putExtra("idEval", id_evaluacion);
        intent.putExtra("idPregunta", idPr);
        ContextUso.startActivity(intent);


    }

    private File createImageFile() throws IOException {
        nombrePic = "";
        String imageFileName = "PR_" + id_evaluacion + "_";
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",   /* suffix */
                ContextUso.getExternalFilesDir(Environment.DIRECTORY_PICTURES)  /* directory */
        );
        this.nombrePic = image.getName();
        return image;
    }

    @Override
    public int getItemCount(){
        return ListaPreguntas.size();
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

            switchEval.setVisibility(View.GONE);
        }
    }

}
