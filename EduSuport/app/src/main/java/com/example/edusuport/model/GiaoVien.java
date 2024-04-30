package com.example.edusuport.model;

public class GiaoVien {
    private String IDGiaoVien;
    private String TenGiaoVien;

    public String getIDGiaoVien() {
        return IDGiaoVien;
    }

    public void setIDGiaoVien(String IDGiaoVien) {
        this.IDGiaoVien = IDGiaoVien;
    }

    public String getTenGiaoVien() {
        return TenGiaoVien;
    }

    public void setTenGiaoVien(String tenGiaoVien) {
        TenGiaoVien = tenGiaoVien;
    }

    public GiaoVien() {
    }

    public GiaoVien(String IDGiaoVien, String tenGiaoVien) {
        this.IDGiaoVien = IDGiaoVien;
        TenGiaoVien = tenGiaoVien;
    }
}
