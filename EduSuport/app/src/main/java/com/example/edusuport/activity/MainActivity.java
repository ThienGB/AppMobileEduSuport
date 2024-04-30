package com.example.edusuport.activity;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.edusuport.R;
import com.example.edusuport.adapter.MonHocAdapter;
import com.example.edusuport.model.MonHoc;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    GridView lvApp;

    MonHocAdapter monHocAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvApp=(GridView) findViewById(R.id.list_view);

        LayoutInflater inflater= (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        ArrayList<MonHoc> list=new ArrayList<>();
        list.add(new MonHoc( "1","Toán học"));
        list.add(new MonHoc( "2","Lý học"));
        list.add(new MonHoc( "3","Hóa học"));
        list.add(new MonHoc( "4","Toán học"));
        list.add(new MonHoc( "5","Lý học"));
        list.add(new MonHoc( "6","Hóa học"));


        monHocAdapter=new MonHocAdapter(MainActivity.this,R.layout.icon_tailieu_gv,list);
        lvApp.setAdapter(monHocAdapter);

        addEvents();
    // Write a message to the database
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("message");
//
//        myRef.setValue("Hello, World!");

    }
    private  void HandelClick(int position){

    }
    private void addEvents() {
        lvApp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HandelClick(position);

            }
        });
    }
}