package com.example.BTL_App_truyen_tranh.GUI.TheLoaiTruyen;

import static com.example.BTL_App_truyen_tranh.DAO.TruyenTranhDao.getall_tt;
import static com.example.BTL_App_truyen_tranh.DAO.TruyenTranhDao.gettruyentranhtl;
import static com.example.BTL_App_truyen_tranh.GUI.Home.HomePage.sqLiteDAO1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.BTL_App_truyen_tranh.GUI.Home.HomeItemTruyen;
import com.example.BTL_App_truyen_tranh.R;

public class TheLoaiTruyen extends AppCompatActivity {
    private ImageView img_back;
    private TextView textTenTheLoai;
    private RecyclerView list_item_truyen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_loai);
        img_back = findViewById(R.id.img_back);
        textTenTheLoai = findViewById(R.id.textTenTheLoai);
        list_item_truyen = findViewById(R.id.list_item_truyen);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        Intent intent = getIntent();
        if (intent.getStringExtra("Key_tentheloai") != null) {
            textTenTheLoai.setText(intent.getStringExtra("Key_tentheloai"));
            //Khởi tạo GridLayoutManager Có số cột là 2
            GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
            //Chuyền Layout Manager cho list_item_truyen
            list_item_truyen.setLayoutManager(gridLayoutManager);
            //Khởi tạo HomeItemTruyen
            HomeItemTruyen homeItemTruyen = new HomeItemTruyen(gettruyentranhtl(intent.getStringExtra("Key_tentheloai"), sqLiteDAO1), this);
            //Chuyền adapter cho list_item_truyen
            list_item_truyen.setAdapter(homeItemTruyen);
        }

    }
}