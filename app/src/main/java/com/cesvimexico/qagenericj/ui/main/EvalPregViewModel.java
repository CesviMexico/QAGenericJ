package com.cesvimexico.qagenericj.ui.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cesvimexico.qagenericj.db.DBManager;
import com.cesvimexico.qagenericj.model.Pregunta;
import com.cesvimexico.qagenericj.model.PreguntaRespuesta;
import com.cesvimexico.qagenericj.model.Respuesta;

import java.util.ArrayList;
import java.util.List;

public class EvalPregViewModel extends ViewModel {
    // TODO: Implement the ViewModel

    @SuppressLint("Range")
    public LiveData<List<PreguntaRespuesta>> getListPreguntas(Context context, int IdSeccion, int IdEvaluacion){
        MutableLiveData<List<PreguntaRespuesta>> ListaPreguntasResp;
        ListaPreguntasResp = new MutableLiveData<List<PreguntaRespuesta>>();


        List<PreguntaRespuesta> listPregResp = new ArrayList<>();

        validaDataRespevaluacion(context, IdSeccion,IdEvaluacion );

        String SqlConsulta = "SELECT    `qa_zeval_resp`.`id_pregunta`,   `qa_crit`.`criterio`,   `qa_pre`.`pregunta`,   `qa_pre`.`tipo`,   `qa_zeval_resp`.`id_respuesta`,   `qa_zeval_resp`.`respuesta`,   `qa_zeval_resp`.`comentario`,   `qa_zeval_resp`.`id_evaluacion` ,  `qa_pre`.`orden`,   count(qa_zeval_evid.id_evidencia) as treg_evidencia FROM   `qa_pre`   INNER JOIN `qa_zeval_resp` ON (`qa_pre`.`id_pregunta` = `qa_zeval_resp`.`id_pregunta`)   INNER JOIN `qa_crit` ON (`qa_pre`.`id_criterio` = `qa_crit`.`id_criterio`) LEFT OUTER JOIN `qa_zeval_evid` ON (`qa_zeval_resp`.`id_pregunta` = `qa_zeval_evid`.`id_pregunta`)  AND (`qa_zeval_resp`.`id_evaluacion` = `qa_zeval_evid`.`id_evaluacion`) WHERE   `qa_pre`.`id_seccion` = '"+IdSeccion+"' AND    `qa_zeval_resp`.`id_evaluacion` = '"+IdEvaluacion+"' group by    `qa_zeval_resp`.`id_evaluacion`,   `qa_zeval_resp`.`id_pregunta`  order by  `qa_pre`.`orden`";
        DBManager bdm = new DBManager(context);
        SQLiteDatabase db = bdm.getWritableDatabase();
        Cursor cursorPR = db.rawQuery(SqlConsulta, null);
        while(cursorPR.moveToNext()){


            ArrayList<Respuesta> resultR = new ArrayList<>();
            /*RESPUESTAS POR PREGUNTAS*/
            String sql = "SELECT id_respuesta, id_pregunta, respuesta, puntaje, `default` FROM `qa_rsp` WHERE `qa_rsp`.`id_pregunta` = '" + cursorPR.getInt(cursorPR.getColumnIndex("id_pregunta")) + "' AND `qa_rsp`.`status` = 'ALTA'";
            Cursor cursorResp = db.rawQuery(sql, null);

            while (cursorResp.moveToNext()) {
                resultR.add(new Respuesta(
                        cursorResp.getInt(cursorResp.getColumnIndex("id_respuesta")),
                        cursorResp.getInt(cursorResp.getColumnIndex("id_pregunta")),
                        cursorResp.getString(cursorResp.getColumnIndex("respuesta")),
                        cursorResp.getInt(cursorResp.getColumnIndex("puntaje"))
                ));

            }
            cursorResp.close();


            listPregResp.add(new PreguntaRespuesta(
                    cursorPR.getInt(cursorPR.getColumnIndex("id_pregunta")),
                    cursorPR.getString(cursorPR.getColumnIndex("criterio")),
                    cursorPR.getString(cursorPR.getColumnIndex("pregunta")),
                    cursorPR.getString(cursorPR.getColumnIndex("tipo")),
                    resultR,
                    cursorPR.getInt(cursorPR.getColumnIndex("id_respuesta")),
                    cursorPR.getString(cursorPR.getColumnIndex("respuesta")),
                    cursorPR.getInt(cursorPR.getColumnIndex("id_evaluacion")),
                    cursorPR.getString(cursorPR.getColumnIndex("comentario")),
                    cursorPR.getInt(cursorPR.getColumnIndex("orden")),
                    cursorPR.getInt(cursorPR.getColumnIndex("treg_evidencia"))
            ));

        }

        cursorPR.close();
        db.close();

        ListaPreguntasResp.setValue(listPregResp);

        return ListaPreguntasResp;
    }


    public void validaDataRespevaluacion(Context context, int IdSeccion, int IdEvaluacion){

        DBManager bdm = new DBManager(context);
        SQLiteDatabase db = bdm.getWritableDatabase();
        String SqlValidar = "SELECT  count(`qa_zeval_resp`.`id_pregunta`) as tpreg FROM   `qa_zeval_resp`   INNER JOIN `qa_pre` ON (`qa_zeval_resp`.`id_pregunta` = `qa_pre`.`id_pregunta`)   INNER JOIN `qa_zeval` ON (`qa_zeval_resp`.`id_evaluacion` = `qa_zeval`.`id_evaluacion`) WHERE   `qa_pre`.`id_seccion` = '"+IdSeccion+"' AND    `qa_zeval`.`id_evaluacion` = '"+IdEvaluacion+"' ";
        Cursor cursorR = db.rawQuery(SqlValidar, null);
        cursorR.moveToFirst();
        @SuppressLint("Range") String tReg = cursorR.getString(cursorR.getColumnIndex("tpreg"));

        cursorR.close();


        if(tReg.equals("0")){
            //Toast.makeText(context, "Hay que insertar los datos", Toast.LENGTH_SHORT).show();

            String SqlConsulta = "SELECT   `qa_pre`.`id_pregunta`,      `qa_pre`.`puntaje` FROM   `qa_pre` WHERE   `qa_pre`.`id_seccion` = '"+IdSeccion+"' and `qa_pre`.`status` = 'ALTA' ";
            cursorR = db.rawQuery(SqlConsulta, null);
            while(cursorR.moveToNext()){

                @SuppressLint("Range") String idPregunta = cursorR.getString(cursorR.getColumnIndex("id_pregunta"));
                @SuppressLint("Range") Double puntaje = cursorR.getDouble(cursorR.getColumnIndex("puntaje"));

                String Aplica = "SI";
                if(puntaje == 0){
                    Aplica = "NO";
                }

                 String sql = "INSERT INTO   `qa_zeval_resp`(   `id_evaluacion`,   `id_pregunta`,   `id_respuesta`,   `aplica`  ) VALUES(   '" + IdEvaluacion + "',  '"+idPregunta+"' ,   '0',   '"+Aplica+"') ";
                 db.execSQL(sql);
            }
            cursorR.close();
        }

        db.close();

    }
}