package com.example.edusuport.model;

public class HocSinh {
    private String maSo;
    private String hoTen;

    public HocSinh() {
        // Constructor mặc định cần thiết cho Firebase
    }

    public HocSinh(String maSo,String hoTen) {
        this.maSo = maSo;
        this.hoTen = hoTen;
    }
    public String getMaSo() {
        return maSo;
    }
    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public void setMaSo(String maSo) {
        this.maSo = maSo;
    }

}
