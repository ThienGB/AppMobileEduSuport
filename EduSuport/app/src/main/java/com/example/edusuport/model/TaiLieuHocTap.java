package com.example.edusuport.model;

import java.sql.Blob;

public class TaiLieuHocTap {
    private String idTaiLieu;
    private int mon;
    private String tenTaiLieu;
    private Blob noiDung;
    private String thoiGian;

    public TaiLieuHocTap(String idTaiLieu, int mon, String tenTaiLieu, Blob noiDung, String thoiGian) {
        this.idTaiLieu = idTaiLieu;
        this.mon = mon;
        this.tenTaiLieu = tenTaiLieu;
        this.noiDung = noiDung;
        this.thoiGian = thoiGian;
    }

    public TaiLieuHocTap(String tenTaiLieu, String thoiGian) {
        this.tenTaiLieu = tenTaiLieu;
        this.thoiGian = thoiGian;
    }


    public String getIdTaiLieu() {
        return idTaiLieu;
    }

    public void setIdTaiLieu(String  idTaiLieu) {
        this.idTaiLieu = idTaiLieu;
    }

    public int getMon() {
        return mon;
    }

    public void setMon(int mon) {
        this.mon = mon;
    }

    public String getTenTaiLieu() {
        return tenTaiLieu;
    }

    public void setTenTaiLieu(String tenTaiLieu) {
        this.tenTaiLieu = tenTaiLieu;
    }

    public Blob getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(Blob noiDung) {
        this.noiDung = noiDung;
    }

    public String getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(String thoiGian) {
        this.thoiGian = thoiGian;
    }
}
