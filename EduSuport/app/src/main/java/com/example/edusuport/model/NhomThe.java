package com.example.edusuport.model;

public class NhomThe {
    private String idNhomThe;
    private String tenNhomThe;
    private String idMon;

    public NhomThe(String idNhomThe, String tenNhomThe, String idMon) {
        this.idNhomThe = idNhomThe;
        this.tenNhomThe = tenNhomThe;
        this.idMon = idMon;
    }

    public String getIdNhomThe() {
        return idNhomThe;
    }

    public void setIdNhomThe(String idNhomThe) {
        this.idNhomThe = idNhomThe;
    }

    public String getTenNhomThe() {
        return tenNhomThe;
    }

    public void setTenNhomThe(String tenNhomThe) {
        this.tenNhomThe = tenNhomThe;
    }

    public String getIdMon() {
        return idMon;
    }

    public void setIdMon(String idMon) {
        this.idMon = idMon;
    }
}
