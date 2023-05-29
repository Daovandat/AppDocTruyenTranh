package com.example.BTL_App_truyen_tranh.GUI.QuanLyTruyen;

import static com.example.BTL_App_truyen_tranh.DAO.ChapTruyenDAO.getall_imgchap;
import static com.example.BTL_App_truyen_tranh.DAO.ChapTruyenDAO.kiem_tra_chap;
import static com.example.BTL_App_truyen_tranh.DAO.ChapTruyenDAO.sua_img;
import static com.example.BTL_App_truyen_tranh.DAO.ChapTruyenDAO.them_chaptruyen;
import static com.example.BTL_App_truyen_tranh.DAO.ChapTruyenDAO.them_imgchaptruyen;
import static com.example.BTL_App_truyen_tranh.GUI.QuanLyTruyen.HomeQuanLy.sqLiteDAO;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.BTL_App_truyen_tranh.BUS.Utils;
import com.example.BTL_App_truyen_tranh.DTO.Chap;
import com.example.BTL_App_truyen_tranh.DTO.imgChap;
import com.example.BTL_App_truyen_tranh.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class addChap extends AppCompatActivity {
    FloatingActionButton floating_add;
    public static RecyclerView list_item_ql_ct;
    ImageView img_back;
    public static List<imgChap> list = new ArrayList<>();
    public static final int chonphoto = 321;
    public static int chonphoto2 = 32122;
    TextView text_luu,textTieude;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_chap);
        img_back = findViewById(R.id.img_back);
        floating_add = findViewById(R.id.floating_add);
        list_item_ql_ct = findViewById(R.id.list_item_ql_ct);
        text_luu = findViewById(R.id.text_luu);
        textTieude = findViewById(R.id.textTieude);
        intent=getIntent();
        list.clear();
        if (intent.getStringExtra("Key_tenChap") != null) {
            textTieude.setText("Sửa chap truyện");
            list=getall_imgchap(intent.getStringExtra("Key_tenChap"),intent.getIntExtra("Key_idTruyen",0),sqLiteDAO);
            GetListImgChap(addChap.this);
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(addChap.this, LinearLayoutManager.VERTICAL, false);
        list_item_ql_ct.setLayoutManager(linearLayoutManager);
        floating_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hasStoragePermissionImg(addChap.this)) {
                    openImageChooserImg();
                } else {
                    ActivityCompat.requestPermissions(addChap.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, chonphoto);
                }
            }
        });
        text_luu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (intent.getStringExtra("Key_tenChap") != null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(addChap.this);
                    builder.setMessage("Bạn có muốn sửa chap này không!")
                            .setPositiveButton("sửa", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    sua_img(intent.getStringExtra("Key_tenChap"),sqLiteDAO);
                                    for (int i = 0; i < list.size(); i++) {
                                        imgChap imgChap = new imgChap(0,intent.getIntExtra("Key_idTruyen",0), intent.getStringExtra("Key_tenChap"), list.get(i).getImg());
                                        them_imgchaptruyen(imgChap, sqLiteDAO);
                                        if (i == list.size()-1) {
                                            Toast.makeText(addChap.this, "Sửa thành công!", Toast.LENGTH_SHORT).show();
                                            onBackPressed();
                                            dialog.dismiss();
                                        }
                                    }
                                }
                            })
                            .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                }
                            });

                    builder.create();
                    builder.show();
                }else {
                    luuChap();
                }
            }
        });
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void luuChap() {
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

        EditText edit_tenchap = dialog.findViewById(R.id.edit_addtl);
        TextView text_view = dialog.findViewById(R.id.text_view);
        Button button_huy = dialog.findViewById(R.id.button_huy);
        Button button_them = dialog.findViewById(R.id.button_them);
        text_view.setText("Lưu chap truyện");
        edit_tenchap.setHint("Nhập tên chap");
        button_them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenchap = edit_tenchap.getText().toString().trim();
                if (tenchap.isEmpty()) {
                    Toast.makeText(dialog.getContext(), "Vui lòng nhập tên chap!", Toast.LENGTH_SHORT).show();
                } else {
                    if (kiem_tra_chap(tenchap,intent.getIntExtra("Key_idTruyen",0))) {
                        Toast.makeText(dialog.getContext(), "Tên thể chap đã tồn tại!", Toast.LENGTH_SHORT).show();
                    } else {
                        if (list.size() > 0) {
                            Chap chap = new Chap(0, intent.getIntExtra("Key_idTruyen", 0), tenchap);
                            if (them_chaptruyen(chap, sqLiteDAO)) {
                                for (int i = 0; i < list.size(); i++) {
                                    imgChap imgChap = new imgChap(0,intent.getIntExtra("Key_idTruyen",0), tenchap, list.get(i).getImg());
                                    them_imgchaptruyen(imgChap, sqLiteDAO);
                                    if (i == list.size()-1) {
                                        Toast.makeText(dialog.getContext(), "Thêm thành công!", Toast.LENGTH_SHORT).show();
                                        onBackPressed();
                                        dialog.dismiss();
                                    }
                                }

                            }
                        } else {
                            Toast.makeText(dialog.getContext(), "vui lòng thêm ảnh !", Toast.LENGTH_SHORT).show();
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

    public static void GetListImgChap(addChap context) {
        QLChapItemImg chapItemImg = new QLChapItemImg(list, context);
        list_item_ql_ct.setAdapter(chapItemImg);
    }

    public static boolean hasStoragePermissionImg(Context context) {
        int read = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);
        return read == PackageManager.PERMISSION_GRANTED;

    }

    private void openImageChooserImg() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, chonphoto);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            if (requestCode == chonphoto) {
                try {
                    Uri selectImageUri = data.getData();
                    InputStream inputStream = getContentResolver().openInputStream(selectImageUri);
                    GetLitImg(inputStream,addChap.this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (requestCode == chonphoto2) {
                try {
                    Uri selectImageUri = data.getData();
                    InputStream inputStream = getContentResolver().openInputStream(selectImageUri);
                    GetUpdateImg(inputStream,addChap.this,chonphoto2);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }
    public static void GetLitImg(InputStream inputStream,addChap context){
        try {
            list.add(new imgChap(1,1,"s",Utils.getBytes(inputStream)));
            GetListImgChap(context);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void GetUpdateImg(InputStream inputStream,addChap context,int position){
        try {
            list.set(position,new imgChap(1,1,"s",Utils.getBytes(inputStream)));
            GetListImgChap(context);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}