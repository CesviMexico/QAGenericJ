package com.cesvimexico.qagenericj.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cesvimexico.qagenericj.model.Respuesta;

import java.util.ArrayList;

public class AdapterRespuesta extends RecyclerView.Adapter<AdapterRespuesta.ViewHolder> {

    private ArrayList<Respuesta> dataSet;
    private LayoutInflater inflater;
    private Context context;
    private String id;

    public AdapterRespuesta(Context context, ArrayList<Respuesta> dataSet, String id) {
        this.dataSet = dataSet;
        this.context = context;
        this.id = id;
        //this.RespuestaDada = "Sí, tenemos un programa basado en la evaluación de riesgos";
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public AdapterRespuesta.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRespuesta.ViewHolder holder, int position) {
        for (int i = 0; i < dataSet.size(); i++) {
            final RadioButton rb = new RadioButton(context);
            rb.setText(dataSet.get(i).getRespuesta());
        }
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View item) {
            super(item);

        }


    }

}
