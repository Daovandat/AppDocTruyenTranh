package com.example.BTL_App_truyen_tranh.GUI.Login;

import static com.example.BTL_App_truyen_tranh.BUS.XuLySuKien.ANIMATION;
import static com.example.BTL_App_truyen_tranh.BUS.XuLySuKien.ANIMATIONDOWN;
import static com.example.BTL_App_truyen_tranh.BUS.XuLySuKien.ANIMATIONUP;
import static com.example.BTL_App_truyen_tranh.DAO.TaiKhoanDAO.kiem_tra_dn;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.example.BTL_App_truyen_tranh.DAO.SQLiteDAO;
import com.example.BTL_App_truyen_tranh.DTO.TaiKhoan;
import com.example.BTL_App_truyen_tranh.GUI.Home.HomePage;
import com.example.BTL_App_truyen_tranh.GUI.QuanLyTruyen.HomeQuanLy;
import com.example.BTL_App_truyen_tranh.MainActivity;
import com.example.BTL_App_truyen_tranh.R;

public class DangNhapActivity extends AppCompatActivity {
    LottieAnimationView animationViewLogo;
    ImageView imageLogo;
    TextView textAppName, text_dang_ky;
    Button button_dang_nhap;
    EditText edit_taikhoan, edit_mat_khau;
    SQLiteDAO sqLiteDAO;
    CharSequence charSequence;
    int index;
    long delay = 200;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        anhXa();
        //Khởi tạo animation
        Animation animationImageLogo = AnimationUtils.loadAnimation(this, R.anim.top_wave);
        //Bắt đầu animation
        imageLogo.setAnimation(animationImageLogo);
        //Khởi tạo animation
        Animation animationZoomIn = AnimationUtils.loadAnimation(this, R.anim.zoom_in);
        //Bắt đầu animation
        animationViewLogo.setAnimation(animationZoomIn);

        animatText("NetTruyen");
        onClick();
        //Tạo hoạn ảnh cho nút ấn

    }

    private void onClick() {
        text_dang_ky.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //Kiểm tra xem sự kiện bằng DOWN
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    //Bắt hiệu ứng cho text
                    text_dang_ky.startAnimation(ANIMATIONUP);
                } else {
                    //Kiểm tra xem sự kiện bằng UP
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        //Bắt hiệu ứng cho text
                        text_dang_ky.startAnimation(ANIMATIONDOWN);

                        startActivity(new Intent(DangNhapActivity.this, DangKyActivity.class));
                    }
                }
                return true;
            }
        });
        button_dang_nhap.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //Kiểm tra xem sự kiện bằng DOWN
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    //Bắt hiệu ứng cho text
                    button_dang_nhap.startAnimation(ANIMATIONUP);
                } else {
                    //Kiểm tra xem sự kiện bằng UP
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        //Bắt hiệu ứng cho text
                        button_dang_nhap.startAnimation(ANIMATIONDOWN);
                        signIn();
                    }
                }
                return true;
            }
        });
    }

    private void signIn() {
        String tk = edit_taikhoan.getText().toString().trim();
        String mk = edit_mat_khau.getText().toString().trim();
        if (tk.isEmpty() || mk.isEmpty()) {
            Toast.makeText(DangNhapActivity.this, "Vui lòng điền đủ thông tin", Toast.LENGTH_SHORT).show();
        } else {
            TaiKhoan taiKhoan = new TaiKhoan(0, tk, "", mk);
            if (tk.equals("admin") && mk.equals("admin")) {
                Intent intent = new Intent(DangNhapActivity.this, HomeQuanLy.class);
                startActivity(intent);
                finish();
            } else {
                if (!kiem_tra_dn(taiKhoan, sqLiteDAO).isEmpty()) {
                    Toast.makeText(DangNhapActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(DangNhapActivity.this, HomePage.class);
                    intent.putExtra("Key_hoten", kiem_tra_dn(taiKhoan, sqLiteDAO));
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(DangNhapActivity.this, "Tài khoản chưa được đăng ký", Toast.LENGTH_SHORT).show();
                }
            }

        }

    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            //Khi được chạy
            //Set Text
            textAppName.setText(charSequence.subSequence(0, index++));
            //Kiểm tra tình trạng
            if (index <= charSequence.length()) {
                handler.postDelayed(runnable, delay);
            }
        }
    };

    private void anhXa() {
        //Khai báo hoạt ảnh
        ANIMATION(this);
        //Khai báo id
        animationViewLogo = findViewById(R.id.animationViewLogo);
        imageLogo = findViewById(R.id.imageLogo);
        textAppName = findViewById(R.id.textAppName);
        edit_taikhoan = findViewById(R.id.edit_taikhoan);
        edit_mat_khau = findViewById(R.id.edit_mat_khau);
        button_dang_nhap = findViewById(R.id.button_dang_nhap);
        text_dang_ky = findViewById(R.id.text_dang_ky);
        sqLiteDAO = new SQLiteDAO(DangNhapActivity.this);
        sqLiteDAO.getdatatk();
    }

    //Tạo phương pháp văn bản động
    public void animatText(CharSequence cs) {
        //Set text
        charSequence = cs;
        //Đặt lại index về 0;
        index = 0;
        //Xóa văn bản
        textAppName.setText("");
        //Xóa cuộc gọi lại
        handler.removeCallbacks(runnable);
        //Chayk handler
        handler.postDelayed(runnable, delay);
    }
}