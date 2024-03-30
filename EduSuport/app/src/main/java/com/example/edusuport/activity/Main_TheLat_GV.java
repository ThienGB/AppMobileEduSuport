package com.example.edusuport.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.edusuport.R;
import com.example.edusuport.adapter.MonHocAdapter;
import com.example.edusuport.adapter.TheLatAdapterGV;
import com.example.edusuport.model.TheLat;

import java.util.ArrayList;

public class Main_TheLat_GV extends AppCompatActivity {

    TheLatAdapterGV theLatAdapterGV;

    ListView lv;
    Button btnAdd1,btnAddN;
    ImageButton imgBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_thelat_flashcards);
        getWig();


        ArrayList<TheLat> list=new ArrayList<TheLat>();
        list.add(new TheLat( "1","Tại sao trái đất màu xanh","Vì có cây"));
        list.add(new TheLat( "1","Văn của mấy thằng mất dạy","Hết tình còn cảm"));
        list.add(new TheLat( "1","1+1=?","2"));
        list.add(new TheLat( "1","CO2 là gì","Khí các b ô nát"));

        theLatAdapterGV = new TheLatAdapterGV(Main_TheLat_GV.this,R.layout.item_main_flashcard, list );
        lv.setAdapter(theLatAdapterGV);

        btnAddN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Main_TheLat_GV.this,
                        "Your Message", Toast.LENGTH_LONG).show();
            }
        });
        imgBack=(ImageButton) findViewById(R.id.back_TaiLieuMain);
        btnAdd1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopUpDialog();
            }
        });

    }
    private void showPopUpDialog() {
        android.app.AlertDialog.Builder builder = new AlertDialog.Builder(Main_TheLat_GV.this);
        builder.setTitle("Thẻ: ");
        LayoutInflater inflater = getLayoutInflater();
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog
        // layout
        builder.setView(inflater.inflate(R.layout.test, null));

        // Thêm các lựa chọn vào danh sách


        // Hiển thị AlertDialog
        builder.create().show();
    }

    private void getWig(){
        lv=(ListView) findViewById(R.id.lvTheLats);
        btnAdd1=(Button) findViewById(R.id.them1TheMoi);
        btnAddN=(Button) findViewById(R.id.themNhieuTheMoi);
    }
}