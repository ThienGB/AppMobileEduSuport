package com.example.edusuport.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.edusuport.R;
import com.example.edusuport.model.HocSinh;
import com.example.edusuport.model.PhuHuynh;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ThemHocSinhActivity extends AppCompatActivity {

    EditText edtHoTen, edtMaSo;
    Button btnXacNhanhs;
    DatabaseReference hocsinhRef, phuhuynhRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_hoc_sinh);
        hocsinhRef = FirebaseDatabase.getInstance().getReference("hocsinh");
        phuhuynhRef = FirebaseDatabase.getInstance().getReference("phuhuynh");

        // Ánh xạ các thành phần từ layout
        edtHoTen = findViewById(R.id.add_hocsinh);
        edtMaSo = findViewById(R.id.add_id);
        btnXacNhanhs = findViewById(R.id.btnXacNhanHs);

        // Xử lý sự kiện khi nhấn nút Xác nhận
        btnXacNhanhs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy thông tin từ các EditText
                String maSo = edtMaSo.getText().toString().trim();
                String hoTen = edtHoTen.getText().toString().trim();

                // Kiểm tra xem các trường thông tin có được nhập đầy đủ không
                if (hoTen.isEmpty() || maSo.isEmpty()) {
                    Toast.makeText(ThemHocSinhActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    // Tạo một đối tượng HocSinh mới
                    HocSinh hocSinh = new HocSinh(hoTen, maSo);
                    // Thêm học sinh vào Firebase Database
                    hocsinhRef.child(maSo).setValue(hocSinh, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@NonNull DatabaseError error, @NonNull DatabaseReference ref) {
                            if (error != null) {
                                Toast.makeText(ThemHocSinhActivity.this, "Thêm học sinh thất bại: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                            } else {
                                // Tạo một đối tượng PhuHuynh mới
                                PhuHuynh phuHuynh = new PhuHuynh(maSo + "ph", "Phụ huynh của " + hoTen);
                                // Thêm phụ huynh vào Firebase Database
                                phuhuynhRef.child(maSo + "ph").setValue(phuHuynh);
                                Toast.makeText(ThemHocSinhActivity.this, "Thêm học sinh thành công", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}