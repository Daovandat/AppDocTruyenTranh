package com.example.BTL_App_truyen_tranh.GUI.Home;

import static com.example.BTL_App_truyen_tranh.DAO.ChapTruyenDAO.getall_chap;
import static com.example.BTL_App_truyen_tranh.GUI.Home.HomePage.sqLiteDAO1;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.BTL_App_truyen_tranh.BUS.Utils;
import com.example.BTL_App_truyen_tranh.DTO.Chap;
import com.example.BTL_App_truyen_tranh.DTO.TruyenTranh;
import com.example.BTL_App_truyen_tranh.GUI.ThongTinTruyen.ThongTinTuyen;
import com.example.BTL_App_truyen_tranh.R;

import java.util.List;

public class HomeItemTruyen extends RecyclerView.Adapter<HomeItemTruyen.HomeItemTruyenHolder> {
    private List<TruyenTranh> listTruyen;
    private Context context;

    public HomeItemTruyen(List<TruyenTranh> listTruyen, Context context) {
        this.listTruyen = listTruyen;
        this.context = context;
    }

    @NonNull
    @Override
    public HomeItemTruyenHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_truyen, parent, false);
        return new HomeItemTruyenHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeItemTruyenHolder holder, int position) {
        TruyenTranh truyenTranh = listTruyen.get(position);
        List<Chap>list=getall_chap(truyenTranh.getIdTruyen(),sqLiteDAO1);
        holder.textName.setText(truyenTranh.getTenTruyen());
        if(list.size()!=0){
             holder.textChap.setText(list.get(list.size()-1).getTenChap());
        }else {
            holder.textChap.setText("Chưa có chap truyện");
        }

        holder.imageViewAnh.setImageBitmap(Utils.getImage(truyenTranh.getImg()));
        holder.card_item_truyen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ThongTinTuyen.class);
                intent.putExtra("Key_idTruyen", truyenTranh.getIdTruyen());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (listTruyen != null) {
            return listTruyen.size();
        }
        return 0;
    }

    public class HomeItemTruyenHolder extends RecyclerView.ViewHolder {
        private ImageView imageViewAnh;
        private TextView textName;
        private TextView textChap;
        private CardView card_item_truyen;

        public HomeItemTruyenHolder(@NonNull View itemView) {
            super(itemView);
            imageViewAnh = itemView.findViewById(R.id.imageViewAnh);
            textName = itemView.findViewById(R.id.textName);
            textChap = itemView.findViewById(R.id.textChap);
            card_item_truyen = itemView.findViewById(R.id.card_item_truyen);
        }
    }
}
