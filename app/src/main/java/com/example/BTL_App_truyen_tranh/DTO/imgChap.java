package com.example.BTL_App_truyen_tranh.DTO;

public class imgChap {
    private int idimgChap;
    private int idtt;
    private String tenChap;
    private byte[] img;

    public imgChap() {
    }

    public imgChap(int idimgChap, int idtt, String tenChap, byte[] img) {
        this.idimgChap = idimgChap;
        this.idtt = idtt;
        this.tenChap = tenChap;
        this.img = img;
    }

    public int getIdtt() {
        return idtt;
    }

    public void setIdtt(int idtt) {
        this.idtt = idtt;
    }

    public int getIdimgChap() {
        return idimgChap;
    }

    public void setIdimgChap(int idimgChap) {
        this.idimgChap = idimgChap;
    }

    public String getTenChap() {
        return tenChap;
    }

    public void setTenChap(String tenChap) {
        this.tenChap = tenChap;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }
}
