package com.example.BTL_App_truyen_tranh.GUI.Login;

import static com.example.BTL_App_truyen_tranh.BUS.XuLySuKien.ANIMATION;
import static com.example.BTL_App_truyen_tranh.BUS.XuLySuKien.ANIMATIONDOWN;
import static com.example.BTL_App_truyen_tranh.BUS.XuLySuKien.ANIMATIONUP;
import static com.example.BTL_App_truyen_tranh.DAO.TaiKhoanDAO.kiem_tra_tk;
import static com.example.BTL_App_truyen_tranh.DAO.TaiKhoanDAO.them_taikhoan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.BTL_App_truyen_tranh.DAO.SQLiteDAO;
import com.example.BTL_App_truyen_tranh.DTO.TaiKhoan;
import com.example.BTL_App_truyen_tranh.R;

public class DangKyActivity extends AppCompatActivity {
    LottieAnimationView animationViewLogo;
    ImageView imageLogo;
    TextView textAppName,text_dang_nhap;
    EditText edit_ho_ten,edit_mat_khau,edit_taikhoan;
    Button button_dang_ky;
    SQLiteDAO sqLiteDAO;
    CharSequence charSequence;
    int index;
    long delay = 200;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);
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
        text_dang_nhap.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //Kiểm tra xem sự kiện bằng DOWN
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    //Bắt hiệu ứng cho text
                    text_dang_nhap.startAnimation(ANIMATIONUP);
                } else {
                    //Kiểm tra xem sự kiện bằng UP
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        //Bắt hiệu ứng cho text
                        text_dang_nhap.startAnimation(ANIMATIONDOWN);

                        startActivity(new Intent(DangKyActivity.this, DangNhapActivity.class));
                        finish();
                    }
                }
                return true;
            }
        });
        button_dang_ky.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //Kiểm tra xem sự kiện bằng DOWN
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    //Bắt hiệu ứng cho text
                    button_dang_ky.startAnimation(ANIMATIONUP);
                } else {
                    //Kiểm tra xem sự kiện bằng UP
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        //Bắt hiệu ứng cho text
                        button_dang_ky.startAnimation(ANIMATIONDOWN);
                        String tk = edit_taikhoan.getText().toString().trim();
                        String hoten = edit_ho_ten.getText().toString().trim();
                        String matkhau = edit_mat_khau.getText().toString().trim();
                        if (tk.isEmpty() && hoten.isEmpty() && matkhau.isEmpty()) {
                            Toast.makeText(DangKyActivity.this, "Vui lòng ko để trống thông tin", Toast.LENGTH_SHORT).show();
                        } else {
                            TaiKhoan taiKhoan=new TaiKhoan(0,tk,hoten,matkhau);
                            if (!kiem_tra_tk(tk,sqLiteDAO)) {
                                Log.e("tk",tk);
                                Log.e("mk",matkhau);
                                if (them_taikhoan(taiKhoan,sqLiteDAO)) {
                                    Toast.makeText(DangKyActivity.this, "Đã đăng ký thành công", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), DangNhapActivity.class));
                                    finish();
                                } else {
                                    Toast.makeText(DangKyActivity.this, "Đăng ký không thành công", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(DangKyActivity.this, "Tài khoản đã tồn tại", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
                return true;
            }
        });
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
        ANIMATION(this);
        animationViewLogo = findViewById(R.id.animationViewLogo);
        imageLogo = findViewById(R.id.imageLogo);
        textAppName = findViewById(R.id.textAppName);
        text_dang_nhap = findViewById(R.id.text_dang_nhap);
        button_dang_ky = findViewById(R.id.button_dang_ky);
        edit_ho_ten = findViewById(R.id.edit_ho_ten);
        edit_mat_khau = findViewById(R.id.edit_mat_khau);
        edit_taikhoan = findViewById(R.id.edit_taikhoan);
        sqLiteDAO=new SQLiteDAO(DangKyActivity.this);
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