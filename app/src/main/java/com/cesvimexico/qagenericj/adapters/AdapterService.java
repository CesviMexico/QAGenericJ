package com.cesvimexico.qagenericj.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cesvimexico.qagenericj.EvaluacionList;
import com.cesvimexico.qagenericj.R;
import com.cesvimexico.qagenericj.model.Service;

import java.util.ArrayList;

public class AdapterService extends RecyclerView.Adapter<AdapterService.ViewHolder> {

    private ArrayList<Service> dataSet;
    private Context context;
    private LayoutInflater inflater;

    public AdapterService(Context context, ArrayList<Service> data) {
        this.dataSet = data;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public AdapterService.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_service, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterService.ViewHolder holder, int position) {
        Drawable myDrawable = context.getDrawable(R.drawable.ic_baseline_widgets_24);
        holder.indAv.setText("100%");
        holder.textView.setText(dataSet.get(position).getServicio());

        switch (dataSet.get(position).getId_servicio()) {
            case 1:
                myDrawable = context.getDrawable(R.drawable.ic_baseline_assignment_ind_24);
                break;
            case 2:
                myDrawable = context.getDrawable(R.drawable.ic_baseline_assignment_ind_24);
                break;

            default:
                myDrawable = context.getDrawable(R.drawable.ic_baseline_widgets_24);
                break;
        }

        holder.imgView.setImageDrawable(myDrawable);

        holder.imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EvaluacionList.class);
                intent.putExtra("id_servicio", dataSet.get(position).getId_servicio());
                intent.putExtra("id_area", dataSet.get(position).getId_area());
                intent.putExtra("tipo_servicio", dataSet.get(position).getDescripcion());
                //intent.putExtra("id", id);
                context.startActivity(intent);
                //((Activity) context).finish();
            }
        });


    }

    @Override
    public int getItemCount() {
       return dataSet.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;
        private final ImageView imgView;
        private final TextView indAv;

        public ViewHolder(View item) {
            super(item);
            textView = item.findViewById(R.id.textViewEtq);
            imgView = item.findViewById(R.id.imgSeccion);
            indAv = item.findViewById(R.id.indAv);
            indAv.setVisibility(View.INVISIBLE);
        }
    }

}
