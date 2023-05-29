package com.example.BTL_App_truyen_tranh.GUI.ThongTinTruyen;

import static com.example.BTL_App_truyen_tranh.DAO.ChapTruyenDAO.getall_chap;
import static com.example.BTL_App_truyen_tranh.DAO.DanhGiaDAO.getall_dg;
import static com.example.BTL_App_truyen_tranh.DAO.DanhGiaDAO.kiem_tra_dg;
import static com.example.BTL_App_truyen_tranh.DAO.DanhGiaDAO.sua_dg;
import static com.example.BTL_App_truyen_tranh.DAO.DanhGiaDAO.them_dg;
import static com.example.BTL_App_truyen_tranh.DAO.TruyenTranhDao.get_truyentranh;
import static com.example.BTL_App_truyen_tranh.GUI.Home.HomePage.sqLiteDAO1;
import static com.example.BTL_App_truyen_tranh.GUI.Home.HomePage.tk;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.BTL_App_truyen_tranh.BUS.Utils;
import com.example.BTL_App_truyen_tranh.DTO.Chap;
import com.example.BTL_App_truyen_tranh.DTO.DanhGia;
import com.example.BTL_App_truyen_tranh.DTO.TruyenTranh;
import com.example.BTL_App_truyen_tranh.R;

import java.util.List;

public class ThongTinTuyen extends AppCompatActivity {
    private TextView text_ten_truyen, text_time, text_tinhtrang, text_slchap, textTenTheLoai, text_gioithieu,txt_danhgia;
    private ImageView image_truyen,img_back;
    private TruyenTranh truyenTranh;
    private ListView list_item;
    private ImageView star_01, star_02, star_03, star_04, star_05, dlstar_01, dlstar_02, dlstar_03, dlstar_04, dlstar_05;
    private Button button_dg;
    private int idtt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_tuyen);
        Intent intent = getIntent();
        anh_xa();

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        if (intent.getIntExtra("Key_idTruyen", 0) != 0) {
            idtt = intent.getIntExtra("Key_idTruyen", 0);
            truyenTranh = get_truyentranh(intent.getIntExtra("Key_idTruyen", 0), sqLiteDAO1);
        }
        List<Chap> list = getall_chap(truyenTranh.getIdTruyen(), sqLiteDAO1);
        text_ten_truyen.setText(truyenTranh.getTenTruyen());
        text_time.setText(truyenTranh.getNgayDang());
        text_tinhtrang.setText(truyenTranh.getTinhTrang());
        text_slchap.setText(list.size() + " Chap");
        textTenTheLoai.setText(truyenTranh.getTheLoai());
        text_gioithieu.setText(truyenTranh.getGioiThieu());
        image_truyen.setImageBitmap(Utils.getImage(truyenTranh.getImg()));

        ArrayAdapter<Chap> arrayAdapter
                = new ArrayAdapter<Chap>(this, android.R.layout.simple_list_item_1, list);

        list_item.setAdapter(arrayAdapter);
        list_item.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Chap chap=list.get(i);
                Intent intent = new Intent(ThongTinTuyen.this, ChapTruyenTranh.class);
                intent.putExtra("Key_tenChap", chap.getTenChap());
                intent.putExtra("Key_idTruyen", chap.getIdtt());
                startActivity(intent);
            }
        });
        button_dg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                danh_gia();
            }
        });
        if(getall_dg(idtt).size()!=0){
            getDg();
        }else {
            txt_danhgia.setText("Chưa có đánh giá");
        }
    }

    private void getDg() {
        int tong = 0;
        for (int i = 0; i < getall_dg(idtt).size(); i++) {
            tong = tong + getall_dg(idtt).get(i).getDanhgia();
        }
        txt_danhgia.setText(tong / getall_dg(idtt).size() + "");
        star(tong / getall_dg(idtt).size());
    }

    int dg = 0;

    private void danh_gia() {

        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dia_log_dg);
        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        window.setAttributes(layoutParams);
        dialog.setCancelable(true);
        dlstar_01 = dialog.findViewById(R.id.star_01);
        dlstar_02 = dialog.findViewById(R.id.star_02);
        dlstar_03 = dialog.findViewById(R.id.star_03);
        dlstar_04 = dialog.findViewById(R.id.star_04);
        dlstar_05 = dialog.findViewById(R.id.star_05);
        Button button_dg = dialog.findViewById(R.id.button_dg);
        Button button_huy = dialog.findViewById(R.id.button_huy);
        if (kiem_tra_dg(tk, idtt) != null) {
            DanhGia danhGia = kiem_tra_dg(tk, idtt);
            stardl(danhGia.getDanhgia());
        }
        dg_star(1, dlstar_01);
        dg_star(2, dlstar_02);
        dg_star(3, dlstar_03);
        dg_star(4, dlstar_04);
        dg_star(5, dlstar_05);

        button_dg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DanhGia danhGia = new DanhGia(0, idtt, dg, tk);
                if (dg == 0) {
                    Toast.makeText(ThongTinTuyen.this, "Vui lòng chọn dánh giá !", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (kiem_tra_dg(tk, idtt) != null) {
                    if (sua_dg(danhGia)) {
                        getDg();
                        Toast.makeText(ThongTinTuyen.this, "Cập nhập thành công", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    } else
                        Toast.makeText(ThongTinTuyen.this, "Cập nhập thất bại", Toast.LENGTH_SHORT).show();
                } else {
                    if (them_dg(danhGia)) {
                        getDg();
                        Toast.makeText(ThongTinTuyen.this, "Đánh giá thành công", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();

                    } else {
                        Toast.makeText(ThongTinTuyen.this, "Đánh giá thất bại", Toast.LENGTH_SHORT).show();
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

    public void dg_star(int dgs, ImageView image) {
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                stardl(dgs);
            }
        });

    }

    private void stardl(int dgs) {
        switch (dgs) {
            case 1:
                dg = 1;
                dlstar_01.setBackground(getDrawable(R.drawable.star2));
                dlstar_02.setBackground(getDrawable(R.drawable.star));
                dlstar_03.setBackground(getDrawable(R.drawable.star));
                dlstar_04.setBackground(getDrawable(R.drawable.star));
                dlstar_05.setBackground(getDrawable(R.drawable.star));
                break;
            case 2:
                dg = 2;
                dlstar_01.setBackground(getDrawable(R.drawable.star2));
                dlstar_02.setBackground(getDrawable(R.drawable.star2));
                dlstar_03.setBackground(getDrawable(R.drawable.star));
                dlstar_04.setBackground(getDrawable(R.drawable.star));
                dlstar_05.setBackground(getDrawable(R.drawable.star));
                break;
            case 3:
                dg = 3;
                dlstar_01.setBackground(getDrawable(R.drawable.star2));
                dlstar_02.setBackground(getDrawable(R.drawable.star2));
                dlstar_03.setBackground(getDrawable(R.drawable.star2));
                dlstar_04.setBackground(getDrawable(R.drawable.star));
                dlstar_05.setBackground(getDrawable(R.drawable.star));
                break;
            case 4:
                dg = 4;
                dlstar_01.setBackground(getDrawable(R.drawable.star2));
                dlstar_02.setBackground(getDrawable(R.drawable.star2));
                dlstar_03.setBackground(getDrawable(R.drawable.star2));
                dlstar_04.setBackground(getDrawable(R.drawable.star2));
                dlstar_05.setBackground(getDrawable(R.drawable.star));
                break;
            case 5:
                dg = 5;
                dlstar_01.setBackground(getDrawable(R.drawable.star2));
                dlstar_02.setBackground(getDrawable(R.drawable.star2));
                dlstar_03.setBackground(getDrawable(R.drawable.star2));
                dlstar_04.setBackground(getDrawable(R.drawable.star2));
                dlstar_05.setBackground(getDrawable(R.drawable.star2));
                break;
            default:
                break;

        }
    }

    private void star(int dgs) {
        switch (dgs) {
            case 1:
                star_01.setBackground(getDrawable(R.drawable.star2));
                star_02.setBackground(getDrawable(R.drawable.star));
                star_03.setBackground(getDrawable(R.drawable.star));
                star_04.setBackground(getDrawable(R.drawable.star));
                star_05.setBackground(getDrawable(R.drawable.star));
                break;
            case 2:
                star_01.setBackground(getDrawable(R.drawable.star2));
                star_02.setBackground(getDrawable(R.drawable.star2));
                star_03.setBackground(getDrawable(R.drawable.star));
                star_04.setBackground(getDrawable(R.drawable.star));
                star_05.setBackground(getDrawable(R.drawable.star));
                break;
            case 3:
                star_01.setBackground(getDrawable(R.drawable.star2));
                star_02.setBackground(getDrawable(R.drawable.star2));
                star_03.setBackground(getDrawable(R.drawable.star2));
                star_04.setBackground(getDrawable(R.drawable.star));
                star_05.setBackground(getDrawable(R.drawable.star));
                break;
            case 4:
                star_01.setBackground(getDrawable(R.drawable.star2));
                star_02.setBackground(getDrawable(R.drawable.star2));
                star_03.setBackground(getDrawable(R.drawable.star2));
                star_04.setBackground(getDrawable(R.drawable.star2));
                star_05.setBackground(getDrawable(R.drawable.star));
                break;
            case 5:
                star_01.setBackground(getDrawable(R.drawable.star2));
                star_02.setBackground(getDrawable(R.drawable.star2));
                star_03.setBackground(getDrawable(R.drawable.star2));
                star_04.setBackground(getDrawable(R.drawable.star2));
                star_05.setBackground(getDrawable(R.drawable.star2));
                break;
            default:
                break;

        }
    }
    private void anh_xa() {
        text_ten_truyen = findViewById(R.id.text_ten_truyen);
        text_time = findViewById(R.id.text_time);
        text_tinhtrang = findViewById(R.id.text_tinhtrang);
        text_slchap = findViewById(R.id.text_slchap);
        textTenTheLoai = findViewById(R.id.textTenTheLoai);
        text_gioithieu = findViewById(R.id.text_gioithieu);
        txt_danhgia = findViewById(R.id.txt_danhgia);
        image_truyen = findViewById(R.id.image_truyen);
        list_item = findViewById(R.id.list_item);
        img_back = findViewById(R.id.img_back);
        star_01 = findViewById(R.id.star_01);
        star_02 = findViewById(R.id.star_02);
        star_03 = findViewById(R.id.star_03);
        star_04 = findViewById(R.id.star_04);
        star_05 = findViewById(R.id.star_05);
        button_dg = findViewById(R.id.button_dg);
    }
}