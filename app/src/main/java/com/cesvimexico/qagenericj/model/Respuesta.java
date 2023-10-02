package com.cesvimexico.qagenericj.model;

public class Respuesta {
    private int id_respuesta;
    private int id_pregunta;
    private String respuesta;
    private int puntaje;

    public int getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }

    public Respuesta() {
    }

    public Respuesta(int id_respuesta, int id_pregunta, String respuesta, int puntaje) {
        this.id_respuesta = id_respuesta;
        this.id_pregunta = id_pregunta;
        this.respuesta = respuesta;
        this.puntaje = puntaje;
    }

    public int getId_respuesta() {
        return id_respuesta;
    }

    public void setId_respuesta(int id_respuesta) {
        this.id_respuesta = id_respuesta;
    }

    public int getId_pregunta() {
        return id_pregunta;
    }

    public void setId_pregunta(int id_pregunta) {
        this.id_pregunta = id_pregunta;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

}
