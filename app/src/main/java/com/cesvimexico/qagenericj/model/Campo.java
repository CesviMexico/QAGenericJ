package com.cesvimexico.qagenericj.model;

public class Campo {

    private int id_campo;
    private int id_servicio;
    private String categoria;
    private String tipo;
    private String etiqueta;
    private int mostrar;
    private String status;
    private int longt;
    private int orden;


    public Campo(int id_campo, int id_servicio, String categoria, String tipo, String etiqueta, int mostrar, String status, int longt,int orden) {
        this.id_campo = id_campo;
        this.id_servicio = id_servicio;
        this.categoria = categoria;
        this.tipo = tipo;
        this.etiqueta = etiqueta;
        this.mostrar = mostrar;
        this.status = status;
        this.orden = longt;
        this.orden = orden;
    }

    public int getId_campo() {
        return id_campo;
    }

    public void setId_campo(int id_campo) {
        this.id_campo = id_campo;
    }

    public int getId_servicio() {
        return id_servicio;
    }

    public void setId_servicio(int id_servicio) {
        this.id_servicio = id_servicio;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public int getMostrar() {
        return mostrar;
    }

    public void setMostrar(int mostrar) {
        this.mostrar = mostrar;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getLongt() {
        return longt;
    }

    public void setLongt(int longt) {
        this.longt = longt;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }


}
