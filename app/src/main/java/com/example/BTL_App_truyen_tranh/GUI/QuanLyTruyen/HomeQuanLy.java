package com.example.BTL_App_truyen_tranh.GUI.QuanLyTruyen;


import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.example.BTL_App_truyen_tranh.DAO.SQLiteDAO;
import com.example.BTL_App_truyen_tranh.GUI.Login.DangNhapActivity;
import com.example.BTL_App_truyen_tranh.R;

public class HomeQuanLy extends AppCompatActivity {
    private CardView card_view_ql_truyen;
    private CardView card_view_ql_tl;
    private Button button_dang_xuat;
    public static SQLiteDAO sqLiteDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_quan_ly);
        card_view_ql_tl = findViewById(R.id.card_view_ql_tl);
        card_view_ql_truyen = findViewById(R.id.card_view_ql_truyen);
        button_dang_xuat = findViewById(R.id.button_dang_xuat);
        sqLiteDAO = new SQLiteDAO(this);
        sqLiteDAO.getdatatl();
        sqLiteDAO.getdataTruyenTranh();
        sqLiteDAO.getdataChap();
        sqLiteDAO.getdataImgChap();
        onClick();
    }

    private void onClick() {
        button_dang_xuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeQuanLy.this, DangNhapActivity.class));
                finish();
            }
        });
        card_view_ql_truyen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeQuanLy.this, QuanLyTruyen.class));
            }
        });
        card_view_ql_tl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeQuanLy.this, QuanLyTheLoai.class));
            }
        });

    }
}