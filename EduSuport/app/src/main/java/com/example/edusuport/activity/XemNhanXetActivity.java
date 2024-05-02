package com.example.edusuport.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;

import com.example.edusuport.DBHelper.DBHelper;
import com.example.edusuport.R;
import com.example.edusuport.adapter.ThongBaoAdapter;
import com.example.edusuport.databinding.ActivityHopThuGopYBinding;
import com.example.edusuport.databinding.ActivityXemNhanXetBinding;
import com.example.edusuport.model.HocSinh;
import com.example.edusuport.model.NhanXet;
import com.example.edusuport.model.PhuHuynh;
import com.example.edusuport.model.ThongBao;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class XemNhanXetActivity extends AppCompatActivity {
    DBHelper dbHelper;
    ActivityXemNhanXetBinding binding;
    private HocSinh hocSinh = HomeHsActivity.hocSinh;
    private PhuHuynh phuHuynh = HomePhActivity.phuHuynh;
    NhanXet nhanXet = new NhanXet();
    String role;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityXemNhanXetBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        dbHelper = new DBHelper();
        Intent intent = getIntent();
        role = (String) intent.getSerializableExtra("role");
        String mshs = "";
        if (role.equals("hocsinh")){
            mshs = hocSinh.getMSHS();
        }else {
            mshs = phuHuynh.getMSHS();
        }

        GetNhanXet(mshs);
        AddEvents();
    }
    public void GetNhanXet(String mshs){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(dbHelper.ColecNhanXet);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                nhanXet = new NhanXet();
                for (DataSnapshot thuSnapshot : dataSnapshot.getChildren()) {
                    String Mshs = thuSnapshot.child(dbHelper.FieldMSHS).getValue(String.class);
                    if (Mshs.equals(mshs))
                    {
                        String IDThongBao = thuSnapshot.getKey();
                        String IDNguoiGui = thuSnapshot.child(dbHelper.FieldIDGiaoVien).getValue(String.class);
                        String NoiDung = thuSnapshot.child(dbHelper.FieldNoiDung).getValue(String.class);
                     //   nhanXet = new NhanXet(IDThongBao, IDNguoiGui, idNguoiNhan, NoiDung, thoigian);
                    }
                }
                SetData(nhanXet);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
    public void SetData(NhanXet nhanXet){
        binding.txvTenHS.setText("Học sinh: "+ hocSinh.getTen());
        binding.txvNoiDungNX.setText("Học sinh: "+ nhanXet.getNoiDung());
        binding.txvDanhGia.setText("Học sinh: "+ nhanXet.getDanhGia());
//        dbHelper.getTenGiaoVienByID(nhanXet.getIDGiaoVien(), new DBHelper.TenHocSinhCallback() {
//            @Override
//            public void onTenHocSinhFetched(String tenPhuHuynh) {
//                if (tenPhuHuynh != null) {
//                    binding.txvTenNguoiNX.setText("Học sinh: "+ tenPhuHuynh);
//                } else {
//                }
//            }
//        });


    }
    public void AddEvents(){
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Back();
            }
        });

    }
    public void Back(){
        super.onBackPressed();
    }
}