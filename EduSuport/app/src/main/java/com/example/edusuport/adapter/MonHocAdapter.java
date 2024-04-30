package com.example.edusuport.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.edusuport.R;
import com.example.edusuport.model.MonHoc;

import java.util.ArrayList;

public class MonHocAdapter extends ArrayAdapter {
    Activity context;
    int resource;
    ArrayList<MonHoc> List= new ArrayList<MonHoc>();

    public MonHocAdapter( Context context, int resource, ArrayList<MonHoc> list) {
        super(context, resource, list);
        List = list;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View customView= convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        customView = inflater.inflate(R.layout.icon_tailieu_gv, null);
      //  ImageView imgHinh = (ImageView)  customView.findViewById(R.id.imgHinh);
        TextView txtTen =(TextView) customView.findViewById(R.id.textTenMonHoc);
        txtTen.setText(List.get(position).getTenMon());
        return customView;
    }
}
