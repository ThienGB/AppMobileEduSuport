package com.example.edusuport.model;

import java.io.Serializable;

public class PhuHuynh implements Serializable {
    private String MSPH;
    private String Ten;
    private String IDLopHoc;
    private String MSHS;
    private String Matkhau;

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
    public String getMatkhau() {
        return Matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.Matkhau = matkhau;
    }

    public PhuHuynh() {
    }
    public PhuHuynh(String MSPH,String mssv, String ten, String idlophoc, String matkhau) {
        this.MSHS= mssv;
        this.Ten = ten;
        this.IDLopHoc = idlophoc;
        this.Matkhau = matkhau;
    }

    public PhuHuynh(String MSPH, String ten, String IDLopHoc, String MSHS) {
        this.MSPH = MSPH;
        Ten = ten;
        this.IDLopHoc = IDLopHoc;
        this.MSHS = MSHS;
    }
}
