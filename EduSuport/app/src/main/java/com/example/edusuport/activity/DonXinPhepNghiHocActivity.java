package com.example.edusuport.activity;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.edusuport.DBHelper.DBHelper;
import com.example.edusuport.R;
import com.example.edusuport.databinding.ActivityDonXinPhepNghiHocBinding;
import com.example.edusuport.databinding.ActivityXemThoiKhoaBieuBinding;
import com.example.edusuport.model.DonXinNghiHoc;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class DonXinPhepNghiHocActivity extends AppCompatActivity {

    DBHelper dbHelper;
    ActivityDonXinPhepNghiHocBinding binding;
    DonXinNghiHoc donXinNghiHoc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDonXinPhepNghiHocBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dbHelper = new DBHelper();
        Intent intent = getIntent();
        donXinNghiHoc = (DonXinNghiHoc) intent.getSerializableExtra("donXinNghiHoc");

        binding.txvLyDo.setText(donXinNghiHoc.getLyDo());
        binding.txvThoiGian.setText(donXinNghiHoc.getThoiGian().toString());
        dbHelper.getTenHocSinhByMSHS(donXinNghiHoc.getMSHS(), new DBHelper.TenHocSinhCallback() {
            @Override
            public void onTenHocSinhFetched(String tenHocSinh) {
                if (tenHocSinh != null) {
                    binding.txvTenNguoiGui.setText(tenHocSinh);
                } else {
                    // Xử lý khi không lấy được tên học sinh
                }
            }
        });
        AddEvents();
    }
    public void AddEvents(){
        binding.btnbackDuyetDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Back();
            }
        });
        binding.btnDongYDuyet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDonXinNghiHoc(donXinNghiHoc.getMaDon(), dbHelper.ValueTTDaDuyet);
                Back();
            }
        });
        binding.btnTuChoiDuyet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDonXinNghiHoc(donXinNghiHoc.getMaDon(), dbHelper.ValueTTTuChoi);
                Back();
            }
        });
    }
    public void updateDonXinNghiHoc(String donId, String newStatus) {
        DatabaseReference donRef = FirebaseDatabase.getInstance().getReference(dbHelper.ColecDonXinNghiHoc).child(donId);
        Map<String, Object> updateData = new HashMap<>();
        updateData.put(dbHelper.FieldTrangThai, newStatus);
        donRef.updateChildren(updateData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(), "Duyệt đơn thành công", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Duyệt đơn thất bại, có lỗi", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    public void Back(){
        Intent intent = new Intent(DonXinPhepNghiHocActivity.this, DuyetDonXinNghiHocActivity.class);
        startActivity(intent);
    }
}