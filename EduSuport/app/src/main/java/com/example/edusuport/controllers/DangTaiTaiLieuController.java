package com.example.edusuport.controllers;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.ActionMode;

import androidx.annotation.NonNull;

import com.example.edusuport.model.MonHoc;
import com.example.edusuport.model.TaiLieuHocTap;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class DangTaiTaiLieuController {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    StorageReference myRefStora= FirebaseStorage.getInstance().getReference();



    ArrayList<MonHoc> list=new ArrayList<MonHoc>();


    public interface DataRetrievedCallback_MonHoc {
        void onDataRetrieved(ArrayList<MonHoc> monHocList);
    }
    public void setGridiewMonHoc( DataRetrievedCallback_MonHoc callback) {
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
                        String tenMonHoc = dataSnapshot.child("tenmon").getValue(String.class); // Lấy tên môn học từ giá trị

                        list.add(new MonHoc(idMonHoc, tenMonHoc));

                    }
                    // Gọi callback với danh sách dữ liệu đã lấy được
                    callback.onDataRetrieved(list);

                }

            }
        });
    }

    public interface DataRetrievedCallback_String {
        void onDataRetrieved(String tenmon);
    }

    public void getMonHoc_idmon(String idmon,DataRetrievedCallback_String callback) {

        myRef.child("monhoc").orderByKey().equalTo(idmon).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    DataSnapshot dataSnapshot = task.getResult().getChildren().iterator().next();
                     String tenmon = dataSnapshot.child("tenmon").getValue(String.class);
                     callback.onDataRetrieved(tenmon);
                }


            }
        });

    }


    public interface DataRetrievedCallback_File {
        void onDataRetrieved(ArrayList<MonHoc> monHocList);
    }
    public void getListTaiLieu_idmon(String idmon){
        myRef.child("tailieuFile");
    }
    public void createNewFileTaiLieu_idmon(String idmon, String tenTaiLieu, Uri data){
        StorageReference reference=myRefStora.child("tailieuFile/"+System.currentTimeMillis()+".pdf");
        reference.putFile(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask= taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete());
                Uri url=uriTask.getResult();
                Log.d("url", url.toString());


                TaiLieuHocTap taiLieuHocTap=new TaiLieuHocTap(idmon,tenTaiLieu,url.toString(), ServerValue.TIMESTAMP);
                myRef.child("taiLieuFile").child(myRef.push().getKey()).setValue(taiLieuHocTap);
            }
        });
    }
}
