package com.example.edusuport.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.edusuport.R;
import com.example.edusuport.model.LopHoc;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ThemLopHocActivity extends AppCompatActivity {

    EditText edtMaLop, edtTenLop, edtSoLuong;
    Button btnXacNhan;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_lop_hoc);
        databaseReference = FirebaseDatabase.getInstance().getReference("lophoc");

        // Ánh xạ các thành phần từ layout
        edtMaLop = findViewById(R.id.edtMaLop);
        edtTenLop = findViewById(R.id.edtTenLop);
        edtSoLuong = findViewById(R.id.edtSoLuong);
        btnXacNhan = findViewById(R.id.btnXacNhan);

        // Xử lý sự kiện khi nhấn nút Xác nhận
        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy thông tin từ các EditText
                String maLop = edtMaLop.getText().toString().trim();
                String tenLop = edtTenLop.getText().toString().trim();
                String soLuong = edtSoLuong.getText().toString().trim();

                // Kiểm tra xem các trường thông tin có được nhập đầy đủ không
                if (maLop.isEmpty() || tenLop.isEmpty() || soLuong.isEmpty()) {
                    Toast.makeText(ThemLopHocActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    // Tạo một đối tượng LopHoc mới
                    LopHoc lopHoc = new LopHoc(maLop, tenLop, soLuong);

                    // Thêm lớp học vào Firebase Database
                    databaseReference.child(maLop).setValue(lopHoc, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@NonNull DatabaseError error, @NonNull DatabaseReference ref) {
                            if (error != null) {
                                Toast.makeText(ThemLopHocActivity.this, "Thêm lớp học thất bại: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ThemLopHocActivity.this, "Thêm lớp học thành công", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}