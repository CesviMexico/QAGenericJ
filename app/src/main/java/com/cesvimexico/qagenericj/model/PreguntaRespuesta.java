package com.cesvimexico.qagenericj.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.cesvimexico.qagenericj.db.DBManager;

import java.util.ArrayList;

public class PreguntaRespuesta {
    private int id_pregunta;
    private String criterio;
    private String pregunta;
    private String tipo;
    private ArrayList<Respuesta> respuestas;

    private int IdRespuesta;
    private String Respuesta;
    private String Comentario;

    private int IdEvaluacion;

    private int Orden;

    private int NoEvidencias;

    public PreguntaRespuesta(int id_pregunta, String criterio, String pregunta, String tipo, ArrayList<Respuesta> respuestas, int id_respuesta, String respuesta, int id_evaluacion, String comentario, int orden, int noevidencias) {
        this.id_pregunta = id_pregunta;
        this.criterio = criterio;
        this.pregunta = pregunta;
        this.respuestas = respuestas;
        this.tipo = tipo;
        this.IdRespuesta = id_respuesta;
        this.Respuesta = respuesta;
        this.IdEvaluacion = id_evaluacion;
        this.Comentario = comentario;
        this.Orden = orden;
        this.NoEvidencias = noevidencias;
    }

    public int getId_pregunta() {
        return id_pregunta;
    }

    public void setId_pregunta(int id_pregunta) {
        this.id_pregunta = id_pregunta;
    }

    public String getCriterio() {
        return criterio;
    }

    public void setCriterio(String criterio) {
        this.criterio = criterio;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public ArrayList<Respuesta> getRespuestas() {
        return respuestas;
    }

    public void setRespuestas(ArrayList<Respuesta> respuestas) {
        this.respuestas = respuestas;
    }

    public int getIdRespuesta() {
        return IdRespuesta;
    }

    public void setIdRespuesta(int idRespuesta, Context context) {
        IdRespuesta = idRespuesta;
        DBManager bdm = new DBManager(context);
        SQLiteDatabase db = bdm.getWritableDatabase();
        String sql = "UPDATE qa_zeval_resp SET id_respuesta = '" + idRespuesta + "' WHERE id_evaluacion='" + this.IdEvaluacion + "' AND id_pregunta='" + this.id_pregunta + "'";
        db.execSQL(sql);
        db.close();
        bdm.close();
    }

    public String getRespuesta() {
        return Respuesta;
    }

    public void setRespuesta(String respuesta, Context context) {
        Respuesta = respuesta;
        DBManager bdm = new DBManager(context);
        SQLiteDatabase db = bdm.getWritableDatabase();
        String sql = "UPDATE qa_zeval_resp SET respuesta = '" + respuesta + "' WHERE id_evaluacion='" + this.IdEvaluacion + "' AND id_pregunta='" + this.id_pregunta + "'";
        db.execSQL(sql);
        db.close();
        bdm.close();
    }

    public int getIdEvaluacion() {
        return IdEvaluacion;
    }

    public void setIdEvaluacion(int idEvaluacion) {
        IdEvaluacion = idEvaluacion;
    }

    public String getComentario() {
        return Comentario;
    }

    public void setComentario(String comentario, Context context) {
        Comentario = comentario;
        DBManager bdm = new DBManager(context);
        SQLiteDatabase db = bdm.getWritableDatabase();
        String sql = "UPDATE qa_zeval_resp SET comentario = '" + comentario + "' WHERE id_evaluacion='" + this.IdEvaluacion + "' AND id_pregunta='" + this.id_pregunta + "'";
        db.execSQL(sql);
        db.close();
        bdm.close();
    }

    public int getOrden() {
        return Orden;
    }

    public void setOrden(int orden) {
        Orden = orden;
    }

    public int getNoEvidencias() {
        return NoEvidencias;
    }

    public void setNoEvidencias(int noEvidencias) {
        NoEvidencias = noEvidencias;
    }
}
