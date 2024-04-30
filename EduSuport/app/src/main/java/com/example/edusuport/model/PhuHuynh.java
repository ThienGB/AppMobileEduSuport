package com.example.edusuport.model;

public class PhuHuynh {
    private String maSo;
    private String ten;

    public PhuHuynh() {
        // Constructor mặc định cần thiết cho Firebase
    }

    public PhuHuynh(String maSo, String ten) {
        this.maSo = maSo;
        this.ten = ten;
    }

    public String getMaSo() {
        return maSo;
    }

    public void setMaSo(String maSo) {
        this.maSo = maSo;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }
}
