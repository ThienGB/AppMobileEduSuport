package com.example.edusuport.model;

import java.sql.Blob;
import java.util.Map;

public class TaiLieuHocTap {
    private String idTaiLieu;
    private String idmon;
    private String tenTaiLieu;
    private String urlfile;
    private Map thoiGian;

    public TaiLieuHocTap(String idTaiLieu, String idmon, String tenTaiLieu, String urlfile, Map thoiGian) {
        this.idTaiLieu = idTaiLieu;
        this.idmon = idmon;
        this.tenTaiLieu = tenTaiLieu;
        this.urlfile = urlfile;
        this.thoiGian = thoiGian;
    }

    public TaiLieuHocTap(String idmon, String tenTaiLieu, String urlfile, Map thoiGian) {
        this.idmon = idmon;
        this.tenTaiLieu = tenTaiLieu;
        this.urlfile = urlfile;
        this.thoiGian = thoiGian;
    }

    public String getIdTaiLieu() {
        return idTaiLieu;
    }

    public void setIdTaiLieu(String  idTaiLieu) {
        this.idTaiLieu = idTaiLieu;
    }

    public String getidmon() {
        return idmon;
    }

    public void setidmon(String idmon) {
        this.idmon = idmon;
    }

    public String getTenTaiLieu() {
        return tenTaiLieu;
    }

    public void setTenTaiLieu(String tenTaiLieu) {
        this.tenTaiLieu = tenTaiLieu;
    }

    public String geturlfile() {
        return urlfile;
    }

    public void seturlfile(String urlfile) {
        this.urlfile = urlfile;
    }

    public Map getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(Map thoiGian) {
        this.thoiGian = thoiGian;
    }
}
