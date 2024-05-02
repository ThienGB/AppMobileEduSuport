package com.example.edusuport.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.edusuport.R;
import com.example.edusuport.model.HocSinh;
import com.example.edusuport.model.PhuHuynh;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;

public class ThemHocSinhActivity extends AppCompatActivity {

    EditText edtHoTen, edtmssv;
    Button btnXacNhan;
    DatabaseReference hocsinhRef, phuhuynhRef;

    ImageButton btnBackHocSinh;
    TextView txvTenGV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_hoc_sinh);

        hocsinhRef = FirebaseDatabase.getInstance().getReference("hocsinh");
        phuhuynhRef = FirebaseDatabase.getInstance().getReference("phuhuynh");

        // Ánh xạ các thành phần từ layout
        edtHoTen = findViewById(R.id.add_hocsinh);
        edtmssv = findViewById(R.id.add_id);
        btnXacNhan = findViewById(R.id.btnXacNhanHs);
        btnBackHocSinh = findViewById(R.id.btnBackHocSinh);

        // Xử lý sự kiện khi nhấn nút Xác nhận
        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy thông tin từ các EditText
                String hoTen = edtHoTen.getText().toString().trim();
                String mssv = edtmssv.getText().toString().trim();
                String idlophoc = "12A12";// gán id lop hoc, cần sửa lại
                String matkhau = "123456";

                // Kiểm tra xem các trường thông tin có được nhập đầy đủ không
                if (hoTen.isEmpty() || mssv.isEmpty()) {
                    Toast.makeText(ThemHocSinhActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    // Truy vấn Firebase Realtime Database để kiểm tra mã số đã tồn tại hay chưa
                    hocsinhRef.child(mssv).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                // Mã số đã tồn tại, hiển thị thông báo
                                Toast.makeText(ThemHocSinhActivity.this, "Mã số đã tồn tại", Toast.LENGTH_SHORT).show();
                            } else {
                                new AlertDialog.Builder(ThemHocSinhActivity.this)
                                        .setTitle("Xác nhận")
                                        .setMessage("Bạn có chắc chắn muốn thêm học sinh này không?")
                                        .setNegativeButton("Xác nhận", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                // Mã số chưa tồn tại, thêm học sinh mới vào Firebase Realtime Database
                                                HocSinh hocSinh = new HocSinh(hoTen, mssv, idlophoc, matkhau);
                                                hocsinhRef.child(mssv).setValue(hocSinh, new DatabaseReference.CompletionListener() {
                                                    @Override
                                                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                                        if (error != null) {
                                                            Toast.makeText(ThemHocSinhActivity.this, "Thêm học sinh thất bại: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                                                        } else {
                                                            // Tạo một đối tượng PhuHuynh mới
                                                            PhuHuynh phuHuynh = new PhuHuynh(mssv, "Phụ huynh của " + hoTen, idlophoc, matkhau);
                                                            // Thêm phụ huynh vào Firebase Database
                                                            phuhuynhRef.child(mssv + "PH").setValue(phuHuynh);
                                                            Toast.makeText(ThemHocSinhActivity.this, "Thêm học sinh thành công", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                            }
                                        })
                                        .setPositiveButton("Hủy bỏ", null)
                                        .show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(ThemHocSinhActivity.this, "Đã xảy ra lỗi: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        btnBackHocSinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BackHocSinh();
            }
        });

    }
    public void BackHocSinh(){
        Intent intent = new Intent(ThemHocSinhActivity.this, DanhSachHocSinh.class);
        startActivity(intent);
    }
}