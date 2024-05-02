package com.example.edusuport.model;

public class ThongBao {
    private String IdThongBao;
    private String Idnguoigui;
    private String Idnguoinhan;
    private String thoigian;
    private String noiDung;

    public ThongBao() {
        // Required empty public constructor for Firebase
    }

    public ThongBao(String idthongBao, String noidung,String idnguoinhan, String idnguoigui, String thoigian) {

        this.noiDung = noidung;
        this.Idnguoigui= idnguoigui;
        this.Idnguoinhan = idnguoinhan;
        this.thoigian = thoigian;
    }

    public String getIdThongBao() {
        return IdThongBao;
    }

    public void setIdThongBao(String idThongBao) {
        IdThongBao = idThongBao;
    }

    public String getIdnguoigui() {
        return Idnguoigui;
    }

    public void setIdnguoigui(String idnguoigui) {
        Idnguoigui = idnguoigui;
    }

    public String getIdnguoinhan() {
        return Idnguoinhan;
    }

    public void setIdnguoinhan(String idnguoinhan) {
        Idnguoinhan = idnguoinhan;
    }
    public String getThoigian() {
        return thoigian;
    }

    public void setThoigian(String thoigian) {
        this.thoigian = thoigian;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

}
