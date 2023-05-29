package com.example.BTL_App_truyen_tranh.DTO;

public class TaiKhoan {
    private int idtk;
    private String tk;
    private String hoten;
    private String mk;

    public TaiKhoan() {
    }

    public TaiKhoan(int idtk, String tk, String hoten, String mk) {
        this.idtk = idtk;
        this.tk = tk;
        this.hoten = hoten;
        this.mk = mk;
    }

    public int getIdtk() {
        return idtk;
    }

    public void setIdtk(int idtk) {
        this.idtk = idtk;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getTk() {
        return tk;
    }

    public void setTk(String email) {
        this.tk = email;
    }

    public String getMk() {
        return mk;
    }

    public void setMk(String mk) {
        this.mk = mk;
    }
}
