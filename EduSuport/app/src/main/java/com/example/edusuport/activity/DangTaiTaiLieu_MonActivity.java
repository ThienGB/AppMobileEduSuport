package com.example.edusuport.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.edusuport.R;
import com.example.edusuport.adapter.NhomTheAdapter;
import com.example.edusuport.adapter.TaiLieuHocTapAdapter;
import com.example.edusuport.model.NhomThe;
import com.example.edusuport.model.TaiLieuHocTap;

import java.util.ArrayList;

public class DangTaiTaiLieu_MonActivity extends AppCompatActivity {

    ListView lvFile;
    GridView gdCard;
    ImageButton imgBack;
    TaiLieuHocTapAdapter taiLieuHocTapAdapter;
    NhomTheAdapter nhomTheAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_tai_tai_lieu_mon);
        loadTabs();
        doFormWidgetsFile();
        doFormWidgetsFlasCard();
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
        //Lấy Tabhost id ra trước /
        final TabHost tab =(TabHost) findViewById(R.id.tabHost_tailieu);
//gọi lệnh setup
        tab.setup();
        TabHost.TabSpec spec;
//Tạo tabl
        spec= tab.newTabSpec("t1");
        spec.setContent(R.id.tab1);
        spec.setIndicator("File");
        tab.addTab(spec);
//Tạo tab2
        spec= tab.newTabSpec("t2");
        spec.setContent(R.id.tab2);
        spec.setIndicator ("FlashCard");
        tab.addTab(spec);
// Thiết lập tạo mặc định được chọn ban đầu là tab B
        tab.setCurrentTab(0);
//Ví dụ tabl chưa nhập thông tin xong mà lại qua tab 2 thì bảo...
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
        ArrayList<TaiLieuHocTap> list=new ArrayList<TaiLieuHocTap>();
        list.add(new TaiLieuHocTap( "Tài liệu học tập pro vip","24/03/2003"));
        list.add(new TaiLieuHocTap( "Cách múa flo mượt","2/03/2003"));
        list.add(new TaiLieuHocTap( "Hhuhu hix","24/03/2003"));
        list.add(new TaiLieuHocTap( "Tài liệu học tập pro vip","24/03/2003"));
        list.add(new TaiLieuHocTap( "Tài liệu học tập pro vip","24/03/2003"));


        taiLieuHocTapAdapter = new TaiLieuHocTapAdapter(DangTaiTaiLieu_MonActivity.this,R.layout.item_tab_listtailieu, list );
        lvFile.setAdapter(taiLieuHocTapAdapter);

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