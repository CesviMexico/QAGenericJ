package com.cesvimexico.qagenericj.model;

public class Evaluacion {
    private int id_evaluacion;
    private int id_servicio;
    private String id_usuario;
    private String f_crea;
    private String f_fin;
    private String f_ini;

    private String f_ini_eval;

    private String f_fin_eval;
    private String f_envio;

    private String f_ult_d;
    private String lugar;
    private String pic;
    private String sign;
    private String status;
    private int avance;
    private String evaluado;
    private String empresa;
    private String idE;
    private String sede;
    private String code_access;

    public Evaluacion() {

    }

    public Evaluacion(int id_evaluacion, int id_servicio, String id_usuario, String f_crea, String f_fin, String f_ini, String f_ult_d, String lugar, String pic, String status, int avance, String evaluado, String empresa, String idE,  String sede, String sign, String code_access, String f_ini_eval,String f_fin_eval, String f_envio ) {
        this.id_evaluacion = id_evaluacion;
        this.id_servicio = id_servicio;
        this.id_usuario = id_usuario;
        this.f_crea = f_crea;
        this.f_fin = f_fin;
        this.f_ini = f_ini;
        this.f_ult_d = f_ult_d;
        this.lugar = lugar;
        this.pic = pic;
        this.status = status;
        this.avance = avance;
        this.evaluado = evaluado;
        this.empresa = empresa;
        this.idE = idE;
        this.sede = sede;
        this.sign = sign;
        this.f_ini_eval = f_ini_eval;
        this.f_fin_eval = f_fin_eval;
        this.f_envio = f_envio;
        this.code_access = code_access;

    }

//    public int getId_evaluacion() {
//        return id_evaluacion;
//    }
//
//    public void setId_evaluacion(int id_evaluacion) {
//        this.id_evaluacion = id_evaluacion;
//    }

    public int getId_servicio() {
        return id_servicio;
    }

    public void setId_servicio(int id_servicio) {
        this.id_servicio = id_servicio;
    }

    public String getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getF_crea() {
        return f_crea;
    }

    public void setF_crea(String f_crea) {
        this.f_crea = f_crea;
    }

    public String getF_fin() {
        return f_fin;
    }

    public void setF_fin(String f_fin) {
        this.f_fin = f_fin;
    }

    public String getF_ini() {
        return f_ini;
    }

    public void setF_ini(String f_ini) {
        this.f_ini = f_ini;
    }

    public String getF_ult_d() {
        return f_ult_d;
    }

    public void setF_ult_d(String f_ult_d) {
        this.f_ult_d = f_ult_d;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getAvance() {
        return avance;
    }

    public void setAvance(int avance) {
        this.avance = avance;
    }

    public String getEvaluado() {
        return evaluado;
    }

    public void setEvaluado(String evaluado) {
        this.evaluado = evaluado;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getIdE() {
        return idE;
    }

    public void setIdE(String idE) {
        this.idE = idE;
    }

    public String getSede() {
        return sede;
    }

    public void setSede(String sede) {
        this.sede = sede;
    }

    public int getId_evaluacion() {
        return id_evaluacion;
    }

    public void setId_evaluacion(int id_evaluacion) {
        this.id_evaluacion = id_evaluacion;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getCode_access() {
        return code_access;
    }

    public void setCode_access(String code_access) {
        this.code_access = code_access;
    }

    public String getF_ini_eval() {
        return f_ini_eval;
    }

    public void setF_ini_eval(String f_ini_eval) {
        this.f_ini_eval = f_ini_eval;
    }

    public String getF_fin_eval() {
        return f_fin_eval;
    }

    public void setF_fin_eval(String f_fin_eval) {
        this.f_fin_eval = f_fin_eval;
    }

    public String getF_envio() {
        return f_envio;
    }

    public void setF_envio(String f_envio) {
        this.f_envio = f_envio;
    }
}
