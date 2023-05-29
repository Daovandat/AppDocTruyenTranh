package com.example.BTL_App_truyen_tranh.GUI.Home;

import static com.example.BTL_App_truyen_tranh.DAO.TheLoaiDAO.getall_tl;
import static com.example.BTL_App_truyen_tranh.DAO.TruyenTranhDao.getall_tt;
import static com.example.BTL_App_truyen_tranh.DAO.TruyenTranhDao.timkiem_tt;
import static com.example.BTL_App_truyen_tranh.GUI.QuanLyTruyen.HomeQuanLy.sqLiteDAO;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.BTL_App_truyen_tranh.DAO.SQLiteDAO;
import com.example.BTL_App_truyen_tranh.DTO.TheLoai;
import com.example.BTL_App_truyen_tranh.GUI.Login.DangNhapActivity;
import com.example.BTL_App_truyen_tranh.GUI.QuanLyTruyen.HomeQlItemTruyen;
import com.example.BTL_App_truyen_tranh.GUI.QuanLyTruyen.HomeQuanLy;
import com.example.BTL_App_truyen_tranh.GUI.QuanLyTruyen.QuanLyTruyen;
import com.example.BTL_App_truyen_tranh.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends AppCompatActivity {
    private RecyclerView list_item_truyen;
    private RecyclerView list_the_loai;
    public static SQLiteDAO sqLiteDAO1;
    private TextView text_name;
    private EditText timkiem;
    private Button button_dang_xuat2;
    public static String tk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        list_item_truyen = findViewById(R.id.list_item_truyen);
        list_the_loai = findViewById(R.id.list_the_loai);
        text_name = findViewById(R.id.text_name);
        button_dang_xuat2 = findViewById(R.id.button_dang_xuat2);
        sqLiteDAO1 = new SQLiteDAO(this);
        sqLiteDAO1.getdatatl();
        sqLiteDAO1.getdataTruyenTranh();
        sqLiteDAO1.getdataChap();
        sqLiteDAO1.getdataImgChap();
        sqLiteDAO1.getDanhGia();
        Intent intent = getIntent();
        tk = intent.getStringExtra("Key_hoten");
        text_name.setText(intent.getStringExtra("Key_hoten"));
        timkiem = findViewById(R.id.timkiem);
        //Khởi tạo GridLayoutManager Có số cột là 2
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        //Chuyền Layout Manager cho list_item_truyen
        list_item_truyen.setLayoutManager(gridLayoutManager);
        timkiem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (timkiem.getText().toString().trim().isEmpty()) {
                    GetListTruyen();
                } else {
                    //Khởi tạo HomeQlItemTruyen
                    HomeItemTruyen homeItemTruyen = new HomeItemTruyen(timkiem_tt(timkiem.getText().toString().trim(), sqLiteDAO1), HomePage.this);
                    //Chuyền adapter cho list_item_truyen
                    list_item_truyen.setAdapter(homeItemTruyen);
                }
            }
        });


        //Khởi tạo LinearLayoutManager cuộn ngang
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        //Chuyền linearLayoutManager cho list_item_truyen
        list_the_loai.setLayoutManager(linearLayoutManager);
        //Khởi tạo HomeItemTheloai
        HomeItemTheloai homeItemTheloai = new HomeItemTheloai(getall_tl(sqLiteDAO1), this);
        //Chuyền Adapter homeItemTheloai cho list_the_loai
        list_the_loai.setAdapter(homeItemTheloai);

        GetListTruyen();
        onClick();
    }

    public void GetListTruyen() {
        //Khởi tạo HomeItemTruyen
        HomeItemTruyen homeItemTruyen = new HomeItemTruyen(getall_tt(sqLiteDAO1), this);
        //Chuyền adapter cho list_item_truyen
        list_item_truyen.setAdapter(homeItemTruyen);
    }

    private void onClick() {
        button_dang_xuat2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomePage.this, DangNhapActivity.class));
                finish();
            }
        });


    }
}