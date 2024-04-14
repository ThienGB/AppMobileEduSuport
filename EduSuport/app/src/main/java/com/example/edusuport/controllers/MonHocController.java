package com.example.edusuport.controllers;

import android.util.Log;
import android.widget.GridView;

import androidx.annotation.NonNull;

import com.example.edusuport.R;
import com.example.edusuport.activity.DangTaiTaiLieuActivity;
import com.example.edusuport.adapter.MonHocAdapter;
import com.example.edusuport.model.MonHoc;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MonHocController {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    ArrayList<MonHoc> list=new ArrayList<MonHoc>();
    public interface DataRetrievedCallback {
        void onDataRetrieved(ArrayList<MonHoc> monHocList);
    }
    public void setGridiewMonHoc( DataRetrievedCallback callback) {
        myRef.child("monhoc").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                    for (DataSnapshot dataSnapshot : task.getResult().getChildren()) {
                        String idMonHoc = dataSnapshot.getKey(); // Lấy ID của môn học từ khóa
                        String tenMonHoc = dataSnapshot.child("tenMon").getValue(String.class); // Lấy tên môn học từ giá trị

                        list.add(new MonHoc(idMonHoc, tenMonHoc));

                    }
                    // Gọi callback với danh sách dữ liệu đã lấy được
                    callback.onDataRetrieved(list);

                }

            }
        });
    }
}
