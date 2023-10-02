package com.cesvimexico.qagenericj.model;

public class Service {

    private int id_servicio;
    private int id_area;
    private String servicio;
    private String descripcion;
    private String instrucciones;
    private String status;

    public Service(int id_servicio, int id_area, String servicio, String descripcion, String instrucciones, String status) {
        this.id_servicio = id_servicio;
        this.id_area = id_area;
        this.servicio = servicio;
        this.descripcion = descripcion;
        this.instrucciones = instrucciones;
        this.status = status;
    }

    public int getId_servicio() {
        return id_servicio;
    }

    public void setId_servicio(int id_servicio) {
        this.id_servicio = id_servicio;
    }

    public int getId_area() {
        return id_area;
    }

    public void setId_area(int id_area) {
        this.id_area = id_area;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getInstrucciones() {
        return instrucciones;
    }

    public void setInstrucciones(String instrucciones) {
        this.instrucciones = instrucciones;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
