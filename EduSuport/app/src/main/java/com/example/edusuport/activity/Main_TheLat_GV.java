package com.example.edusuport.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.edusuport.R;
import com.example.edusuport.adapter.TheLatAdapterGV;
import com.example.edusuport.model.TheLat;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

public class Main_TheLat_GV extends AppCompatActivity {

    TheLatAdapterGV theLatAdapterGV;

    ListView lv;
    ImageButton btnAdd1;
    ImageButton imgBack;
    BottomSheetDialog dialog;

    Button add;
    private String txtCauHoi,txtCauTraloi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_thelat_flashcards);
        getWig();
        getData();

//        dialog =new BottomSheetDialog(this);
//        View view= getLayoutInflater().inflate(R.layout.test,null,false);
//

        handleAllClick();

      //  dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

    }
    private void getData(){
        ArrayList<TheLat> list=new ArrayList<TheLat>();
        list.add(new TheLat( "1","Tại sao trái đất màu xanh","Vì có cây"));
        list.add(new TheLat( "1","Văn của mấy thằng mất dạy","Hết tình còn cảm"));
        list.add(new TheLat( "1","1+1=?","2"));
        list.add(new TheLat( "1","CO2 là gì","Khí các b ô nát"));

        theLatAdapterGV = new TheLatAdapterGV(Main_TheLat_GV.this,R.layout.item_main_flashcard, list );
        lv.setAdapter(theLatAdapterGV);

    }
    private void showPopUpDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Main_TheLat_GV.this);
        LayoutInflater layoutInflater = LayoutInflater.from(Main_TheLat_GV.this);
        builder.setTitle("Thẻ: ");
        LayoutInflater inflater = getLayoutInflater();
        final View alertView = layoutInflater.inflate(R.layout.activity_click_add_card, null);
        builder.setView(inflater.inflate(R.layout.activity_click_add_card, null));
        EditText edCauHoi=(EditText) alertView.findViewById(R.id.add_cauhoi);
        EditText edCauTraLoi=(EditText) alertView.findViewById(R.id.add_cautraloi);
        txtCauHoi= edCauHoi.getText() + "";

        builder.setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                handAddCard();
            }
        });


           builder.create().show();
    }

    private void getWig(){
        lv=(ListView) findViewById(R.id.lvTheLats);
        btnAdd1=(ImageButton) findViewById(R.id.them1TheMoi);
        imgBack=(ImageButton) findViewById(R.id.back_TaiLieuMain);

    }
    private void  test(){
        dialog.show();
    }
    private void handleAllClick(){


        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Main_TheLat_GV.this,DangTaiTaiLieu_MonActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        btnAdd1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // showPopUpDialog();
              //  test();
            }
        });
    }
    private void handAddCard(){

        Toast.makeText(Main_TheLat_GV.this, txtCauHoi,Toast.LENGTH_SHORT).show();

    }
}