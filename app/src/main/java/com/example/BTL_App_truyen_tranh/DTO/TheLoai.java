package com.example.BTL_App_truyen_tranh.DTO;

public class TheLoai {
    private int id;
    private String tenTheLoai;

    public TheLoai(int id, String tenTheLoai) {
        this.id = id;
        this.tenTheLoai = tenTheLoai;
    }

    public TheLoai() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenTheLoai() {
        return tenTheLoai;
    }

    public void setTenTheLoai(String tenTheLoai) {
        this.tenTheLoai = tenTheLoai;
    }

    @Override
    public String toString() {
        return tenTheLoai ;
    }
}
