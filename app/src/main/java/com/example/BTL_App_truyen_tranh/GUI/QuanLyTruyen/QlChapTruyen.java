package com.example.BTL_App_truyen_tranh.GUI.QuanLyTruyen;

import static com.example.BTL_App_truyen_tranh.DAO.ChapTruyenDAO.getall_chap;
import static com.example.BTL_App_truyen_tranh.GUI.QuanLyTruyen.HomeQuanLy.sqLiteDAO;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.BTL_App_truyen_tranh.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class QlChapTruyen extends AppCompatActivity {
    FloatingActionButton floating_add;
    static RecyclerView list_item_ql_ct;
    ImageView image_back;
    Intent intent2 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ql_chap_truyen);
        floating_add = findViewById(R.id.floating_add);
        list_item_ql_ct = findViewById(R.id.list_item_ql_ct);
        image_back = findViewById(R.id.img_back);
        intent2=getIntent();
        image_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        floating_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(QlChapTruyen.this, addChap.class);
                intent.putExtra("Key_idTruyen", intent2.getIntExtra("Key_idTruyen", 0));
                startActivity(intent);
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(QlChapTruyen.this, LinearLayoutManager.VERTICAL, false);
        list_item_ql_ct.setLayoutManager(linearLayoutManager);
        if (intent2.getIntExtra("Key_idTruyen", 0)!=0){
            GetListChap( QlChapTruyen.this,intent2.getIntExtra("Key_idTruyen", 0));
        }
    }

    public static void GetListChap(Context context,int idtt) {
        HomeQLItemChap homeQLItemChap = new HomeQLItemChap(getall_chap(idtt,sqLiteDAO), context);
        list_item_ql_ct.setAdapter(homeQLItemChap);
    }

}