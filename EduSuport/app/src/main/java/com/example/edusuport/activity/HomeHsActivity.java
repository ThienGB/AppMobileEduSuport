package com.example.edusuport.activity;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.edusuport.R;
import com.example.edusuport.adapter.ChucNangHomeAdapter;
import com.example.edusuport.model.ChucNang;
import com.example.edusuport.model.HocSinh;

import java.util.ArrayList;

public class HomeHsActivity extends AppCompatActivity {
    public static HocSinh hocSinh = new HocSinh("21110499", "Lê Quang Lâm", "12B8");
    GridView gvChucNang;
    ArrayList<ChucNang> ListCN=new ArrayList<>();
    ChucNangHomeAdapter chucNangHomeAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_ph);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainph), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getForm();
        getData();
    }
    private void getData() {
        ListCN.add(new ChucNang("XTKBHS","Thời khóa biểu"));
        ListCN.add(new ChucNang("XTBHS","Xem thông báo"));
        ListCN.add(new ChucNang("XTLHS","Tài liệu"));
        ListCN.add(new ChucNang("XDHS","Xem điểm"));
        ListCN.add(new ChucNang("XNHHS","Xem nhận xét"));
        chucNangHomeAdapter=new ChucNangHomeAdapter(HomeHsActivity.this,R.layout.icon_tailieu_gv,ListCN);
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