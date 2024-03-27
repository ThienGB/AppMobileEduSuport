package com.example.edusuport.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;

import com.example.edusuport.R;
import com.example.edusuport.adapter.MonHocAdapter;
import com.example.edusuport.model.MonHoc;

import java.util.ArrayList;

public class DangTaiTaiLieuActivity extends AppCompatActivity {

    GridView gvmon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_tai_tai_lieu);

        ArrayList<MonHoc> list=new ArrayList<MonHoc>();
        list.add(new MonHoc( 1,"Toán học"));
        list.add(new MonHoc( 2,"Lý học"));
        list.add(new MonHoc( 3,"Hóa học"));
        list.add(new MonHoc( 4,"Toán học"));
        list.add(new MonHoc( 5,"Lý học"));
        list.add(new MonHoc( 6,"Hóa học"));
        list.add(new MonHoc( 7,"Toán học"));
        list.add(new MonHoc( 8,"Lý học"));
        list.add(new MonHoc( 9,"Hóa học"));
        list.add(new MonHoc( 10,"Toán học"));
        list.add(new MonHoc( 11,"Lý học"));
        list.add(new MonHoc( 12,"Hóa học"));

        gvmon=(GridView)findViewById(R.id.grid_monhoc);
        MonHocAdapter monHocAdapter = new MonHocAdapter(DangTaiTaiLieuActivity.this,R.layout.icon_tailieu_gv, list );
        gvmon.setAdapter(monHocAdapter);
    }

}