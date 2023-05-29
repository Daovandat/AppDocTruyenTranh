package com.example.BTL_App_truyen_tranh.GUI.QuanLyTruyen;

import static com.example.BTL_App_truyen_tranh.GUI.QuanLyTruyen.QuanLyTruyen.chonphoto;
import static com.example.BTL_App_truyen_tranh.GUI.QuanLyTruyen.addChap.GetListImgChap;
import static com.example.BTL_App_truyen_tranh.GUI.QuanLyTruyen.addChap.chonphoto2;
import static com.example.BTL_App_truyen_tranh.GUI.QuanLyTruyen.addChap.hasStoragePermissionImg;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.BTL_App_truyen_tranh.BUS.Utils;
import com.example.BTL_App_truyen_tranh.DTO.imgChap;
import com.example.BTL_App_truyen_tranh.R;

import java.util.List;


public class QLChapItemImg extends RecyclerView.Adapter<QLChapItemImg.ChapItemImgHolder> {
    private List<imgChap> listchap;
    private addChap context;

    public QLChapItemImg(List<imgChap> listchap, addChap context) {
        this.listchap = listchap;
        this.context = context;
    }

    @NonNull
    @Override
    public ChapItemImgHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_img, parent, false);
        return new ChapItemImgHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChapItemImgHolder holder, @SuppressLint("RecyclerView") int position) {
        imgChap imgChap = listchap.get(position);
        holder.image_chap.setImageBitmap(Utils.getImage(imgChap.getImg()));
        holder.button_sua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hasStoragePermissionImg(context)) {
                    openImageChooserImg(position);
                } else {
                    ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, chonphoto);
                }
            }
        });
        holder.button_xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xoaImgChap(position);
            }
        });
    }


    private void openImageChooserImg(int position) {
        chonphoto2=position;
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        context.startActivityForResult(intent, chonphoto2);
    }

    private void xoaImgChap(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Bạn có muốn xóa hình này không!")
                .setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        listchap.remove(position);
                        GetListImgChap(context);
                        Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });

        builder.create();
        builder.show();
    }


    @Override
    public int getItemCount() {
        if (listchap != null) {
            return listchap.size();
        }
        return 0;
    }

    public class ChapItemImgHolder extends RecyclerView.ViewHolder {
        private ImageView image_chap;
        private Button button_xoa, button_sua;

        public ChapItemImgHolder(@NonNull View itemView) {
            super(itemView);
            image_chap = itemView.findViewById(R.id.image_chap);
            button_xoa = itemView.findViewById(R.id.button_xoa);
            button_sua = itemView.findViewById(R.id.button_sua);
        }
    }
}
