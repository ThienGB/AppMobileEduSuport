package com.example.edusuport.model;

public class NhanXet {
    private String idNhanXet;
    private String idGiaoVien;
    private String idHocSinh;
    private String noiDung;
    private String danhGia;

    public NhanXet() {
        // Required empty public constructor
    }

    public NhanXet(String idNhanXet,String idGiaoVien,String idHocSinh,String noiDung, String danhGia) {
        this.idNhanXet = idNhanXet;
        this.idGiaoVien = idGiaoVien;
        this.idHocSinh = idHocSinh;
        this.noiDung = noiDung;
        this.danhGia = danhGia;
    }

    public String getIdNhanXet(){return idNhanXet;}

    public void setIdNhanXet(String idNhanXet) {
        this.idNhanXet = idNhanXet;
    }

    public String getIdGiaoVien() {
        return idGiaoVien;
    }

    public void setIdGiaoVien(String idGiaoVien) {
        this.idGiaoVien = idGiaoVien;
    }

    public String getIdHocSinh() {
        return idHocSinh;
    }

    public void setIdHocSinh(String idHocSinh) {
        this.idHocSinh = idHocSinh;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public String getDanhGia() {
        return danhGia;
    }

    public void setDanhGia(String danhGia) {
        this.danhGia = danhGia;
    }
}