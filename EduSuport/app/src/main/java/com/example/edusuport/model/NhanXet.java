package com.example.edusuport.model;

public class NhanXet {
    private String noiDung;
    private String danhGia;

    public NhanXet() {
        // Required empty public constructor
    }

    public NhanXet(String noiDung, String danhGia) {
        this.noiDung = noiDung;
        this.danhGia = danhGia;
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
