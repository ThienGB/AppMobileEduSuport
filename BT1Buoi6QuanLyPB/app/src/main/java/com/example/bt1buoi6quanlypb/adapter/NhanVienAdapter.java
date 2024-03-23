package com.example.bt1buoi6quanlypb.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.bt1buoi6quanlypb.R;
import com.example.bt1buoi6quanlypb.model.NhanVien;

import java.util.ArrayList;

public class NhanVienAdapter extends ArrayAdapter<NhanVien> {
    Activity context;
    int layoutID;
    ArrayList<NhanVien> arrNhanVien;
    public NhanVienAdapter (Activity context, int textViewResourceId, ArrayList<NhanVien>objects){
        super(context, textViewResourceId, objects);
        this.context= context;
        this.layoutID = textViewResourceId;
        this.arrNhanVien = objects;
    }
    @NonNull
    @Override
    public View getView(int position, @NonNull View convertView, @NonNull ViewGroup parent){
        convertView = context.getLayoutInflater().inflate(layoutID, null);
        TextView txtnv = (TextView)convertView.findViewById(R.id.txtShortInfo);
        TextView txtmotanv = convertView.findViewById(R.id.txtDetalInfo);
        ImageView img = (ImageView) convertView.findViewById(R.id.imgView);

        NhanVien nv = arrNhanVien.get(position);
        txtnv.setText(nv.toString());
        String strMota= "";
        String cv = "Chức vụ :" + nv.getChucvu().getChucVu();
        String gt = "Giới tính: " + (nv.isGioitinh()?"Nữ":"Nam");
        img.setImageResource(R.drawable.img_3);
        if (!nv.isGioitinh())
            img.setImageResource(R.drawable.img_2);
        strMota = cv +"\n" + gt;
        txtmotanv.setText(strMota);
        return convertView;
    }
}
