package com.example.edusuport.DBHelper;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DBHelper {
    private DatabaseReference databaseRef;
    public final String ColecThoiKhoaBieu = "thoikhoabieu";
    public final String ColecMonHoc = "monhoc";
    public final String ColecHocSinh ="hocsinh";
    public final String ColecDonXinNghiHoc="donxinnghihoc";
    public final String FieldMSHS="mshs";
    public final String FieldLyDo ="lydo";
    public final String FieldThoiGian="thoigian";
    public final String FieldTrangThai="trangthai";
    public final String FieldTenHS = "ten";
    public final String FieldIDLopHoc = "idlophoc";
    public final String FieldTenMon = "tenmon";
    public final String FieldIDMon = "idmon";
    public final String FieldTiet = "tiet";
    public final String FieldThu = "thu";
    public final String ValueTTDaDuyet = "Đã duyệt";
    public final String ValueTTChuaDuyet = "Chưa duyệt";
    public final String ValueTTTuChoi = "Từ chối";

    public DBHelper() {
        // Khởi tạo DatabaseReference
        databaseRef = FirebaseDatabase.getInstance().getReference();
    }

    public void getTenHocSinhByMSHS(String MSHS, final TenHocSinhCallback callback) {
        DatabaseReference hocsinhRef = databaseRef.child(ColecHocSinh).child(MSHS).child(FieldTenHS);
        hocsinhRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String tenHocSinh = dataSnapshot.getValue(String.class);
                    callback.onTenHocSinhFetched(tenHocSinh);
                } else {
                    callback.onTenHocSinhFetched(null);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callback.onTenHocSinhFetched(null);
            }
        });
    }

    public interface TenHocSinhCallback {
        void onTenHocSinhFetched(String tenHocSinh);
    }


}
