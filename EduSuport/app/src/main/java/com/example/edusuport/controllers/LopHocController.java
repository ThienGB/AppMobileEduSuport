package com.example.edusuport.controllers;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.edusuport.model.LopHoc;
import com.example.edusuport.model.MonHoc;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class LopHocController {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    StorageReference myRefStora= FirebaseStorage.getInstance().getReference();

    ArrayList<LopHoc> list=new ArrayList<LopHoc>();
    public interface DataRetrievedCallback_LopHoc {
        void onDataRetrieved(ArrayList<LopHoc> monHocList);
    }

    public void getListLopHoc_idGV(String idGV,DataRetrievedCallback_LopHoc callbackLopHoc){
        myRef.child("lophoc").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot lophocSnapshot : dataSnapshot.getChildren()) {
                    String idLop = lophocSnapshot.getKey();
                    String idGiaovien = lophocSnapshot.child("idGiaoVien").getValue(String.class);
                    if (idGiaovien != null && idGiaovien.equals(idGV)) {
                        String tenLop = lophocSnapshot.child("tenLopHoc").getValue(String.class);
                        long siSo = lophocSnapshot.child("soLuong").getValue(long.class);
                        Log.d("Giaos ien",String.valueOf(tenLop));
                        list.add(new LopHoc(idLop,idGV,tenLop, siSo));

                    }

                }

                callbackLopHoc.onDataRetrieved(list);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("nônnooono",String.valueOf(databaseError));
            }
        });
    }
}
