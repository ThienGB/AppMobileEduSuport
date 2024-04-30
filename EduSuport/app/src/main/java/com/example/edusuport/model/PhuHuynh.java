package com.example.edusuport.model;

import java.io.Serializable;

public class PhuHuynh implements Serializable {
    private String MSPH;
    private String Ten;
    private String IDLopHoc;
    private String MSHS;

    public String getMSPH() {
        return MSPH;
    }

    public void setMSPH(String MSPH) {
        this.MSPH = MSPH;
    }

    public String getTen() {
        return Ten;
    }

    public void setTen(String ten) {
        Ten = ten;
    }

    public String getIDLopHoc() {
        return IDLopHoc;
    }

    public void setIDLopHoc(String IDLopHoc) {
        this.IDLopHoc = IDLopHoc;
    }

    public String getMSHS() {
        return MSHS;
    }

    public void setMSHS(String MSHS) {
        this.MSHS = MSHS;
    }

    public PhuHuynh() {
    }

    public PhuHuynh(String MSPH, String ten, String IDLopHoc, String MSHS) {
        this.MSPH = MSPH;
        Ten = ten;
        this.IDLopHoc = IDLopHoc;
        this.MSHS = MSHS;
    }
}
