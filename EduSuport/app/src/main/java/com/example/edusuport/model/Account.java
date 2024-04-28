package com.example.edusuport.model;

import android.net.Uri;

public class Account {
    private String idTK;
    private String taiKhoan;
    private String matKhau;
    private Uri urlAva;
    private String phoneNum;
    private String role;
    private String ngonNgu;
    private String themeDL;
    private String trangThai;

    public Account(String taiKhoan, String matKhau, Uri urlAva, String phoneNum, String role, String ngonNgu, String themeDL, String trangThai) {
        this.taiKhoan = taiKhoan;
        this.matKhau = matKhau;
        this.urlAva = urlAva;
        this.phoneNum = phoneNum;
        this.role = role;
        this.ngonNgu = ngonNgu;
        this.themeDL = themeDL;
        this.trangThai = trangThai;
    }

    public Account(String idTK, String taiKhoan, String matKhau, Uri urlAva, String phoneNum, String role, String ngonNgu, String themeDL, String trangThai) {
        this.idTK = idTK;
        this.taiKhoan = taiKhoan;
        this.matKhau = matKhau;
        this.urlAva = urlAva;
        this.phoneNum = phoneNum;
        this.role = role;
        this.ngonNgu = ngonNgu;
        this.themeDL = themeDL;
        this.trangThai = trangThai;
    }

    public String getIdTK() {
        return idTK;
    }

    public void setIdTK(String idTK) {
        this.idTK = idTK;
    }

    public String getTaiKhoan() {
        return taiKhoan;
    }

    public void setTaiKhoan(String taiKhoan) {
        this.taiKhoan = taiKhoan;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public Uri getUrlAva() {
        return urlAva;
    }

    public void setUrlAva(Uri urlAva) {
        this.urlAva = urlAva;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getNgonNgu() {
        return ngonNgu;
    }

    public void setNgonNgu(String ngonNgu) {
        this.ngonNgu = ngonNgu;
    }

    public String getThemeDL() {
        return themeDL;
    }

    public void setThemeDL(String themeDL) {
        this.themeDL = themeDL;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
}
