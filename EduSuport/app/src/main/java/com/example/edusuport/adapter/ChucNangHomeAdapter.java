package com.example.edusuport.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.edusuport.R;
import com.example.edusuport.model.ChucNang;
import com.example.edusuport.model.MonHoc;

import java.util.ArrayList;

public class ChucNangHomeAdapter extends ArrayAdapter {
    Activity context;
    int resource;
    ArrayList<ChucNang> List= new ArrayList<ChucNang>();

    public ChucNangHomeAdapter(Context context, int resource, ArrayList<ChucNang> list) {
        super(context, resource, list);
        List = list;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View customView= convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        customView = inflater.inflate(R.layout.icon_tailieu_gv, null);

        ImageView imgHinh = (ImageView)  customView.findViewById(R.id.imgMonHoc);
        TextView txtTen =(TextView) customView.findViewById(R.id.textTenMonHoc);
        txtTen.setText(List.get(position).getTenChucNang());
        imgHinh.setImageResource(R.drawable.flashcard);
        return customView;
    }
}
