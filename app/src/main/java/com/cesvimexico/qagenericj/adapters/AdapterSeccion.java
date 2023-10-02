package com.cesvimexico.qagenericj.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cesvimexico.qagenericj.EvaluacionForm;
import com.cesvimexico.qagenericj.EvaluacionList;
import com.cesvimexico.qagenericj.EvaluacionPreg;
import com.cesvimexico.qagenericj.EvaluacionPreguntav2;
import com.cesvimexico.qagenericj.R;
import com.cesvimexico.qagenericj.db.DBManager;
import com.cesvimexico.qagenericj.model.EvaluacionData;
import com.cesvimexico.qagenericj.model.Seccion;
import com.cesvimexico.qagenericj.model.Service;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class AdapterSeccion extends RecyclerView.Adapter<AdapterSeccion.ViewHolder> {

    private ArrayList<Seccion> data;
    private Context context;

    private LayoutInflater inflater;
    private String idE;
    private String sql;
    private DBManager bdm;
    private SQLiteDatabase db;
    private Cursor cursor;
    private int id_evaluacion;
    private int total = 0;
    private int total_cont = 0;
    private final String TAG = "EVALUACION";
    private float porcentaje;



    public AdapterSeccion(Context context, ArrayList<Seccion> data, String idE, int id_evaluacion) {
        this.data = data;
        this.context = context;
        this.idE = idE;
        this.id_evaluacion = id_evaluacion;
        this.bdm = new DBManager(context);
        this.db = bdm.getWritableDatabase();
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public AdapterSeccion.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_seccion, parent, false);
        return new AdapterSeccion.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterSeccion.ViewHolder holder, int position) {
        holder.txtSeccion.setText(data.get(holder.getAdapterPosition()).getNombre());
        total = 0;
        total_cont = 0;
        double puntajemax = 0;

        sql = "SELECT COUNT(0) AS total, SUM(`qa_pre`.`puntaje`) as puntajemax FROM `qa_sec` INNER JOIN `qa_pre` ON (`qa_sec`.`id_seccion` = `qa_pre`.`id_seccion`) WHERE `qa_sec`.`id_seccion` = '" + data.get(holder.getAdapterPosition()).getId_seccion() + "'  and qa_pre.status = 'ALTA'  ";
        cursor = db.rawQuery(sql, null);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                total= cursor.getInt(0);
                puntajemax = cursor.getInt(1);
            }
        }

        sql = "SELECT COUNT(0) as total FROM\n" +
                "  `qa_zeval_resp`\n" +
                "  INNER JOIN `qa_pre` ON (`qa_zeval_resp`.`id_pregunta` = `qa_pre`.`id_pregunta`)\n" +
                "WHERE `qa_pre`.`id_seccion` = '" + data.get(holder.getAdapterPosition()).getId_seccion() + "' AND (`qa_zeval_resp`.`id_respuesta` > 0  or `qa_zeval_resp`.respuesta <> '' ) and  `qa_zeval_resp`.`id_evaluacion` = '"+id_evaluacion+"' and qa_pre.status = 'ALTA' ";
        cursor = db.rawQuery(sql, null);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                total_cont= cursor.getInt(0);
            }
        }



        if(total_cont == 0 && puntajemax == 0 && total == 0){
            porcentaje =100;
        }else{
            porcentaje = total_cont*100/total;

        }




        holder.txtAvSecc.setText(String.valueOf((int)porcentaje)+" %");




        holder.evalua_cardSEC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(data.get(holder.getAdapterPosition()).getId_servicio() == 3){
                    Intent intent = new Intent(context, EvaluacionPreguntav2.class);
                    intent.putExtra("id_servicio", data.get(holder.getAdapterPosition()).getId_servicio());
                    intent.putExtra("id_seccion", data.get(holder.getAdapterPosition()).getId_seccion());
                    intent.putExtra("idE", idE);
                    ((Activity) context).startActivity(intent);
                   // ((Activity) context).finish();
                }else {
                    Intent intent = new Intent(context, EvaluacionPreg.class);
                    intent.putExtra("id_servicio", data.get(holder.getAdapterPosition()).getId_servicio());
                    intent.putExtra("id_seccion", data.get(holder.getAdapterPosition()).getId_seccion());
                    intent.putExtra("idE", idE);
                    ((Activity) context).startActivity(intent);
                 //   ((Activity) context).finish();
                }
              /*  Intent intent = new Intent(context, EvaluacionPreg.class);
                intent.putExtra("id_servicio", data.get(holder.getAdapterPosition()).getId_servicio());
                intent.putExtra("id_seccion", data.get(holder.getAdapterPosition()).getId_seccion());
                intent.putExtra("idE", idE);
                ((Activity) context).startActivity(intent);
                ((Activity) context).finish();

               */


            }
        });

        EvaluacionData evData = new EvaluacionData(context, idE);
        evData.calPrcAvance();


    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView txtSeccion;
        private final TextView txtAvSecc;
        private final MaterialCardView evalua_cardSEC;

        public ViewHolder(View item) {
            super(item);
            txtSeccion = item.findViewById(R.id.txtSeccion);
            evalua_cardSEC = item.findViewById(R.id.evalua_cardSEC);
            txtAvSecc  = item.findViewById(R.id.txtAvSecc);
        }
    }


}
