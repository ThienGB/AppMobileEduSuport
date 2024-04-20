package com.example.edusuport.controllers;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.ActionMode;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.edusuport.model.MonHoc;
import com.example.edusuport.model.TaiLieuHocTap;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DangTaiTaiLieuController {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    StorageReference myRefStora= FirebaseStorage.getInstance().getReference();



    ArrayList<MonHoc> list=new ArrayList<MonHoc>();
    ArrayList<TaiLieuHocTap> listFile=new ArrayList<TaiLieuHocTap>();
///(1)/////////////////////////////////////////////////////////////////////////////////////////

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
////(2)////////////////////////////////////////////////////////////////////////////////////////
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

    ////(3)////////////////////////////////////////////////////////////////////////////////////////
    public interface DataRetrievedCallback_File {
        void onDataRetrieved(ArrayList<TaiLieuHocTap> monHocList);
    }

    public void createNewFileTaiLieu_idmon (String idmon, String tenTaiLieu, Uri data,String extension,String idlop,UploadCallback callback){
        String idTaiLieu=myRef.push().getKey();

        StorageReference reference=myRefStora.child("tailieuFile/"+idTaiLieu+extension);
        reference.putFile(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                if(!taskSnapshot.getTask().isSuccessful()){

                }
                else{
                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                    while (!uriTask.isComplete()) ;
                    Uri url = uriTask.getResult();
                    TaiLieuHocTap taiLieuHocTap = new TaiLieuHocTap( idTaiLieu,idmon, tenTaiLieu, url.toString(),extension, ServerValue.TIMESTAMP,idlop);
                    myRef.child("taiLieuFile").child(idTaiLieu).setValue(taiLieuHocTap);
                    callback.onUploadComplete();
                   // Toast.makeText()
                }
                   }

        });
    }
    public interface UploadCallback {
        void onUploadComplete();
        void onUploadFailed(String errorMessage); // Optionally, handle upload failure
    }


    ////////(4)////////////////////////////////////////////////////////////////////////////////////

    public void getListViewTaiLieu(String idmon, String idlop,DataRetrievedCallback_File callback){
        myRef.child("taiLieuFile").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {

                    for (DataSnapshot dataSnapshot : task.getResult().getChildren()) {

                        String idTL = dataSnapshot.getKey(); // Lấy tên môn học từ giá trị
                        String idLop = dataSnapshot.child("idLop").getValue(String.class); // Lấy tên môn học từ giá trị
                        String idMon = dataSnapshot.child("idmon").getValue(String.class); // Lấy tên môn học từ giá trị
                        String tenTaiLieu = dataSnapshot.child("tenTaiLieu").getValue(String.class); // Lấy tên môn học từ giá trị
                        Long thoiGian = dataSnapshot.child("thoiGian").getValue(Long.class); // Lấy tên môn học từ giá trị
                        String fileType = dataSnapshot.child("fileType").getValue(String.class); // Lấy tên môn học từ giá trị
                        String urlfile = dataSnapshot.child("urlfile").getValue(String.class); // Lấy tên môn học từ giá trị

                        Log.d("list file", String.valueOf(thoiGian));


                       if(Objects.equals(idLop, idlop) && Objects.equals(idmon, idMon)){
                           Map<String, Object> timestampMap = convertTimestampToMap(thoiGian);
                           listFile.add(new TaiLieuHocTap(idTL,idmon, tenTaiLieu,urlfile,fileType,timestampMap,idLop));
                     }

                    }
                    // Gọi callback với danh sách dữ liệu đã lấy được
                    Log.d("list file", String.valueOf(listFile));
                    callback.onDataRetrieved(listFile);


                }

            }
        });
    }
    public static Map<String, Object> convertTimestampToMap(Long timestamp) {
        Map<String, Object> timestampMap = new HashMap<>();
        timestampMap.put("timestamp", timestamp);
        return timestampMap;
    }

    ////////(4)////////////////////////////////////////////////////////////////////////////////////

    public void getTaiLieu_idTaiLieu(String idTaiLieu, DataRetrievedCallback_String callback){
        myRef.child("taiLieuFile").orderByKey().equalTo(idTaiLieu).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    DataSnapshot dataSnapshot = task.getResult().getChildren().iterator().next();
                    String urlfile = dataSnapshot.child("urlfile").getValue(String.class);
                    callback.onDataRetrieved(urlfile);
                }


            }
        });
    }
    ////////(4)////////////////////////////////////////////////////////////////////////////////////
    public void deleteTaiLieu_idTaiLieu(String idTaiLieu, String fileType, UploadCallback callback, Context mContext){
        StorageReference reference=myRefStora.child("tailieuFile/"+idTaiLieu+fileType);
        myRef.child("taiLieuFile").child(idTaiLieu).removeValue();
        reference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                callback.onUploadComplete();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(mContext, "Xóa thất bại", Toast.LENGTH_SHORT).show();
            }
        });


    }
    ////////(4)////////////////////////////////////////////////////////////////////////////////////
    public void editTenTaiLieu(String idTaiLieu,String tenNew,UploadCallback callback){
        Map<String, Object> updates = new HashMap<>();
        updates.put("taiLieuFile/" + idTaiLieu + "/thoiGian", ServerValue.TIMESTAMP);
        updates.put("taiLieuFile/" + idTaiLieu + "/tenTaiLieu", tenNew);

        myRef.updateChildren(updates).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                callback.onUploadComplete();
            }
        });
    }
}
