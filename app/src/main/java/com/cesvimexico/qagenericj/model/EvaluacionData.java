package com.cesvimexico.qagenericj.model;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.content.Context;
import android.util.Log;

import com.cesvimexico.qagenericj.EvaluacionForm;
import com.cesvimexico.qagenericj.db.DBManager;
import com.google.firebase.firestore.FirebaseFirestore;


public class EvaluacionData {
    private Cursor cursor;
    private DBManager bdm;
    private SQLiteDatabase db;
    private FirebaseFirestore dbFB;
    private String idE;

    private int id_evaluacion;
    private int id_servicio;
    private String id_usuario;
    private String status;
    private int prc_avance;

    private int id_area;

    public EvaluacionData(Context context, String idE) {
        this.bdm = new DBManager(context);
        this.db = bdm.getWritableDatabase();
        this.idE = idE;



        String sql = "SELECT `qa_zeval`.`id_evaluacion`, `qa_zeval`.`id_servicio`,  `qa_zeval`.`id_usuario`, `qa_zeval`.`status`, `qa_zeval`.`prc_avance`, `qa_serv`.`id_area` " +
                "FROM `qa_serv` INNER JOIN `qa_zeval` ON (`qa_serv`.`id_servicio` = `qa_zeval`.`id_servicio`) " +
                "WHERE `qa_zeval`.`id_evaluacion_fb`='" + idE + "'";
        cursor = db.rawQuery(sql, null);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                this.id_evaluacion = cursor.getInt(0);
                this.id_servicio = cursor.getInt(1);
                this.id_usuario = cursor.getString(2);
                this.status = cursor.getString(3);
                this.prc_avance = cursor.getInt(4);
                this.id_area = cursor.getInt(5);
            }
        }

    }

    public int getId_evaluacion() {
        return id_evaluacion;
    }

    public Cursor getCursor() {
        return cursor;
    }

    public String getIdE() {
        return idE;
    }

    public int getId_servicio() {
        return id_servicio;
    }

    public String getId_usuario() {
        return id_usuario;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
        String sql = "UPDATE qa_zeval SET status='" + status + "' WHERE id_evaluacion_fb='" + idE + "'";
        db.execSQL(sql);

    }

    public int getPrc_avance() {
        return prc_avance;
    }

    public void setPrc_avance(int prc_avance) {
        this.prc_avance = prc_avance;
        String sql = "UPDATE qa_zeval SET prc_avance='" + prc_avance + "' WHERE id_evaluacion_fb='" + idE + "'";
        db.execSQL(sql);
    }

    public int calPrcAvance() {
        int promedio = 0;
        String sql = "SELECT `qa_pre`.`id_seccion`,\n" +
                "(SELECT COUNT(0) as total \n" +
                "FROM `qa_zeval_resp` INNER JOIN `qa_pre` AS qp ON (`qa_zeval_resp`.`id_pregunta` = `qp`.`id_pregunta`)\n" +
                "WHERE `qp`.`id_seccion` = `qa_pre`.`id_seccion` and `qp`.`status` = 'ALTA'  AND  ( `qa_zeval_resp`.`id_respuesta` <> '0' or  `qa_zeval_resp`.respuesta <> '' or `qa_zeval_resp`.`aplica` = 'SI' ) AND `qa_zeval_resp`.`id_evaluacion` = '" + id_evaluacion + "' )*100/COUNT(0) as prc \n" +
                "   ,sum(qa_pre.puntaje) as puntaje_max , COUNT(qa_pre.puntaje) as trg_max_max FROM \n" +
                "`qa_sec` INNER JOIN `qa_pre` ON (`qa_sec`.`id_seccion` = `qa_pre`.`id_seccion`) where qa_sec.id_servicio = '"+id_servicio+"'  and `qa_pre`.`status` = 'ALTA' and  qa_sec.nombre <> 'Fotos alternas' \n   " +
                "GROUP BY `qa_pre`.`id_seccion`";

        cursor = db.rawQuery(sql, null);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                if(cursor.getInt(2) == 0 && cursor.getInt(3) == 0){ //// puntaje_max   and trg_max_max
                    promedio = promedio + 100;
                }else{
                    promedio = promedio + cursor.getInt(1);
                }

            }
        }
        if(promedio == 0){
            promedio = 0;
        }else{
            promedio = promedio/cursor.getCount();
        }

        setPrc_avance(promedio);
        Log.d("calPrcAvance", String.valueOf(promedio));
        return promedio;
    }

    public int getId_area() {
        return id_area;
    }

}
