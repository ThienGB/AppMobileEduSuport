package com.example.edusuport.model;

public class LopHoc {
    private String idLopHoc;
    private String idGiaoVien;
    private String tenLopHoc;

    public String getIdLopHoc() {
        return idLopHoc;
    }

    public void setIdLopHoc(String idLopHoc) {
        this.idLopHoc = idLopHoc;
    }

    public String getIdGiaoVien() {
        return idGiaoVien;
    }

    public void setIdGiaoVien(String idGiaoVien) {
        this.idGiaoVien = idGiaoVien;
    }

    public String getTenLopHoc() {
        return tenLopHoc;
    }

    public void setTenLopHoc(String tenLopHoc) {
        this.tenLopHoc = tenLopHoc;
    }


    public LopHoc(String idLopHoc, String idGiaoVien, String tenLopHoc) {
        this.idLopHoc = idLopHoc;
        this.idGiaoVien = idGiaoVien;
        this.tenLopHoc = tenLopHoc;
    }


    public LopHoc(String idGiaoVien, String tenLopHoc) {
        this.idGiaoVien = idGiaoVien;
        this.tenLopHoc = tenLopHoc;
    }

    public LopHoc() {
    }

}
