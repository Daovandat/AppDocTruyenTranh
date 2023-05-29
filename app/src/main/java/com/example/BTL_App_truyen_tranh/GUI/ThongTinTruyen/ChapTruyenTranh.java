package com.example.BTL_App_truyen_tranh.GUI.ThongTinTruyen;

import static com.example.BTL_App_truyen_tranh.DAO.ChapTruyenDAO.getall_chap;
import static com.example.BTL_App_truyen_tranh.DAO.ChapTruyenDAO.getall_imgchap;
import static com.example.BTL_App_truyen_tranh.GUI.Home.HomePage.sqLiteDAO1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.BTL_App_truyen_tranh.DTO.Chap;
import com.example.BTL_App_truyen_tranh.DTO.imgChap;
import com.example.BTL_App_truyen_tranh.R;

import java.util.ArrayList;
import java.util.List;

public class ChapTruyenTranh extends AppCompatActivity {
    private TextView tenchap;
    private ImageView img_back;
    private RecyclerView list_item_img;
    public List<imgChap> list = new ArrayList<>();
    private Button button_truoc, button_sau;
    private int position=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chap_truyentranh);
        list_item_img = findViewById(R.id.list_item_img);
        tenchap = findViewById(R.id.tenchap);
        img_back = findViewById(R.id.img_back);
        button_truoc = findViewById(R.id.button_truoc);
        button_sau = findViewById(R.id.button_sau);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        Intent intent = getIntent();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ChapTruyenTranh.this, LinearLayoutManager.VERTICAL, false);
        list_item_img.setLayoutManager(linearLayoutManager);
        if (intent.getStringExtra("Key_tenChap") != null) {
            list = getall_imgchap(intent.getStringExtra("Key_tenChap"), intent.getIntExtra("Key_idTruyen", 0), sqLiteDAO1);
            tenchap.setText(intent.getStringExtra("Key_tenChap"));
            ListImgChap();

        }
        List<Chap> listchap = getall_chap(intent.getIntExtra("Key_idTruyen", 0), sqLiteDAO1);
        for (int i=0;i<listchap.size();i++){
            if(intent.getStringExtra("Key_tenChap").equals(listchap.get(i).getTenChap())){
                position=i;
                break;
            }
        }
        if(position==0){
            button_truoc.setVisibility(View.GONE);
        }
        if(position==listchap.size()-1){
            button_sau.setVisibility(View.GONE);
        }
        button_truoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(position>0){
                    position--;
                    if(position==0){
                        button_truoc.setVisibility(View.GONE);
                    }else {
                        button_sau.setVisibility(View.VISIBLE);
                    }
                    list = getall_imgchap(listchap.get(position).getTenChap(), intent.getIntExtra("Key_idTruyen", 0), sqLiteDAO1);
                    tenchap.setText(listchap.get(position).getTenChap());
                    ListImgChap();
                }
            }
        });

        button_sau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(position<listchap.size()-1){
                    position++;
                    if(position==listchap.size()-1){
                        button_sau.setVisibility(View.GONE);
                    }else {
                        button_truoc.setVisibility(View.VISIBLE);
                    }
                    list = getall_imgchap(listchap.get(position).getTenChap(), intent.getIntExtra("Key_idTruyen", 0), sqLiteDAO1);
                    tenchap.setText(listchap.get(position).getTenChap());
                    ListImgChap();
                }
            }
        });

    }

    private void ListImgChap() {
        ChapItemImg chapItemImg = new ChapItemImg(list);
        list_item_img.setAdapter(chapItemImg);
    }
}