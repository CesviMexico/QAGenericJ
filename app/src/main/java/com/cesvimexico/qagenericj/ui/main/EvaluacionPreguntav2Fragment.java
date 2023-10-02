package com.cesvimexico.qagenericj.ui.main;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cesvimexico.qagenericj.EvaluacionPreguntav2;
import com.cesvimexico.qagenericj.R;
import com.cesvimexico.qagenericj.adapters.AdapterPreguntaResp;
import com.cesvimexico.qagenericj.model.EvaluacionData;
import com.cesvimexico.qagenericj.model.Pregunta;
import com.cesvimexico.qagenericj.model.PreguntaRespuesta;

import java.util.List;

public class EvaluacionPreguntav2Fragment extends Fragment {

    private EvalPregViewModel mViewModel;

    int IdSeccion ;
    int IdEvaluacion;

    RecyclerView preguntasRecyclerView;
    AdapterPreguntaResp adaptador;
    boolean bandRecycler = false;
    public static EvaluacionPreguntav2Fragment newInstance() {


        return new EvaluacionPreguntav2Fragment();

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(EvalPregViewModel.class);
        // TODO: Use the ViewModel

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        preferences = getActivity().getSharedPreferences("usrdata", Context.MODE_PRIVATE);
        String email = preferences.getString("email", "");
        int id_servicio = getActivity().getIntent().getExtras().getInt("id_servicio");
        IdSeccion = getActivity().getIntent().getExtras().getInt("id_seccion");
        String idEF = getActivity().getIntent().getExtras().getString("idE");

        EvaluacionData evalData = new EvaluacionData(getContext(), idEF);
        int id_area = evalData.getId_area();
        IdEvaluacion = evalData.getId_evaluacion();



    }

    @Override
    public void onStart() {
        super.onStart();

        refrescarRecyclerViewPreg();

   //     Toast.makeText(this.getContext(), "Hola regresamos ", Toast.LENGTH_SHORT).show();




    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_main2, container, false);

        preguntasRecyclerView = view.findViewById(R.id.recyclerview_preguntas);

        return view;

    }


    public void refrescarRecyclerViewPreg (){
        mViewModel.getListPreguntas(getContext(),IdSeccion,IdEvaluacion).observe(getViewLifecycleOwner(), new Observer<List<PreguntaRespuesta>>() {
            @Override
            public void onChanged(List<PreguntaRespuesta> preguntasRespuestas) {





                if(bandRecycler == true){
                    adaptador.recargarAdapter(preguntasRespuestas);
                    adaptador.notifyDataSetChanged();

                }else{
                    adaptador = new AdapterPreguntaResp(preguntasRespuestas, getContext());
                    preguntasRecyclerView.setAdapter(adaptador);
                    RecyclerView.LayoutManager layoutManager;
                    layoutManager = new LinearLayoutManager(getContext());
                    preguntasRecyclerView.setLayoutManager(layoutManager);

                    bandRecycler = true;
                }

            }
        });
    }

}