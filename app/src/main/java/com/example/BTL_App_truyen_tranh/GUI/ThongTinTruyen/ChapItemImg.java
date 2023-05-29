package com.example.BTL_App_truyen_tranh.GUI.ThongTinTruyen;

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
import com.example.BTL_App_truyen_tranh.GUI.QuanLyTruyen.addChap;
import com.example.BTL_App_truyen_tranh.R;

import java.util.List;

public class ChapItemImg extends RecyclerView.Adapter<ChapItemImg.ChapItemImgHoldes> {
    private List<imgChap> listchap;

    public ChapItemImg(List<imgChap> listchap) {
        this.listchap = listchap;
    }

    @NonNull
    @Override
    public ChapItemImgHoldes onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chapimg, parent, false);
        return new ChapItemImgHoldes(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChapItemImgHoldes holder, @SuppressLint("RecyclerView") int position) {
        imgChap imgChap = listchap.get(position);
        holder.image_chap.setImageBitmap(Utils.getImage(imgChap.getImg()));
    }


    @Override
    public int getItemCount() {
        if (listchap != null) {
            return listchap.size();
        }
        return 0;
    }

    public class ChapItemImgHoldes extends RecyclerView.ViewHolder {
        private ImageView image_chap;

        public ChapItemImgHoldes(@NonNull View itemView) {
            super(itemView);
            image_chap = itemView.findViewById(R.id.image_chap);
        }
    }
}
