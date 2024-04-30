package com.example.edusuport.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.edusuport.R;
import com.example.edusuport.adapter.ChucNangHomeAdapter;
import com.example.edusuport.model.ChucNang;
import com.example.edusuport.model.GiaoVien;

import java.util.ArrayList;

public class Home extends AppCompatActivity {
    public static GiaoVien giaoVien = new GiaoVien("123", "Nguyễn Hữu Thoại");
    GridView gvChucNang;
    ArrayList<ChucNang> ListCN=new ArrayList<>();
    ChucNangHomeAdapter chucNangHomeAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.form_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getForm();
        getData();
    }

    private void getData() {
        ListCN.add(new ChucNang("TKBGV","Chỉnh sửa thời khóa biểu"));
        ListCN.add(new ChucNang("TBGV","Gửi thông báo"));
        ListCN.add(new ChucNang("GYGV","Xem góp ý phụ huynh"));
        ListCN.add(new ChucNang("DXNGV","Xem đơn xin nghỉ học"));
        ListCN.add(new ChucNang("TLHTGV","Đăng tải tài liệu học tập"));
        ListCN.add(new ChucNang("NDGV","Nhập điểm"));
        ListCN.add(new ChucNang("DGHSGV","Đánh giá học sinh"));

        chucNangHomeAdapter=new ChucNangHomeAdapter(Home.this,R.layout.icon_tailieu_gv,ListCN);
        gvChucNang.setAdapter(chucNangHomeAdapter);
    }
    public void AddEvents(){
        gvChucNang.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    private void getForm() {
        gvChucNang=findViewById(R.id.grid_ChucNang);
    }
}