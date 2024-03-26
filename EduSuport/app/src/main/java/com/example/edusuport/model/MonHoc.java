package com.example.edusuport.model;

public class MonHoc {
    private int idMon;
    private String tenMon;
    public MonHoc(int idMon, String tenMon) {
        this.idMon = idMon;
        this.tenMon = tenMon;
    }

    public int getIdMon() {
        return idMon;
    }

    public void setIdMon(int idMon) {
        this.idMon = idMon;
    }

    public String getTenMon() {
        return tenMon;
    }

    public void setTenMon(String tenMon) {
        this.tenMon = tenMon;
    }
}
