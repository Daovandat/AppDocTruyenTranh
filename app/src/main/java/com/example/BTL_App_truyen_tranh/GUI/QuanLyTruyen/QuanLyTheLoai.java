package com.example.BTL_App_truyen_tranh.GUI.QuanLyTruyen;

import static com.example.BTL_App_truyen_tranh.DAO.TheLoaiDAO.getall_tl;
import static com.example.BTL_App_truyen_tranh.DAO.TheLoaiDAO.kiem_tra_tl;
import static com.example.BTL_App_truyen_tranh.DAO.TheLoaiDAO.them_tl;
import static com.example.BTL_App_truyen_tranh.GUI.QuanLyTruyen.HomeQuanLy.sqLiteDAO;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.BTL_App_truyen_tranh.DTO.TheLoai;
import com.example.BTL_App_truyen_tranh.GUI.Home.HomeItemTheloai;
import com.example.BTL_App_truyen_tranh.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class QuanLyTheLoai extends AppCompatActivity {
    private ImageView img_back;
    public static RecyclerView list_item_ql_tl;
    private FloatingActionButton floating_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_the_loai);
        floating_add = findViewById(R.id.floating_add);
        list_item_ql_tl = findViewById(R.id.list_item_ql_tl);
        img_back = findViewById(R.id.img_back);
        GetList(this);
        floating_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTheloai();
            }
        });
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        //Khởi tạo LinearLayoutManager cuộn dọc
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(QuanLyTheLoai.this, LinearLayoutManager.VERTICAL, false);
        //Chuyền linearLayoutManager cho list_item_ql_tl
        list_item_ql_tl.setLayoutManager(linearLayoutManager);
    }

    public static void GetList(Context context) {

        //Khởi tạo homeQlItemTheLoai
        HomeQlItemTheLoai homeQlItemTheLoai = new HomeQlItemTheLoai(getall_tl(sqLiteDAO), context);
        //Chuyền Adapter homeQlItemTheLoai cho list_item_ql_tl
        list_item_ql_tl.setAdapter(homeQlItemTheLoai);


    }

    private void addTheloai() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_tl);
        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        window.setAttributes(layoutParams);
        dialog.setCancelable(true);

        EditText edit_addtl = dialog.findViewById(R.id.edit_addtl);
        TextView text_view = dialog.findViewById(R.id.text_view);
        Button button_huy = dialog.findViewById(R.id.button_huy);
        Button button_them = dialog.findViewById(R.id.button_them);
        text_view.setText("Thêm thể loại");
        button_them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tentl = edit_addtl.getText().toString().trim();
                if (tentl.isEmpty()) {
                    Toast.makeText(dialog.getContext(), "Vui lòng nhập tên thể loại!", Toast.LENGTH_SHORT).show();
                } else {
                    if (kiem_tra_tl(tentl)) {
                        Toast.makeText(dialog.getContext(), "Tên thể loại đã tồn tại!", Toast.LENGTH_SHORT).show();
                    } else {
                        if (them_tl(tentl)) {
                            Toast.makeText(dialog.getContext(), "Thêm thành công!", Toast.LENGTH_SHORT).show();
                            GetList(QuanLyTheLoai.this);
                            dialog.dismiss();
                        }

                    }
                }
            }
        });
        button_huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


        dialog.show();
    }


}