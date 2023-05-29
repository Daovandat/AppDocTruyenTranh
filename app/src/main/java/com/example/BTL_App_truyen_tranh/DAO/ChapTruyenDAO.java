package com.example.BTL_App_truyen_tranh.DAO;

import static com.example.BTL_App_truyen_tranh.GUI.QuanLyTruyen.HomeQuanLy.sqLiteDAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.BTL_App_truyen_tranh.DTO.Chap;
import com.example.BTL_App_truyen_tranh.DTO.TruyenTranh;
import com.example.BTL_App_truyen_tranh.DTO.imgChap;

import java.util.ArrayList;
import java.util.List;


public class ChapTruyenDAO {
    public static Boolean them_chaptruyen(Chap chap, SQLiteDAO sqLiteDAO) {
        SQLiteDatabase MyDB = sqLiteDAO.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("idtt", chap.getIdtt());
        contentValues.put("tenChap", chap.getTenChap());
        long result = MyDB.insert("chap", null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public static Boolean them_imgchaptruyen(imgChap imgchap, SQLiteDAO sqLiteDAO) {
        SQLiteDatabase MyDB = sqLiteDAO.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("idtt", imgchap.getIdtt());
        contentValues.put("tenChap", imgchap.getTenChap());
        contentValues.put("img", imgchap.getImg());
        long result = MyDB.insert("imgchap", null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public static Boolean sua_img(String tenchap, SQLiteDAO sqLiteDAO) {
        SQLiteDatabase MyDB = sqLiteDAO.getWritableDatabase();
        long result = MyDB.delete("imgchap", "tenChap=?", new String[]{tenchap});
        if (result == -1)
            return false;
        else
            return true;
    }

    public static Boolean kiem_tra_chap(String tenchap,int idtt) {
        SQLiteDatabase MyDB = sqLiteDAO.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from chap where tenChap = ? and idtt=?", new String[]{tenchap,String.valueOf(idtt)});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public static boolean xoa_chap(int idChap) {
        SQLiteDatabase MyDB = sqLiteDAO.getWritableDatabase();
        long result = MyDB.delete("chap", "idChap=?", new String[]{String.valueOf(idChap)});
        if (result == -1)
            return false;
        else
            return true;
    }

    public static List<Chap> getall_chap(int idtt,SQLiteDAO sqLiteDAO) {
        List<Chap> chapList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = sqLiteDAO.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * From chap  where idtt = ?", new String[]{String.valueOf(idtt)});
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Chap chap = new Chap();
                chap.setIdChap(cursor.getInt(0));
                chap.setIdtt(cursor.getInt(1));
                chap.setTenChap(cursor.getString(2));
                chapList.add(chap);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return chapList;
    }
    public static List<imgChap> getall_imgchap(String tenChap,int idtt,SQLiteDAO sqLiteDAO) {
        List<imgChap> imgChapList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = sqLiteDAO.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * From imgchap where tenChap = ? and idtt = ? ",  new String[]{tenChap,String.valueOf(idtt)});
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                imgChap imgChap = new imgChap();
                imgChap.setIdimgChap(cursor.getInt(0));
                imgChap.setIdtt(cursor.getInt(1));
                imgChap.setTenChap(cursor.getString(2));
                imgChap.setImg(cursor.getBlob(3));
                imgChapList.add(imgChap);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return imgChapList;
    }

    public static Chap get_chap(int idChap, SQLiteDAO sqLiteDAO) {
        Chap chap = new Chap();
        SQLiteDatabase MyDB = sqLiteDAO.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from chap where idChap = ?", new String[]{String.valueOf(idChap)});
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            chap.setIdChap(cursor.getInt(0));
            chap.setIdtt(cursor.getInt(1));
            chap.setTenChap(cursor.getString(2));
            cursor.close();
            return chap;
        }
        return null;
    }
    public static imgChap get_imgchap(int idChap, SQLiteDAO sqLiteDAO) {
        imgChap imgChap = new imgChap();
        SQLiteDatabase MyDB = sqLiteDAO.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from imgchap where idChap = ?", new String[]{String.valueOf(idChap)});
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            imgChap.setIdimgChap(cursor.getInt(0));
            imgChap.setTenChap(cursor.getString(1));
            imgChap.setImg(cursor.getBlob(2));
            cursor.close();
            return imgChap;
        }
        return null;
    }
}
