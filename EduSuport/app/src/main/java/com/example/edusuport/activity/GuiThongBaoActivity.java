package com.example.edusuport.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.ValueEventListener;
import com.example.edusuport.R;

public class GuiThongBaoActivity extends AppCompatActivity {

    private EditText edtStudentId, edtMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gui_thong_bao);

        // Khởi tạo Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference studentsRef = database.getReference("guithongbao");

        // Ánh xạ các view
        edtStudentId = findViewById(R.id.mahocsinh);
        edtMessage = findViewById(R.id.textInputEditText);
        ImageButton btnSend = findViewById(R.id.btnXacnhangui);

        // Xử lý sự kiện khi nhấn nút gửi
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String studentId = edtStudentId.getText().toString().trim();
                String message = edtMessage.getText().toString().trim();

                if (!studentId.isEmpty() && !message.isEmpty()) {
                    // Tìm học sinh trong Firebase theo mã học sinh
                    Query query = studentsRef.orderByChild("id").equalTo(studentId);
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                // Gửi thông báo đến học sinh
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    Student student = snapshot.getValue(Student.class);
                                    if (student != null) {
                                        sendNotification(student.getId(), message);
                                    }
                                }
                                // Xóa dữ liệu trong các ô nhập
                                edtStudentId.setText("");
                                edtMessage.setText("");
                            } else {
                                // Hiển thị thông báo nếu không tìm thấy học sinh
                                // (Có thể thêm Toast hoặc TextView để hiển thị)
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            // Xử lý lỗi nếu có
                        }
                    });
                }
            }
        });

    }
}