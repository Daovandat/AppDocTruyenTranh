package com.example.BTL_App_truyen_tranh.DAO;

import static com.example.BTL_App_truyen_tranh.GUI.QuanLyTruyen.HomeQuanLy.sqLiteDAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.BTL_App_truyen_tranh.DTO.TheLoai;

import java.util.ArrayList;
import java.util.List;


public class TheLoaiDAO {

    public static Boolean them_tl(String tentl) {
        SQLiteDatabase MyDB = sqLiteDAO.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tentl", tentl);
        long result = MyDB.insert("theloai", null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }
    public static Boolean kiem_tra_tl(String tentl) {
        SQLiteDatabase MyDB = sqLiteDAO.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from theloai where tentl = ?", new String[]{tentl});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }
    public static boolean xoa_tl(int idtl) {
        SQLiteDatabase MyDB = sqLiteDAO.getWritableDatabase();
        long result = MyDB.delete("theloai","idtl=?" , new String[]{String.valueOf(idtl)});
        if (result == -1)
            return false;
        else
            return true;
    }
    public static boolean sua_tl(int idtl,String tentl) {
        SQLiteDatabase MyDB = sqLiteDAO.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tentl",tentl);
        long result = MyDB.update("theloai",contentValues,"idtl=?" , new String[]{String.valueOf(idtl)});
        if (result == -1)
            return false;
        else
            return true;
    }
    public static List<TheLoai> getall_tl(SQLiteDAO sqLiteDAO)  {
        List<TheLoai> theLoais = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = sqLiteDAO.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * From theloai", null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                TheLoai theLoai = new TheLoai();
                theLoai.setId(cursor.getInt(0));
                theLoai.setTenTheLoai(cursor.getString(1));
                theLoais.add(theLoai);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return theLoais;
    }
}
