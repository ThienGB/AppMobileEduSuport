package com.example.edusuport.model;

import java.io.Serializable;

public class HocSinh implements Serializable {
    private String MSHS;
    private String Ten;
    private String IDLopHoc;

    public HocSinh(String MSSV, String ten, String IDLopHoc) {
        this.MSHS = MSSV;
        Ten = ten;
        this.IDLopHoc = IDLopHoc;
    }

    public String getMSHS() {
        return MSHS;
    }

    public void setMSHS(String MSSV) {
        this.MSHS = MSSV;
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

    public HocSinh() {
    }
}
