package com.example.BTL_App_truyen_tranh.GUI.QuanLyTruyen;

import static com.example.BTL_App_truyen_tranh.DAO.ChapTruyenDAO.sua_img;
import static com.example.BTL_App_truyen_tranh.DAO.ChapTruyenDAO.xoa_chap;
import static com.example.BTL_App_truyen_tranh.GUI.QuanLyTruyen.QlChapTruyen.GetListChap;
import static com.example.BTL_App_truyen_tranh.GUI.QuanLyTruyen.HomeQuanLy.sqLiteDAO;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.BTL_App_truyen_tranh.DTO.Chap;
import com.example.BTL_App_truyen_tranh.R;

import java.util.List;


public class HomeQLItemChap extends RecyclerView.Adapter<HomeQLItemChap.HomeQLItemChapHolder> {
    private List<Chap> listchap;
    private Context context;

    public HomeQLItemChap(List<Chap> listchap, Context context) {
        this.listchap = listchap;
        this.context = context;
    }

    @NonNull
    @Override
    public HomeQLItemChapHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ql_tl, parent, false);
        return new HomeQLItemChapHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeQLItemChapHolder holder, int position) {
        Chap chap = listchap.get(position);
        holder.text_name_tl.setText(chap.getTenChap());
        holder.image_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, addChap.class);
                intent.putExtra("Key_tenChap", chap.getTenChap());
                intent.putExtra("Key_idTruyen", chap.getIdtt());
                context.startActivity(intent);
            }
        });
        holder.image_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xoaChap(chap.getIdChap(), chap.getTenChap(),chap.getIdtt());
            }
        });
    }


    private void xoaChap(int ids, String tenchap,int idtt) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Bạn có muốn xóa chap này không!")
                .setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (xoa_chap(ids)) {
                            sua_img(tenchap, sqLiteDAO);
                            GetListChap(context,idtt);
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


    @Override
    public int getItemCount() {
        if (listchap != null) {
            return listchap.size();
        }
        return 0;
    }

    public class HomeQLItemChapHolder extends RecyclerView.ViewHolder {
        private TextView text_name_tl;
        private ImageView image_edit, image_delete;

        public HomeQLItemChapHolder(@NonNull View itemView) {
            super(itemView);
            text_name_tl = itemView.findViewById(R.id.text_name_tl);
            image_edit = itemView.findViewById(R.id.image_edit);
            image_delete = itemView.findViewById(R.id.image_delete);
        }
    }
}
