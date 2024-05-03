package com.example.edusuport.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
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
        TextView txvTenGV = findViewById(R.id.txvTenGV);
        txvTenGV.setText("Giáo viên: " + giaoVien.getTenGiaoVien());
        getForm();
        getData();
        AddEvents();
    }
    private void getData() {
        ListCN.add(new ChucNang("TKBGV","Chỉnh sửa thời khóa biểu"));
        ListCN.add(new ChucNang("TBGV","Gửi thông báo"));
        ListCN.add(new ChucNang("GYGV","Xem góp ý phụ huynh"));
        ListCN.add(new ChucNang("DXNGV","Xem đơn xin nghỉ học"));
        ListCN.add(new ChucNang("TLHTGV","Đăng tải tài liệu học tập"));
        ListCN.add(new ChucNang("TLGV","Đăng thẻ lật"));
        ListCN.add(new ChucNang("NDGV","Nhập điểm"));
        ListCN.add(new ChucNang("DGHSGV","Đánh giá học sinh"));
        chucNangHomeAdapter=new ChucNangHomeAdapter(Home.this,R.layout.icon_tailieu_gv,ListCN);
        gvChucNang.setAdapter(chucNangHomeAdapter);
    }
    public void AddEvents(){
        ImageButton btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
                builder.setTitle("Xác nhận đăng xuất");
                builder.setMessage("Bạn có chắc chắn muốn đăng xuất không?");

                builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Home.giaoVien = new GiaoVien();
                        Intent intent=new Intent(Home.this, Login.class);
                        startActivity(intent);
                    }
                });

                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss(); // Đóng hộp thoại khi người dùng chọn hủy
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
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