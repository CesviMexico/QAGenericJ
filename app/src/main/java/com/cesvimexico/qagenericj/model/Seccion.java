package com.cesvimexico.qagenericj.model;

public class Seccion {

    private int id_seccion;
    private int id_servicio;
    private String instrucciones;
    private String nombre;


    public Seccion(int id_seccion, int id_servicio, String instrucciones, String nombre) {
        this.id_seccion = id_seccion;
        this.id_servicio = id_servicio;
        this.instrucciones = instrucciones;
        this.nombre = nombre;
    }

    public int getId_seccion() {
        return id_seccion;
    }

    public void setId_seccion(int id_seccion) {
        this.id_seccion = id_seccion;
    }

    public int getId_servicio() {
        return id_servicio;
    }

    public void setId_servicio(int id_servicio) {
        this.id_servicio = id_servicio;
    }

    public String getInstrucciones() {
        return instrucciones;
    }

    public void setInstrucciones(String instrucciones) {
        this.instrucciones = instrucciones;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
