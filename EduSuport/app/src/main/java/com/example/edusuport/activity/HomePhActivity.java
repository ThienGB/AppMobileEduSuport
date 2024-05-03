package com.example.edusuport.activity;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.edusuport.R;
import com.example.edusuport.adapter.ChucNangHomeAdapter;
import com.example.edusuport.model.ChucNang;
import com.example.edusuport.model.GiaoVien;
import com.example.edusuport.model.PhuHuynh;

import java.util.ArrayList;

public class HomePhActivity extends AppCompatActivity {
   // public static PhuHuynh phuHuynh = new PhuHuynh("21110499PH", "Lê Quang", "12B8", "21110499");
    public static PhuHuynh phuHuynh;
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
        TextView txvTenGV = findViewById(R.id.txvTenPH);
        txvTenGV.setText(phuHuynh.getTen());
        getForm();
        getData();
        AddEvents();
    }
    private void getData() {
        ListCN.add(new ChucNang("DXPPH","Đơn xin nghỉ học"));
        ListCN.add(new ChucNang("GYPH","Góp ý giáo viên"));
        ListCN.add(new ChucNang("TBPH","Xem thông báo"));
        chucNangHomeAdapter=new ChucNangHomeAdapter(HomePhActivity.this,R.layout.icon_tailieu_gv,ListCN);
        gvChucNang.setAdapter(chucNangHomeAdapter);
    }
    public void AddEvents(){
        ImageButton btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(HomePhActivity.this);
                builder.setTitle("Xác nhận đăng xuất");
                builder.setMessage("Bạn có chắc chắn muốn đăng xuất không?");

                builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        HomePhActivity.phuHuynh = new PhuHuynh();
                        Intent intent=new Intent(HomePhActivity.this, Login.class);
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