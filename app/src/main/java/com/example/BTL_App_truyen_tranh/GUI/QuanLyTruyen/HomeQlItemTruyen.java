package com.example.BTL_App_truyen_tranh.GUI.QuanLyTruyen;

import static com.example.BTL_App_truyen_tranh.DAO.TheLoaiDAO.getall_tl;
import static com.example.BTL_App_truyen_tranh.DAO.TruyenTranhDao.sua_truyentranh;
import static com.example.BTL_App_truyen_tranh.DAO.TruyenTranhDao.xoa_truyentranh;
import static com.example.BTL_App_truyen_tranh.GUI.QuanLyTruyen.HomeQuanLy.sqLiteDAO;
import static com.example.BTL_App_truyen_tranh.GUI.QuanLyTruyen.QuanLyTruyen.GetListTruyen;
import static com.example.BTL_App_truyen_tranh.GUI.QuanLyTruyen.QuanLyTruyen.chonphoto;
import static com.example.BTL_App_truyen_tranh.GUI.QuanLyTruyen.QuanLyTruyen.hasStoragePermission;
import static com.example.BTL_App_truyen_tranh.GUI.QuanLyTruyen.QuanLyTruyen.imageView;
import static com.example.BTL_App_truyen_tranh.GUI.QuanLyTruyen.QuanLyTruyen.selectImageUri;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.BTL_App_truyen_tranh.BUS.Utils;
import com.example.BTL_App_truyen_tranh.DTO.TruyenTranh;
import com.example.BTL_App_truyen_tranh.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;


public class HomeQlItemTruyen extends RecyclerView.Adapter<HomeQlItemTruyen.HomeQlItemTruyenHolder> {
    private List<TruyenTranh> listTruyen;
    private QuanLyTruyen context;


    public HomeQlItemTruyen(List<TruyenTranh> listTruyen, QuanLyTruyen context) {
        this.listTruyen = listTruyen;
        this.context = context;
    }

    @NonNull
    @Override
    public HomeQlItemTruyenHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ql_truyen, parent, false);
        return new HomeQlItemTruyenHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeQlItemTruyenHolder holder, int position) {
        TruyenTranh truyenTranh = listTruyen.get(position);
        holder.textid.setText(truyenTranh.getIdTruyen() + "");
        holder.text_ten_truyen.setText(truyenTranh.getTenTruyen());
        holder.text_theLoai.setText(truyenTranh.getTheLoai());
        holder.text_theLoai.setText(truyenTranh.getTheLoai());
        holder.image_truyen.setImageBitmap(Utils.getImage(truyenTranh.getImg()));


        holder.image_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                suatruyentranh(truyenTranh.getIdTruyen(), truyenTranh);
            }
        });
        holder.image_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xoaTruyen(truyenTranh.getIdTruyen());
            }
        });
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, QlChapTruyen.class);
                intent.putExtra("Key_idTruyen", truyenTranh.getIdTruyen());
                context.startActivity(intent);
            }
        });
    }

    private void openImageChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        context.startActivityForResult(intent, chonphoto);
    }

    private void suatruyentranh(int id, TruyenTranh truyenTranhs) {
        Dialog dialog = new Dialog(context);
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
        final ArrayAdapter adapter = new ArrayAdapter(context, R.layout.dropdown_item, getall_tl(sqLiteDAO));
        spinner.setAdapter(adapter);
        int giatri = -1;
        for (int i = 0; i < getall_tl(sqLiteDAO).size(); i++) {
            if (getall_tl(sqLiteDAO).get(i).getTenTheLoai().equalsIgnoreCase(truyenTranhs.getTheLoai())) {
                giatri = i;
                break;
            }
        }
        spinner.setSelection(giatri);
        ten.setText("Sửa truyện tranh");
        button_them.setText("Sửa");
        editTextTenTruyen.setText(truyenTranhs.getTenTruyen());
        editTextGioiThieu.setText(truyenTranhs.getGioiThieu());
        if (radioButton.getText().equals(truyenTranhs.getTinhTrang())) {
            radioButton.setChecked(true);
        } else {
            radioButton2.setChecked(true);
        }


        imageView.setImageBitmap(Utils.getImage(truyenTranhs.getImg()));

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (hasStoragePermission(context)) {
                    openImageChooser();
                } else {
                    ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, chonphoto);
                }
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
                        Toast.makeText(context, "Vui lòng điền đủ thông tin!", Toast.LENGTH_SHORT).show();
                    } else {
                        if (selectImageUri != null) {
                            InputStream inputStream = context.getContentResolver().openInputStream(selectImageUri);
                            TruyenTranh truyenTranh = new TruyenTranh(truyenTranhs.getIdTruyen(), tenTruyenTranh, truyenTranhs.getNgayDang(), trangThai, spinner.getSelectedItem().toString(), gioiThieu, Utils.getBytes(inputStream));
                            if (sua_truyentranh(truyenTranh, sqLiteDAO)) {
                                Toast.makeText(context, "Sửa thành công!", Toast.LENGTH_SHORT).show();
                                GetListTruyen(context);
                                dialog.dismiss();
                            }
                        } else {
                            TruyenTranh truyenTranh = new TruyenTranh(truyenTranhs.getIdTruyen(), tenTruyenTranh, truyenTranhs.getNgayDang(), trangThai, spinner.getSelectedItem().toString(), gioiThieu, truyenTranhs.getImg());
                            if (sua_truyentranh(truyenTranh, sqLiteDAO)) {
                                Toast.makeText(context, "Sửa thành công!", Toast.LENGTH_SHORT).show();
                                GetListTruyen(context);
                                dialog.dismiss();
                            }

                        }

                    }
                } catch (IOException e) {
                    e.printStackTrace();
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

    private void xoaTruyen(int ids) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Bạn có muốn xóa truyện này không!")
                .setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (xoa_truyentranh(ids)) {
                            GetListTruyen(context);
                            Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });

        builder.create();
        builder.show();
    }

    ;

    @Override
    public int getItemCount() {
        if (listTruyen != null) {
            return listTruyen.size();
        }
        return 0;
    }

    public class HomeQlItemTruyenHolder extends RecyclerView.ViewHolder {
        private TextView textid, text_ten_truyen, text_theLoai;
        private ImageView image_edit, image_delete, image_truyen;
        private LinearLayout linearLayout;

        public HomeQlItemTruyenHolder(@NonNull View itemView) {
            super(itemView);
            textid = itemView.findViewById(R.id.textid);
            text_ten_truyen = itemView.findViewById(R.id.text_ten_truyen);
            text_theLoai = itemView.findViewById(R.id.text_theLoai);
            image_edit = itemView.findViewById(R.id.image_edit);
            image_delete = itemView.findViewById(R.id.image_delete);
            image_truyen = itemView.findViewById(R.id.image_truyen);
            linearLayout = itemView.findViewById(R.id.linearLayout);
        }
    }
}
