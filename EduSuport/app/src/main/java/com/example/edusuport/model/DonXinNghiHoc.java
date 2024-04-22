package com.example.edusuport.model;
import java.io.Serializable;
import java.sql.Timestamp;
public class DonXinNghiHoc implements Serializable {
    private String IDDon;
    private String MSHS;
    private String LyDo;

    private Timestamp ThoiGian;
    private String TrangThai;

    public String getMaDon() {
        return IDDon;
    }

    public void setMaDon(String maDon) {
        IDDon = maDon;
    }

    public String getMSHS() {
        return MSHS;
    }

    public void setMSHS(String MSHS) {
        this.MSHS = MSHS;
    }

    public String getLyDo() {
        return LyDo;
    }

    public void setLyDo(String lyDo) {
        LyDo = lyDo;
    }

    public Timestamp getThoiGian() {
        return ThoiGian;
    }

    public void setThoiGian(Timestamp thoiGian) {
        ThoiGian = thoiGian;
    }

    public String getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(String trangThai) {
        TrangThai = trangThai;
    }

    public DonXinNghiHoc(String maDon, String MSHS, String lyDo, Timestamp thoiGian, String trangThai) {
        IDDon = maDon;
        this.MSHS = MSHS;
        LyDo = lyDo;
        ThoiGian = thoiGian;
        TrangThai = trangThai;
    }

    public DonXinNghiHoc() {
    }
}
