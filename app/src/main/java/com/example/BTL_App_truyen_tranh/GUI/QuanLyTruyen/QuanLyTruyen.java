package com.example.BTL_App_truyen_tranh.GUI.QuanLyTruyen;

import static com.example.BTL_App_truyen_tranh.DAO.TheLoaiDAO.getall_tl;
import static com.example.BTL_App_truyen_tranh.DAO.TruyenTranhDao.getall_tt;
import static com.example.BTL_App_truyen_tranh.DAO.TruyenTranhDao.them_truyentranh;
import static com.example.BTL_App_truyen_tranh.DAO.TruyenTranhDao.timkiem_tt;
import static com.example.BTL_App_truyen_tranh.GUI.QuanLyTruyen.HomeQuanLy.sqLiteDAO;
import static com.example.BTL_App_truyen_tranh.GUI.QuanLyTruyen.QuanLyTheLoai.list_item_ql_tl;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.BTL_App_truyen_tranh.BUS.Utils;
import com.example.BTL_App_truyen_tranh.DTO.TheLoai;
import com.example.BTL_App_truyen_tranh.DTO.TruyenTranh;
import com.example.BTL_App_truyen_tranh.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class QuanLyTruyen extends AppCompatActivity {
    private FloatingActionButton floating_add;
    private static RecyclerView list_item_truyen;
    private ImageView image_back;
    public static final int chonphoto = 321;
    public static Uri selectImageUri;
    public  static  ImageView imageView;
    private EditText timkiem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_truyen);
        floating_add = findViewById(R.id.floating_add);
        list_item_truyen = findViewById(R.id.list_item_truyen);
        image_back = findViewById(R.id.image_back);
        timkiem = findViewById(R.id.timkiem);

        GetListTruyen(this);
        floating_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTheTruyen();
            }
        });
        image_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        //Khởi tạo LinearLayoutManager cuộn dọc
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(QuanLyTruyen.this, LinearLayoutManager.VERTICAL, false);
        //Chuyền linearLayoutManager cho list_item_truyen
        list_item_truyen.setLayoutManager(linearLayoutManager);

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
                    GetListTruyen(QuanLyTruyen.this);
                } else {
                    //Khởi tạo HomeQlItemTruyen
                    HomeQlItemTruyen homeQlItemTruyen = new HomeQlItemTruyen(timkiem_tt(timkiem.getText().toString().trim(),sqLiteDAO), QuanLyTruyen.this);
                    //Chuyền Adapter homeQlItemTruyen cho list_item_truyen
                    list_item_truyen.setAdapter(homeQlItemTruyen);
                }
            }
        });
    }

    private void addTheTruyen() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_truyentranh);
        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        window.setAttributes(layoutParams);
        dialog.setCancelable(true);
        TextView ten = dialog.findViewById(R.id.ten);
        imageView = dialog.findViewById(R.id.imageViewAnh);
        EditText editTextTenTruyen = dialog.findViewById(R.id.editTextTenTruyen);
        RadioButton radioButton = dialog.findViewById(R.id.radioButton);
        RadioButton radioButton2 = dialog.findViewById(R.id.radioButton2);
        Spinner spinner = dialog.findViewById(R.id.spinner);
        EditText editTextGioiThieu = dialog.findViewById(R.id.editTextGioiThieu);

        Button button_huy = dialog.findViewById(R.id.button_huy);
        Button button_them = dialog.findViewById(R.id.button_them);

        ten.setText("Thêm truyện tranh");
        final ArrayAdapter adapter = new ArrayAdapter(QuanLyTruyen.this, R.layout.dropdown_item, getall_tl(sqLiteDAO));
        spinner.setAdapter(adapter);
        spinner.setSelection(0);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (hasStoragePermission(QuanLyTruyen.this)) {
                    openImageChooser();
                } else {
                    ActivityCompat.requestPermissions(QuanLyTruyen.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, chonphoto);
                }
            }
        });
        button_huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        button_them.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                try {
                    String tenTruyenTranh = editTextTenTruyen.getText().toString().trim();
                    String gioiThieu = editTextGioiThieu.getText().toString().trim();
                    String trangThai = "";
                    if (radioButton.isChecked()) {
                        trangThai = radioButton.getText().toString().trim();
                    }
                    if (radioButton2.isChecked()) {
                        trangThai = radioButton2.getText().toString().trim();
                    }
                    if (tenTruyenTranh.isEmpty() && gioiThieu.isEmpty() && trangThai.isEmpty()) {
                        Toast.makeText(QuanLyTruyen.this, "Vui lòng điền đủ thông tin!", Toast.LENGTH_SHORT).show();
                    } else {
                        if (selectImageUri != null) {
                            InputStream inputStream = getContentResolver().openInputStream(selectImageUri);
                            TruyenTranh truyenTranh = new TruyenTranh(0, tenTruyenTranh, java.time.LocalDateTime.now().toString(), trangThai, spinner.getSelectedItem().toString(), gioiThieu, Utils.getBytes(inputStream));
                            if (them_truyentranh(truyenTranh, sqLiteDAO)) {
                                Toast.makeText(QuanLyTruyen.this, "Thêm thành công!", Toast.LENGTH_SHORT).show();
                                GetListTruyen(QuanLyTruyen.this);
                                dialog.dismiss();
                            }
                        } else {
                            Toast.makeText(QuanLyTruyen.this, "Vui thêm ảnh!", Toast.LENGTH_SHORT).show();

                        }

                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        dialog.show();
    }

    private void openImageChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, chonphoto);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            if (requestCode == chonphoto) {
                selectImageUri = data.getData();
                if (selectImageUri != null) {
                    Uri uri = data.getData();
                    imageView.setImageURI(uri);
                }
            }
        }

    }

    public static boolean hasStoragePermission(Context context) {
        int read = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);
        return read == PackageManager.PERMISSION_GRANTED;

    }

    public static void GetListTruyen(QuanLyTruyen context) {
        //Khởi tạo HomeQlItemTruyen
        HomeQlItemTruyen homeQlItemTruyen = new HomeQlItemTruyen(getall_tt(sqLiteDAO), context);
        //Chuyền Adapter homeQlItemTruyen cho list_item_truyen
        list_item_truyen.setAdapter(homeQlItemTruyen);
    }
}