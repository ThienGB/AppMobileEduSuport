package com.example.edusuport.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.example.edusuport.R;
import com.example.edusuport.adapter.MonHocAdapter;
import com.example.edusuport.adapter.NhomTheAdapter;
import com.example.edusuport.adapter.TaiLieuHocTapAdapter;
import com.example.edusuport.controllers.DangTaiTaiLieuController;
import com.example.edusuport.model.MonHoc;
import com.example.edusuport.model.NhomThe;
import com.example.edusuport.model.TaiLieuHocTap;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class DangTaiTaiLieu_MonActivity extends AppCompatActivity {

    TextView textTenMonHoc;
    ListView lvFile;
    GridView gdCard;
    ImageButton imgBack;
    FloatingActionButton btnuploadfileTL,btnAddFlashCard;
    TaiLieuHocTapAdapter taiLieuHocTapAdapter;
    NhomTheAdapter nhomTheAdapter;
    String idMon, tenFile;
    DangTaiTaiLieuController dangTaiTaiLieuController=new DangTaiTaiLieuController();;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_tai_tai_lieu_mon);
        loadTabs();
        doFormWidgetsFile();
        doFormWidgetsFlasCard();
        clickBack();
    }

    private void clickBack(){
        imgBack=(ImageButton) findViewById(R.id.back_TaiLieuMain);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DangTaiTaiLieu_MonActivity.this,DangTaiTaiLieuActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    public void loadTabs(){

        textTenMonHoc = (TextView) findViewById(R.id.textTenMonHoc);
        idMon=getIntent().getStringExtra("idMon");
        dangTaiTaiLieuController.getMonHoc_idmon(idMon, new DangTaiTaiLieuController.DataRetrievedCallback_String() {
            @Override
            public void onDataRetrieved(String tenmon) {
                textTenMonHoc.setText("Môn học: "+tenmon);
            }
        });

        Log.d("idMon", String.valueOf(idMon));
        //Lấy Tabhost id ra trước /
        final TabHost tab =(TabHost) findViewById(R.id.tabHost_tailieu);
        tab.setup();
        TabHost.TabSpec spec;
        spec= tab.newTabSpec("t1");
        spec.setContent(R.id.tab1);
        spec.setIndicator("File");
        tab.addTab(spec);
        spec= tab.newTabSpec("t2");
        spec.setContent(R.id.tab2);
        spec.setIndicator ("FlashCard");
        tab.addTab(spec);
        tab.setCurrentTab(0);
        tab.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String arg0) {
                String s = "Tab tag="+arg0+"; index=" + tab.getCurrentTab();
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void doFormWidgetsFile()
    {
        lvFile=(ListView) findViewById(R.id.lv_fileTaiLieu);
        btnuploadfileTL=(FloatingActionButton) findViewById(R.id.btn_uploadfileTL);

        btnuploadfileTL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.app.AlertDialog.Builder builder = new AlertDialog.Builder(DangTaiTaiLieu_MonActivity.this);
                LayoutInflater layoutInflater = LayoutInflater.from(DangTaiTaiLieu_MonActivity.this);
                builder.setTitle("Thêm tài liệu: ");
                LayoutInflater inflater = getLayoutInflater();
                final View alertView = layoutInflater.inflate(R.layout.activity_click_upfile_gv, null);
                builder.setView(inflater.inflate(R.layout.activity_click_upfile_gv, null));
                EditText txtTenFile=(EditText) alertView.findViewById(R.id.txtTenFile);
                android.widget.LinearLayout upFile=(android.widget.LinearLayout) alertView.findViewById(R.id.btn_upNewfile);
                tenFile=txtTenFile.getText().toString();

                builder.setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent=new Intent();
                        intent.setType("application/pdf");
                        intent.setAction(intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent,"Select PDF File"),1);

                    }
                });


                builder.create().show();
            }
        });
        ArrayList<TaiLieuHocTap> list=new ArrayList<TaiLieuHocTap>();
//        list.add(new TaiLieuHocTap( "Tài liệu học tập pro vip","24/03/2003"));
//        list.add(new TaiLieuHocTap( "Cách múa flo mượt","2/03/2003"));
//        list.add(new TaiLieuHocTap( "Hhuhu hix","24/03/2003"));
//        list.add(new TaiLieuHocTap( "Tài liệu học tập pro vip","24/03/2003"));
//        list.add(new TaiLieuHocTap( "Tài liệu học tập pro vip","24/03/2003"));


        taiLieuHocTapAdapter = new TaiLieuHocTapAdapter(DangTaiTaiLieu_MonActivity.this,R.layout.item_tab_listtailieu, list );
        lvFile.setAdapter(taiLieuHocTapAdapter);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK && data!=null && data.getData()!=null ){
            final ProgressDialog progressDialog=new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            dangTaiTaiLieuController.createNewFileTaiLieu_idmon(idMon,tenFile,data.getData());
            progressDialog.dismiss();
        }
    }

    public void doFormWidgetsFlasCard()
    {
        gdCard=(GridView) findViewById(R.id.grid_flashcard);
        ArrayList<NhomThe> list=new ArrayList<NhomThe>();
        list.add(new NhomThe( "1","Thẻ vip","2"));
        list.add(new NhomThe( "2","300 bài code thiếu nhi","2"));
        list.add(new NhomThe( "3","Học soạn giáo án cùng cô giáo","2"));
        list.add(new NhomThe( "4","Kiếm tiền không khó","2"));
        list.add(new NhomThe( "5","Kiếm tiền khó","2"));
        list.add(new NhomThe( "6","Thẻ vip","2"));



        nhomTheAdapter = new NhomTheAdapter(DangTaiTaiLieu_MonActivity.this,R.layout.item_tab_flashcard, list );
        gdCard.setAdapter(nhomTheAdapter);

    }

}