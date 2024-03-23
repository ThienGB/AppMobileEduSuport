package com.example.bt1buoi6quanlypb.model;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class NhanVien extends Infor implements Serializable {
    private static final long  serialVersionIUD = 1L;
    private boolean gioitinh;
    private ChucVu chucvu;
    private PhongBan phongban;

    public NhanVien(String ma, String ten, boolean gioitinh, ChucVu chucvu, PhongBan phongban) {
        super(ma, ten);
        this.gioitinh = gioitinh;
        this.chucvu = chucvu;
        this.phongban = phongban;
    }

    public NhanVien(String ma, String ten, boolean gioitinh) {
        super(ma, ten);
        this.gioitinh = gioitinh;
    }
    public NhanVien(){
        super();
    }
    @NonNull
    @Override
    public String toString(){
        return super.toString();
    }

    public boolean isGioitinh() {
        return gioitinh;
    }

    public void setGioitinh(boolean gioitinh) {
        this.gioitinh = gioitinh;
    }

    public ChucVu getChucvu() {
        return chucvu;
    }

    public void setChucvu(ChucVu chucvu) {
        this.chucvu = chucvu;
    }

    public PhongBan getPhongban() {
        return phongban;
    }

    public void setPhongban(PhongBan phongban) {
        this.phongban = phongban;
    }

}
