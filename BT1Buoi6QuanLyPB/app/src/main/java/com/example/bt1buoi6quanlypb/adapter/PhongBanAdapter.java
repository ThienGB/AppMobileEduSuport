package com.example.bt1buoi6quanlypb.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.bt1buoi6quanlypb.R;
import com.example.bt1buoi6quanlypb.model.NhanVien;
import com.example.bt1buoi6quanlypb.model.PhongBan;

import java.util.ArrayList;

public class PhongBanAdapter extends ArrayAdapter<PhongBan>{
    Activity context;
    int layoutId;
    ArrayList<PhongBan> arrPhongBan;
    public PhongBanAdapter (Activity context, int textViewResourceId, ArrayList<PhongBan>objects)
    {
        super(context, textViewResourceId, objects);
        this.context = context;
        this.layoutId = textViewResourceId;
        this.arrPhongBan = objects;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        convertView = context.getLayoutInflater().inflate(layoutId, null);
        TextView txtpb = (TextView) convertView.findViewById(R.id.txtShortInfo);
        TextView txtmotapb = (TextView) convertView.findViewById(R.id.txtDetalInfo);
        PhongBan pb =arrPhongBan.get(position);
        txtpb.setText(pb.toString());
        String strMota = "";
        String tp = "Trưởng phòng: [Chưa có]";
        NhanVien nv = pb.getTruongPhong();
        if (nv != null){
            tp = "Trưởng phòng: [" + nv.getTen() + "]";
        }
        ArrayList<NhanVien> dsPp = pb.getPhoPhong();
        String pp = "Phó phòng: [Chưa có]";
        if (dsPp != null) {
            if (dsPp.size() > 0){
                pp = "Phó phòng: \n";
                for (int i = 0; i < dsPp.size(); i++){
                    pp += (i + 1)+ ". " + dsPp.get(i).getTen() + "\n";
                }
            }
        }

        strMota = tp + "\n" + pp;
        txtmotapb.setText(strMota);
        return convertView;
    }
}
