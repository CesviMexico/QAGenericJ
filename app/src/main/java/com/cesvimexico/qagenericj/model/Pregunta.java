package com.cesvimexico.qagenericj.model;

import java.util.ArrayList;

public class Pregunta {
    private int id_pregunta;
    private String criterio;
    private String pregunta;
    private String tipo;
    private ArrayList<Respuesta> respuestas;

    public int getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }

    private int puntaje;

    public Pregunta(int id_pregunta, String criterio, String pregunta, String tipo, ArrayList<Respuesta> respuestas, int puntaje) {
        this.id_pregunta = id_pregunta;
        this.criterio = criterio;
        this.pregunta = pregunta;
        this.respuestas = respuestas;
        this.tipo = tipo;
        this.puntaje = puntaje;
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
}
