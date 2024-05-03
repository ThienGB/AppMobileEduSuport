package com.example.edusuport.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.edusuport.DBHelper.DBHelper;
import com.example.edusuport.model.GiaoVien;
import com.example.edusuport.model.ThongBao;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.ValueEventListener;
import com.example.edusuport.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

public class GuiThongBaoActivity extends AppCompatActivity {

    EditText edtTenLop, edtTieuDe, edtNoiDung;
    ImageButton btnXacNhan;
    DatabaseReference lophocRef, thongbaoRef;
    private GiaoVien giaoVien = Home.giaoVien;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gui_thong_bao);

// Ánh xạ các thành phần từ layout
        edtTenLop = findViewById(R.id.tenLop);
        edtTieuDe = findViewById(R.id.tieudethongbao);
        edtNoiDung = findViewById(R.id.textInputEditText);
        btnXacNhan = findViewById(R.id.btnXacnhangui);

        lophocRef = FirebaseDatabase.getInstance().getReference("lophoc");
        thongbaoRef = FirebaseDatabase.getInstance().getReference("thongbao");

        // Xử lý sự kiện khi nhấn nút Xác nhận
        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy thông tin từ các EditText
                String tenLop = edtTenLop.getText().toString().trim();
                String tieuDe = edtTieuDe.getText().toString().trim();
                String noiDung = edtNoiDung.getText().toString().trim();
                String idGiaoVien = "1"; //gán ở login

                // Kiểm tra xem các trường thông tin có được nhập đầy đủ không
                if (tenLop.isEmpty() || tieuDe.isEmpty() || noiDung.isEmpty()) {
                    Toast.makeText(GuiThongBaoActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    // Xác nhận idLop có tồn tại trong Firebase
                    lophocRef.child(tenLop).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                // `idLop` tồn tại trong Firebase
                                // Gửi thông báo đến lớp
                                sendNotificationToClass(tieuDe, noiDung, idGiaoVien, tenLop);
                            } else {
                                // `idLop` không tồn tại trong Firebase
                                Toast.makeText(GuiThongBaoActivity.this, "Lớp không tồn tại", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            // Xử lý khi truy vấn bị hủy bỏ
                            Toast.makeText(GuiThongBaoActivity.this, "Truy vấn bị hủy bỏ", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
    // Hàm gửi thông báo đến lớp
    private void sendNotificationToClass(String tieuDe, String noiDung, String idGiaoVien, String tenLop) {
        // Tạo một đối tượng ThongBao mới
        DBHelper dbHelper = new DBHelper();
     //   ThongBao thongBao = new ThongBao(tieuDe, noiDung, idGiaoVien, tenLop, getCurrentDate());

        Map<String, Object> updates = new HashMap<>();
        String IDThongBao = UUID.randomUUID().toString();
        long thoigian = System.currentTimeMillis();
        updates.put(dbHelper.FieldIDNguoiGui, giaoVien.getIDGiaoVien());
        updates.put(dbHelper.FieldIDNguoiNhan, giaoVien.getIDGiaoVien());
//        updates.put(dbHelper.FieldIDLopHoc, IDLop);
//        updates.put(dbHelper.FieldTiet, tiet);
//        updates.put(dbHelper.FieldThu, thu);

          //  myRef.child(key).updateChildren(updates);
//        thongbaoRef.child(IDThongBao).setValue(thongBao, new DatabaseReference.CompletionListener() {
//            @Override
//            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
//                if (error != null) {
//                    Toast.makeText(GuiThongBaoActivity.this, "Gửi thông báo thất bại: " + error.getMessage(), Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(GuiThongBaoActivity.this, "Gửi thông báo thành công", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
    }

    // Hàm để lấy ngày hiện tại
    private String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return sdf.format(new Date());
    }

}