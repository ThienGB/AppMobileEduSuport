package com.example.edusuport.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.edusuport.R;
import com.example.edusuport.adapter.MonHocAdapter;
import com.example.edusuport.controllers.MonHocController;
import com.example.edusuport.model.MonHoc;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Objects;

public class DangTaiTaiLieuActivity extends AppCompatActivity {

    GridView gvmon;
    MonHocAdapter monHocAdapter;
    MonHocController monHocController=new MonHocController();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_tai_tai_lieu);


        LayoutInflater inflater= (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);


        gvmon=(GridView)findViewById(R.id.grid_monhoc);

        monHocController.setGridiewMonHoc(new MonHocController.DataRetrievedCallback() {
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
                Log.d("huuhu", String.valueOf(monHoc.getIdMon()));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

}