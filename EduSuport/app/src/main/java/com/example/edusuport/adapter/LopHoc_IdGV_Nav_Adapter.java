package com.example.edusuport.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.edusuport.R;
import com.example.edusuport.model.LopHoc;
import com.example.edusuport.model.MonHoc;

import java.util.ArrayList;
import java.util.List;

public class LopHoc_IdGV_Nav_Adapter  extends RecyclerView.Adapter<LopHoc_IdGV_Nav_Adapter.ViewHolder> {
   // Activity context;
    int resource;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView txtTen;

        public ViewHolder(View itemView) {

            super(itemView);
            txtTen =(TextView) itemView.findViewById(R.id.ten_navLopHoc);
        }
    }
    ArrayList<LopHoc> List= new ArrayList<LopHoc>();


    public LopHoc_IdGV_Nav_Adapter(ArrayList<LopHoc> list) {
            List = list;
        }

    @NonNull
    @Override
    public LopHoc_IdGV_Nav_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.item_chonlop, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView) ;

        return viewHolder;
        }

    @Override
    public void onBindViewHolder(@NonNull LopHoc_IdGV_Nav_Adapter.ViewHolder viewHolder, int position) {
        LopHoc lopHoc = List.get(position);

        TextView textView = viewHolder.txtTen;
        textView.setText(lopHoc.getTenLopHoc());


    }


    @Override
    public int getItemCount() {
        return List.size();
    }

}
