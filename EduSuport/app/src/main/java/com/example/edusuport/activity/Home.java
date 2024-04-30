package com.example.edusuport.activity;

import android.os.Bundle;
import android.widget.GridView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.edusuport.R;
import com.example.edusuport.adapter.ChucNangHomeAdapter;
import com.example.edusuport.model.ChucNang;

import java.util.ArrayList;

public class Home extends AppCompatActivity {

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
        ListCN.add(new ChucNang("TKB","Chỉnh sửa thời khóa biểu"));
        ListCN.add(new ChucNang("TB","Gửi thông báo"));
        ListCN.add(new ChucNang("GY","Xem góp ý phụ huynh"));
        ListCN.add(new ChucNang("DXN","Xem đơn xin nghỉ học"));
        ListCN.add(new ChucNang("TLHT","Đăng tải tài liệu học tập"));
        ListCN.add(new ChucNang("CD","Cho điểm"));

        chucNangHomeAdapter=new ChucNangHomeAdapter(Home.this,R.layout.icon_tailieu_gv,ListCN);
        gvChucNang.setAdapter(chucNangHomeAdapter);
    }

    private void getForm() {
        gvChucNang=findViewById(R.id.grid_ChucNang);
    }
}