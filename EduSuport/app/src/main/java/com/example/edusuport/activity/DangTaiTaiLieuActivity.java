package com.example.edusuport.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.edusuport.R;
import com.example.edusuport.adapter.MonHocAdapter;
import com.example.edusuport.model.MonHoc;

import java.util.ArrayList;

public class DangTaiTaiLieuActivity extends AppCompatActivity {

    GridView gvmon;
    MonHocAdapter monHocAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_tai_tai_lieu);


        LayoutInflater inflater= (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        ArrayList<MonHoc> list=new ArrayList<MonHoc>();
        list.add(new MonHoc( "1","Toán học"));
        list.add(new MonHoc( "2","Lý học"));
        list.add(new MonHoc( "3","Hóa học"));
        list.add(new MonHoc( "4","Toán học"));
        list.add(new MonHoc( "5","Lý học"));
        list.add(new MonHoc( "6","Hóa học"));



        gvmon=(GridView)findViewById(R.id.grid_monhoc);
        monHocAdapter = new MonHocAdapter(DangTaiTaiLieuActivity.this,R.layout.icon_tailieu_gv, list );
        gvmon.setAdapter(monHocAdapter);


        addEventsClick();

    }
    private void addDataGridView(ArrayList<MonHoc> list){

    }

    private void HandelClick(int position){
        Toast.makeText(DangTaiTaiLieuActivity.this,"position"+position,Toast.LENGTH_SHORT).show();
    }

    private void addEventsClick() {
        gvmon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(DangTaiTaiLieuActivity.this,"position"+position,Toast.LENGTH_SHORT).show();
                Intent intent= new Intent(DangTaiTaiLieuActivity.this,DangTaiTaiLieu_MonActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

}