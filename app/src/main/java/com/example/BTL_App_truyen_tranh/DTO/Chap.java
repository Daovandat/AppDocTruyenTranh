package com.example.BTL_App_truyen_tranh.DTO;

import java.util.List;

public class Chap {
    private int idChap;
    private int idtt;
    private String tenChap;

    public Chap(int idChap, int idtt, String tenChap) {
        this.idChap = idChap;
        this.idtt = idtt;
        this.tenChap = tenChap;
    }

    public Chap() {
    }

    public int getIdtt() {
        return idtt;
    }

    public void setIdtt(int idtt) {
        this.idtt = idtt;
    }

    public int getIdChap() {
        return idChap;
    }

    public void setIdChap(int idChap) {
        this.idChap = idChap;
    }

    public String getTenChap() {
        return tenChap;
    }

    public void setTenChap(String tenChap) {
        this.tenChap = tenChap;
    }

    @Override
    public String toString() {
        return tenChap;
    }
}
