package com.example.edusuport.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.edusuport.R;
import com.example.edusuport.activity.ChinhsuaThoiKhoaBieuActivity;
import com.example.edusuport.activity.DangTaiTaiLieuActivity;
import com.example.edusuport.activity.DuyetDonXinNghiHocActivity;
import com.example.edusuport.activity.GuiThongBaoActivity;
import com.example.edusuport.activity.Home;
import com.example.edusuport.activity.HopThuGopYActivity;
import com.example.edusuport.activity.NhanXetChungActivity;
import com.example.edusuport.activity.NhapDiemChungActivity;
import com.example.edusuport.model.ChucNang;
import com.example.edusuport.model.MonHoc;

import java.util.ArrayList;

public class ChucNangHomeAdapter extends ArrayAdapter {
    Context context;
    int resource;
    ArrayList<ChucNang> list= new ArrayList<ChucNang>();

    public ChucNangHomeAdapter(Context context, int resource, ArrayList<ChucNang> list) {
        super(context, resource, list);
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View customView= convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        customView = inflater.inflate(R.layout.icon_tailieu_gv, null);

        ImageView imgHinh = (ImageView)  customView.findViewById(R.id.imgMonHoc);
        TextView txtTen =(TextView) customView.findViewById(R.id.textTenMonHoc);
        txtTen.setText(list.get(position).getTenChucNang());
        imgHinh.setImageResource(R.drawable.flashcard);
        customView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = list.get(position).getIdChucNang();
                switch (id){
                    case "TKBGV":{
                        Intent intent=new Intent(context, ChinhsuaThoiKhoaBieuActivity.class);
                        context.startActivity(intent);
                        break;
                    }
                    case "TBGV":{
                        Intent intent=new Intent(context, GuiThongBaoActivity.class);
                        context.startActivity(intent);
                        break;
                    }
                    case "GYGV":{
                        Intent intent=new Intent(context, HopThuGopYActivity.class);
                        context.startActivity(intent);
                        break;
                    } case "DXNGV":{
                        Intent intent=new Intent(context, DuyetDonXinNghiHocActivity.class);
                        context.startActivity(intent);
                        break;
                    }
                    case "TLHTGV":{
                        Intent intent=new Intent(context, DangTaiTaiLieuActivity.class);
                        context.startActivity(intent);
                        break;
                    }
                    case "NDGV":{
                        Intent intent=new Intent(context, NhapDiemChungActivity.class);
                        context.startActivity(intent);
                        break;
                    }
                    case "DGHSGV":{
                        Intent intent=new Intent(context, NhanXetChungActivity.class);
                        context.startActivity(intent);
                        break;
                    }


                }
            }
        });
        return customView;
    }
}
