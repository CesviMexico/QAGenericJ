package com.cesvimexico.qagenericj.model;

public class Area {
    private int id_area;
    private String area;
    private String status;

    public int getId_area() {
        return id_area;
    }

    public Area(int id_area, String area, String status) {
        this.id_area = id_area;
        this.area = area;
        this.status = status;
    }

    public void setId_area(int id_area) {
        this.id_area = id_area;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
