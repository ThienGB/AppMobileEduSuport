package com.example.edusuport.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.edusuport.DBHelper.DBHelper;
import com.example.edusuport.R;
import com.example.edusuport.databinding.ActivityNhanXetCaNhanBinding;
import com.example.edusuport.databinding.ActivityNhanXetChungBinding;
import com.example.edusuport.model.GiaoVien;
import com.example.edusuport.model.HocSinh;
import com.example.edusuport.model.NhanXet;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class NhanXetCaNhanActivity extends AppCompatActivity {
    DBHelper dbHelper;
    ActivityNhanXetCaNhanBinding binding;
    private GiaoVien giaoVien = Home.giaoVien;
    private HocSinh hocSinh;
    String DanhGia ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNhanXetCaNhanBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        dbHelper = new DBHelper();
        Intent intent = getIntent();
        hocSinh = (HocSinh) intent.getSerializableExtra("hocSinh");
        binding.txvTenHS.setText("Học sinh: " + hocSinh.getTen());
        binding.txvMaHS.setText("Mã số học sinh: "+ hocSinh.getMSHS());
        AddEvents();
    }
    public void AddEvents(){
        binding.edtNhanXet.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    v.setBackgroundResource(R.drawable.edittext_boder_click);
                } else {
                    v.setBackgroundResource(R.drawable.edittext_border);
                }
            }
        });
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Back();
            }
        });
        binding.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LuuNhanXet();
            }
        });
        binding.ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (fromUser) { // Đảm bảo rằng sự kiện chỉ xảy ra khi người dùng thực sự thay đổi đánh giá
                    if (rating == 1.0) {
                        binding.txvDanhGia.setText("Đánh giá: Yếu");
                        DanhGia = "Yếu";
                    } else if (rating == 2.0) {
                        binding.txvDanhGia.setText("Đánh giá: Trung bình");
                        DanhGia = "Trung bình";
                    } else if (rating == 3.0) {
                        binding.txvDanhGia.setText("Đánh giá: Khá");
                        DanhGia = "Khá";
                    } else if (rating == 4.0) {
                        binding.txvDanhGia.setText("Đánh giá: Giỏi");
                        DanhGia = "Giỏi";
                    } else if (rating == 5.0) {
                        binding.txvDanhGia.setText("Đánh giá: Xuất sắc");
                        DanhGia = "Xuất sắc";
                    }
                }
            }
        });

    }

    private void LuuNhanXet() {
        if (DanhGia.equals("")){
            Toast.makeText(NhanXetCaNhanActivity.this, "Vui lòng đánh giá sinh viên!!", Toast.LENGTH_SHORT).show();
            return;
        }
        String NhanXet = binding.edtNhanXet.getText().toString();
        if (NhanXet.equals("")){
            Toast.makeText(NhanXetCaNhanActivity.this, "Vui lòng nhập nhận xét sinh viên!!", Toast.LENGTH_SHORT).show();
            return;
        }
        String IDNhanXet = UUID.randomUUID().toString();
        NhanXet nhanXet = new NhanXet(IDNhanXet, giaoVien.getIDGiaoVien(), hocSinh.getMSHS(), NhanXet, DanhGia);
        Map<String, Object> updates = new HashMap<>();
        updates.put(dbHelper.FieldIDGiaoVien, giaoVien.getIDGiaoVien());
        updates.put(dbHelper.FieldMSHS, hocSinh.getMSHS());
        updates.put(dbHelper.FieldNoiDung, NhanXet);
        updates.put(dbHelper.FieldDanhGia, DanhGia);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference nhanXetRef = database.getReference(dbHelper.ColecNhanXet);
        nhanXetRef.child(IDNhanXet).updateChildren(updates);
        Toast.makeText(NhanXetCaNhanActivity.this, "Đánh giá thành công!!", Toast.LENGTH_SHORT).show();
        Back();

    }
    public void Back(){
        super.onBackPressed();
    }
}
