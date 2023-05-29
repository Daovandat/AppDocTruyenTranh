package com.example.BTL_App_truyen_tranh.DTO;


public class DanhGia {
    int iddg,idtt,danhgia;
    String tk;
    public DanhGia() {
    }

    public DanhGia(int iddg, int idtt, int danhgia, String tk) {
        this.iddg = iddg;
        this.idtt = idtt;
        this.danhgia = danhgia;
        this.tk = tk;
    }


    public int getIddg() {
        return iddg;
    }

    public void setIddg(int iddg) {
        this.iddg = iddg;
    }

    public int getIdtt() {
        return idtt;
    }

    public void setIdtt(int idtt) {
        this.idtt = idtt;
    }

    public int getDanhgia() {
        return danhgia;
    }

    public void setDanhgia(int danhgia) {
        this.danhgia = danhgia;
    }

    public String getTk() {
        return tk;
    }

    public void setTk(String tk) {
        this.tk = tk;
    }
}
