package com.example.BTL_App_truyen_tranh.DTO;

import java.util.List;

public class TruyenTranh {
    int idTruyen;
    String tenTruyen;
    String ngayDang;
    String tinhTrang;
    String theLoai;
    String gioiThieu;
    byte[] img;

    public TruyenTranh() {
    }

    public TruyenTranh(int idTruyen, String tenTruyen, String ngayDang, String tinhTrang, String theLoai, String gioiThieu, byte[] img) {
        this.idTruyen = idTruyen;
        this.tenTruyen = tenTruyen;
        this.ngayDang = ngayDang;
        this.tinhTrang = tinhTrang;
        this.theLoai = theLoai;
        this.gioiThieu = gioiThieu;
        this.img = img;
    }

    public int getIdTruyen() {
        return idTruyen;
    }

    public void setIdTruyen(int idTruyen) {
        this.idTruyen = idTruyen;
    }

    public String getTenTruyen() {
        return tenTruyen;
    }

    public void setTenTruyen(String tenTruyen) {
        this.tenTruyen = tenTruyen;
    }

    public String getNgayDang() {
        return ngayDang;
    }

    public void setNgayDang(String ngayDang) {
        this.ngayDang = ngayDang;
    }

    public String getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    public String getTheLoai() {
        return theLoai;
    }

    public void setTheLoai(String theLoai) {
        this.theLoai = theLoai;
    }

    public String getGioiThieu() {
        return gioiThieu;
    }

    public void setGioiThieu(String gioiThieu) {
        this.gioiThieu = gioiThieu;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }


}
