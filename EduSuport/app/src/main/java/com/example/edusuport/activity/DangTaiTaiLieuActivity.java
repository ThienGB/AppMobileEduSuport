package com.example.edusuport.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.edusuport.R;
import com.example.edusuport.adapter.MonHocAdapter;
import com.example.edusuport.controllers.DangTaiTaiLieuController;
import com.example.edusuport.controllers.LopHocController;
import com.example.edusuport.model.LopHoc;
import com.example.edusuport.model.MonHoc;

import java.util.ArrayList;

public class DangTaiTaiLieuActivity extends AppCompatActivity {

    GridView gvmon;
    MonHocAdapter monHocAdapter;
    DangTaiTaiLieuController dangTaiTaiLieuController =new DangTaiTaiLieuController();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_tai_tai_lieu);


        LayoutInflater inflater= (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);


        gvmon=(GridView)findViewById(R.id.grid_monhoc);

        dangTaiTaiLieuController.setGridiewMonHoc(new DangTaiTaiLieuController.DataRetrievedCallback_MonHoc() {
            @Override
            public void onDataRetrieved(ArrayList<MonHoc> monHocList) {
                monHocAdapter = new MonHocAdapter(DangTaiTaiLieuActivity.this,R.layout.icon_tailieu_gv, monHocList );
                gvmon.setAdapter(monHocAdapter);
            }
        });


        addEventsClick();

    }



    private void HandelClick(int position){
        Toast.makeText(DangTaiTaiLieuActivity.this,"position"+position,Toast.LENGTH_SHORT).show();
    }

    private void addEventsClick() {
        gvmon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MonHoc monHoc =(MonHoc)  monHocAdapter.getItem(position);
                Intent intent= new Intent(DangTaiTaiLieuActivity.this,DangTaiTaiLieu_MonActivity.class);
                intent.putExtra("idMon",monHoc.getIdMon());
                intent.putExtra("idGV","1");
                LopHocController lopHocController=new LopHocController();
                lopHocController.getListLopHoc_idGV("1", new LopHocController.DataRetrievedCallback_LopHoc() {
                    @Override
                    public void onDataRetrieved(ArrayList<LopHoc> monHocList) {
                        intent.putExtra("firstClass",monHocList.get(0).getIdLopHoc().toString());
                       // Log.d("FIRST CLASS",monHocList.get(0).getIdLopHoc().toString());
                        //Toast.makeText(getApplicationContext(), monHocList.get(0).getIdLopHoc().toString(), Toast.LENGTH_SHORT).show();
                    }
                });
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

}