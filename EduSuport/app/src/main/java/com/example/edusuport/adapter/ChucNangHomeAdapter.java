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
import com.example.edusuport.activity.Main_DonXinNghiHoc_PH;
import com.example.edusuport.activity.Main_TheLat_GV;
import com.example.edusuport.activity.Main_ThuGopY_PH;
import com.example.edusuport.activity.NhanXetChungActivity;
import com.example.edusuport.activity.NhapDiemChungActivity;
import com.example.edusuport.activity.ThuGopYPhActivity;
import com.example.edusuport.activity.XemDiemActivity;
import com.example.edusuport.activity.XemNhanXetActivity;
import com.example.edusuport.activity.XemTaiLieu_MonHSActivity;
import com.example.edusuport.activity.XemThoiKhoaBieuActivity;
import com.example.edusuport.activity.XemThongBaoActivity;
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
        String id = list.get(position).getIdChucNang();
        switch (id){
            case "TKBGV":{
                imgHinh.setImageResource(R.drawable.ic_calendar);
                break;
            }
            case "TBGV":{
                imgHinh.setImageResource(R.drawable.icon_thongbao);
                break;
            }
            case "GYGV":{
                imgHinh.setImageResource(R.drawable.icon_feedback);break;
            } case "DXNGV":{
                imgHinh.setImageResource(R.drawable.icon_license);break;
            }
            case "TLHTGV":{
                imgHinh.setImageResource(R.drawable.icon_document);break;
            }
            case "NDGV":{
                imgHinh.setImageResource(R.drawable.icon_score);break;
            }
            case "DGHSGV":{
                imgHinh.setImageResource(R.drawable.icon_dghs);break;
            }


        }



        customView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (id){
                    case "TBPH":{
                        Intent intent = new Intent(context, XemThongBaoActivity.class);
                        intent.putExtra("role", "phuhuynh");
                        context.startActivity(intent);
                        break;
                    }
                    case "TKBGV":{
                        Intent intent=new Intent(context, ChinhsuaThoiKhoaBieuActivity.class);
                        context.startActivity(intent);
                        break;
                    }
                    case "TBGV":{
                        Intent intent=new Intent(context, GuiThongBaoActivity.class);
                        context.startActivity(intent);
                        break;
                    } case "TLGV":{
                        Intent intent=new Intent(context, Main_TheLat_GV.class);
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
                    case "DXPPH":{
                        Intent intent=new Intent(context, Main_DonXinNghiHoc_PH.class);
                        context.startActivity(intent);
                        break;
                    }
                    case "GYPH":{
                        Intent intent=new Intent(context, Main_ThuGopY_PH.class);
                        context.startActivity(intent);
                        break;
                    }
                    case "XNHPH":{
                        Intent intent=new Intent(context, XemNhanXetActivity.class);
                        intent.putExtra("role", "phuhuynh");
                        context.startActivity(intent);
                        break;
                    }
                    case "XTKBHS":{
                        Intent intent=new Intent(context, XemThoiKhoaBieuActivity.class);
                        context.startActivity(intent);
                        break;
                    }
                    case "XTBHS":{
                        Intent intent=new Intent(context, XemThongBaoActivity.class);
                        intent.putExtra("role", "hocsinh");
                        context.startActivity(intent);
                        break;
                    }
                    case "XTLHS":{
                        Intent intent=new Intent(context, XemTaiLieu_MonHSActivity.class);
                        context.startActivity(intent);
                        break;
                    }
                    case "XDHS":{
                        Intent intent=new Intent(context, XemDiemActivity.class);
                        context.startActivity(intent);
                        break;
                    }
                    case "XNHHS":{
                        Intent intent=new Intent(context, XemNhanXetActivity.class);
                        intent.putExtra("role", "hocsinh");
                        context.startActivity(intent);
                        break;
                    }


                }
            }
        });
        return customView;
    }
}
