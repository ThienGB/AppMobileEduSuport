package com.example.edusuport.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.edusuport.R;
import com.example.edusuport.model.NhanXet;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NhanXetCaNhanActivity extends AppCompatActivity {

    DatabaseReference nhanXetRef;
    Spinner spinnerDanhGia;
    Button btnXacNhan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhan_xet_ca_nhan);

        nhanXetRef = FirebaseDatabase.getInstance().getReference("nhanxet");

        spinnerDanhGia = findViewById(R.id.spinnerDanhGia);
        btnXacNhan = findViewById(R.id.btnXacNhan);

        // Tạo dữ liệu cho Spinner
        String[] danhGiaArray = {"Tốt", "Kém"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, danhGiaArray);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDanhGia.setAdapter(spinnerAdapter);

        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                luuNhanXet();
            }
        });
    }

    private void luuNhanXet() {
        // Lấy thông tin từ Spinner và TextView
        String danhGia = spinnerDanhGia.getSelectedItem().toString();
        String maSoHocSinh = "110201"; // Thay thế bằng mã số học sinh thực tế
        String noiDungNhanXet = "Làm bài tập đầy đủ! Có cố gắng trong học tập!"; // Thay thế bằng lời nhận xét thực tế

        // Lưu vào Firebase Database
        NhanXet nhanXet = new NhanXet(noiDungNhanXet, danhGia);
        nhanXetRef.child(maSoHocSinh).setValue(nhanXet, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@NonNull DatabaseError error, @NonNull DatabaseReference ref) {
                if (error != null) {
                    Toast.makeText(NhanXetCaNhanActivity.this, "Lưu nhận xét thất bại: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(NhanXetCaNhanActivity.this, "Lưu nhận xét thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
