package com.example.BTL_App_truyen_tranh.DAO;

import static com.example.BTL_App_truyen_tranh.GUI.Home.HomePage.sqLiteDAO1;
import static com.example.BTL_App_truyen_tranh.GUI.QuanLyTruyen.HomeQuanLy.sqLiteDAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.BTL_App_truyen_tranh.DTO.DanhGia;
import com.example.BTL_App_truyen_tranh.DTO.TaiKhoan;
import com.example.BTL_App_truyen_tranh.DTO.TheLoai;

import java.util.ArrayList;
import java.util.List;

public class DanhGiaDAO {

    public static Boolean them_dg(DanhGia danhGia) {
        SQLiteDatabase MyDB = sqLiteDAO1.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tk", danhGia.getTk());
        contentValues.put("idtt", danhGia.getIdtt());
        contentValues.put("danhgia", danhGia.getDanhgia());
        long result = MyDB.insert("danhgia", null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }


    public static boolean sua_dg(DanhGia danhGia) {
        SQLiteDatabase MyDB = sqLiteDAO1.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tk", danhGia.getTk());
        contentValues.put("idtt", danhGia.getIdtt());
        contentValues.put("danhgia", danhGia.getDanhgia());
        long result = MyDB.update("danhgia", contentValues, "tk = ? and idtt = ?", new String[]{String.valueOf(danhGia.getTk()), String.valueOf(danhGia.getIdtt())});
        if (result == -1)
            return false;
        else
            return true;
    }

    public static List<DanhGia> getall_dg(int idtt) {
        List<DanhGia> danhGias = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = sqLiteDAO1.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * From danhgia where idtt = ?", new String[]{String.valueOf(idtt)});
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                DanhGia danhGia = new DanhGia();
                danhGia.setIddg(cursor.getInt(0));
                danhGia.setTk(cursor.getString(1));
                danhGia.setIdtt(cursor.getInt(2));
                danhGia.setDanhgia(cursor.getInt(3));
                danhGias.add(danhGia);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return danhGias;
    }

    public static DanhGia kiem_tra_dg(String tk, int idtt) {
        SQLiteDatabase MyDB = sqLiteDAO1.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from danhgia where tk = ? and idtt = ?", new String[]{tk, String.valueOf(idtt)});
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            DanhGia danhGia = new DanhGia();
            danhGia.setIddg(cursor.getInt(0));
            danhGia.setTk(cursor.getString(1));
            danhGia.setIdtt(cursor.getInt(2));
            danhGia.setDanhgia(cursor.getInt(3));
            cursor.close();
            return danhGia;
        } else
            return null;
    }

}
