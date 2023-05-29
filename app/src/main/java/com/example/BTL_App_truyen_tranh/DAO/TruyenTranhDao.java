package com.example.BTL_App_truyen_tranh.DAO;

import static com.example.BTL_App_truyen_tranh.GUI.QuanLyTruyen.HomeQuanLy.sqLiteDAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.BTL_App_truyen_tranh.DTO.TruyenTranh;

import java.util.ArrayList;
import java.util.List;

public class TruyenTranhDao {
    public static Boolean them_truyentranh(TruyenTranh truyenTranh,SQLiteDAO sqLiteDAO) {
        SQLiteDatabase MyDB = sqLiteDAO.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tenTruyen", truyenTranh.getTenTruyen());
        contentValues.put("ngayDang", truyenTranh.getNgayDang());
        contentValues.put("tinhTrang", truyenTranh.getTinhTrang());
        contentValues.put("theLoai", truyenTranh.getTheLoai());
        contentValues.put("gioiThieu", truyenTranh.getGioiThieu());
        contentValues.put("img", truyenTranh.getImg());
        long result = MyDB.insert("truyentranh", null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }
    public static Boolean sua_truyentranh(TruyenTranh truyenTranh,SQLiteDAO sqLiteDAO) {
        SQLiteDatabase MyDB = sqLiteDAO.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tenTruyen", truyenTranh.getTenTruyen());
        contentValues.put("ngayDang", truyenTranh.getNgayDang());
        contentValues.put("tinhTrang", truyenTranh.getTinhTrang());
        contentValues.put("theLoai", truyenTranh.getTheLoai());
        contentValues.put("gioiThieu", truyenTranh.getGioiThieu());
        contentValues.put("img", truyenTranh.getImg());
        long result = MyDB.update("truyentranh",contentValues,"idtt=?" , new String[]{String.valueOf(truyenTranh.getIdTruyen())});
        if (result == -1)
            return false;
        else
            return true;
    }
    public static boolean xoa_truyentranh(int idtt) {
        SQLiteDatabase MyDB = sqLiteDAO.getWritableDatabase();
        long result = MyDB.delete("truyentranh","idtt=?" , new String[]{String.valueOf(idtt)});
        if (result == -1)
            return false;
        else
            return true;
    }
    public static List<TruyenTranh> getall_tt(SQLiteDAO sqLiteDAO) {
        List<TruyenTranh> sanphams = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = sqLiteDAO.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * From truyentranh", null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                TruyenTranh truyenTranh = new TruyenTranh();
                truyenTranh.setIdTruyen(cursor.getInt(0));
                truyenTranh.setTenTruyen(cursor.getString(1));
                truyenTranh.setNgayDang(cursor.getString(2));
                truyenTranh.setTinhTrang(cursor.getString(3));
                truyenTranh.setTheLoai(cursor.getString(4));
                truyenTranh.setGioiThieu(cursor.getString(5));
                truyenTranh.setImg(cursor.getBlob(6));
                sanphams.add(truyenTranh);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return sanphams;
    }
    public static List<TruyenTranh> timkiem_tt(String tenTruyen,SQLiteDAO sqLiteDAO) {
        List<TruyenTranh> sanphams = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = sqLiteDAO.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * From truyentranh where tenTruyen LIKE ?", new String[]{"%" + tenTruyen + "%"});
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                TruyenTranh truyenTranh = new TruyenTranh();
                truyenTranh.setIdTruyen(cursor.getInt(0));
                truyenTranh.setTenTruyen(cursor.getString(1));
                truyenTranh.setNgayDang(cursor.getString(2));
                truyenTranh.setTinhTrang(cursor.getString(3));
                truyenTranh.setTheLoai(cursor.getString(4));
                truyenTranh.setGioiThieu(cursor.getString(5));
                truyenTranh.setImg(cursor.getBlob(6));
                sanphams.add(truyenTranh);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return sanphams;
    }
    public static List<TruyenTranh> gettruyentranhtl(String tentl,SQLiteDAO sqLiteDAO) {
        List<TruyenTranh> sanphams = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = sqLiteDAO.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * From truyentranh where theLoai= ?", new String[]{tentl });
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                TruyenTranh truyenTranh = new TruyenTranh();
                truyenTranh.setIdTruyen(cursor.getInt(0));
                truyenTranh.setTenTruyen(cursor.getString(1));
                truyenTranh.setNgayDang(cursor.getString(2));
                truyenTranh.setTinhTrang(cursor.getString(3));
                truyenTranh.setTheLoai(cursor.getString(4));
                truyenTranh.setGioiThieu(cursor.getString(5));
                truyenTranh.setImg(cursor.getBlob(6));
                sanphams.add(truyenTranh);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return sanphams;
    }
    public static TruyenTranh get_truyentranh(int idtt,SQLiteDAO sqLiteDAO) {
        TruyenTranh truyenTranh = new TruyenTranh();
        SQLiteDatabase MyDB = sqLiteDAO.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from truyentranh where idtt = ?", new String[]{String.valueOf(idtt)});
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            truyenTranh.setIdTruyen(cursor.getInt(0));
            truyenTranh.setTenTruyen(cursor.getString(1));
            truyenTranh.setNgayDang(cursor.getString(2));
            truyenTranh.setTinhTrang(cursor.getString(3));
            truyenTranh.setTheLoai(cursor.getString(4));
            truyenTranh.setGioiThieu(cursor.getString(5));
            truyenTranh.setImg(cursor.getBlob(6));
            cursor.close();
            return truyenTranh;
        }
        return null;
    }
}
