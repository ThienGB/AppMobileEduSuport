package com.example.edusuport.model;

public class LopHoc {
    private String MaLop;
    private String TenLop;
    private String SoLuong;

    public LopHoc() {
        // Constructor mặc định cần thiết cho Firebase
    }

    public LopHoc(String MaLop, String TenLop, String SoLuong) {
        this.MaLop = MaLop;
        this.TenLop = TenLop;
        this.SoLuong = SoLuong;
    }

    public String getMaLop() {
        return MaLop;
    }

    public void setMaLop(String MaLop) {
        this.MaLop = MaLop;
    }

    public String getTenLop() {
        return TenLop;
    }

    public void setTenLop(String TenLop) {
        this.TenLop = TenLop;
    }

    public String getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(String SoLuong) {
        this.SoLuong = SoLuong;
    }
}
