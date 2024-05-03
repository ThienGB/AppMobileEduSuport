package com.example.edusuport.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.edusuport.DBHelper.DBHelper;
import com.example.edusuport.R;
import com.example.edusuport.adapter.HocSinhAdapter;
import com.example.edusuport.adapter.LopHoc_IdGV_Nav_Adapter;
import com.example.edusuport.adapter.ViewHolderClick;
import com.example.edusuport.controllers.DangTaiTaiLieuController;
import com.example.edusuport.controllers.LopHocController;
import com.example.edusuport.databinding.ActivityDanhSachHocSinhBinding;
import com.example.edusuport.databinding.ActivityNhanXetChungBinding;
import com.example.edusuport.model.GiaoVien;
import com.example.edusuport.model.HocSinh;
import com.example.edusuport.model.LopHoc;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DanhSachHocSinh extends AppCompatActivity {
    DBHelper dbHelper;
    ActivityDanhSachHocSinhBinding binding;
    ArrayAdapter<HocSinhAdapter> adapter;
    ArrayList<HocSinh> listHS = new ArrayList<>();
    public  static GiaoVien giaoVien = Home.giaoVien;
    ArrayList<LopHoc> listLop=new ArrayList<LopHoc>();
    private LopHoc lopHoc = QuanLyLopHocActivity.lopHoc;
    LopHocController lopHocController=new LopHocController();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDanhSachHocSinhBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        dbHelper = new DBHelper();

        GetDSHocSinh(lopHoc.getIdLopHoc());
        AddEvents();
    }
    public void GetDSHocSinh(String IDLopHoc){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(dbHelper.ColecHocSinh);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listHS = new ArrayList<>();
                for (DataSnapshot monHocSnapshot : dataSnapshot.getChildren()) {
                    if (monHocSnapshot.child(dbHelper.FieldIDLopHoc).getValue(String.class).equals(IDLopHoc))
                    {
                        String Mshs = monHocSnapshot.getKey();
                        String Ten = monHocSnapshot.child(dbHelper.FieldTenPH).getValue(String.class);
                        String IDLopHoc = monHocSnapshot.child(dbHelper.FieldIDLopHoc).getValue(String.class);

                        HocSinh hs = new HocSinh(Mshs, Ten, IDLopHoc);
                        listHS.add(hs);
                        Log.d("Don Xin Phep", "ID: " + Mshs + ",Tên : " + Ten);
                    }
                }
                SetData(listHS);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
    public void SetData(ArrayList<HocSinh> list){
        adapter = new HocSinhAdapter(this, R.layout.item_giao_vien_layout, list);
        binding.lstHocSinh.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    public void AddEvents(){
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Back();
            }
        });

        binding.lstHocSinh.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showBottomSheet(listHS.get(position));
//                Intent intent = new Intent(DanhSachHocSinh.this, ThemHocSinhActivity.class);
//                intent.putExtra("hocSinh", listHS.get(position));
//                startActivity(intent);
                return false;
            }
        });
        binding.searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<HocSinh> filteredThuGopYList = filter(listHS, newText);
                SetData(filteredThuGopYList);
                Log.d("ListFillert", "ID: " + filteredThuGopYList.size());
                return true;
            }
        });
    }
    private ArrayList<HocSinh> filter(ArrayList<HocSinh> thuGopYList, String query) {
        ArrayList<HocSinh> filteredList = new ArrayList<>();
        for (HocSinh hocSinh : thuGopYList) {
            if (hocSinh.getMSHS().toLowerCase().contains(query.toLowerCase())
            || hocSinh.getTen().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(hocSinh);
            }
        }
        return filteredList;
    }
    public void showBottomSheet(HocSinh HS){
        final Dialog dialog=new Dialog(DanhSachHocSinh.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottom_hocsinh);

        TableRow sua=dialog.findViewById(R.id.bot_chinhsuaGFC);
        TableRow xoa=dialog.findViewById(R.id.bot_xoaGFC);
        TextView ten=dialog.findViewById(R.id.txvTenHSBT);
        TextView mahs = dialog.findViewById(R.id.txvMaHSBT);
        ten.setText(HS.getTen());
        mahs.setText(HS.getMSHS());
        sua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.app.AlertDialog.Builder builder = new AlertDialog.Builder(DanhSachHocSinh.this);
                LayoutInflater layoutInflater = LayoutInflater.from(DanhSachHocSinh.this);
                builder.setTitle("Chỉnh sửa học sinh");

                final View alertView = layoutInflater.inflate(R.layout.layout_sua_hoc_sinh, null);
                builder.setView(alertView);

                EditText txtTenHS=(EditText) alertView.findViewById(R.id.sua_txtTenHS);
                txtTenHS.setText(HS.getTen());

                EditText txtMaHS=(EditText) alertView.findViewById(R.id.sua_txtMaHS);
                txtMaHS.setText(HS.getMSHS());
                txtTenHS.setTextColor(ContextCompat.getColor(DanhSachHocSinh.this, R.color.black_low_emp));
                txtMaHS.setTextColor(ContextCompat.getColor(DanhSachHocSinh.this, R.color.black_low_emp));
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogmini, int which) {
                        String tenNew= txtTenHS.getText().toString();
                        String mshsNew= txtMaHS.getText().toString();
                        HocSinh hs = new HocSinh(HS.getMSHS(), tenNew, mshsNew);
                        editHS(hs);
                        dialog.dismiss();

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DanhSachHocSinh.this);
                builder.setTitle("Xác nhận xóa");
                builder.setMessage("Bạn có chắc chắn muốn xóa không?");

                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogmini, int which) {
                        deleteHS(HS.getMSHS());
                        dialogmini.dismiss();
                        dialog.dismiss();
                    }
                });

                // Nút hủy
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                // Hiển thị hộp thoại xác nhận
                AlertDialog dialog1 = builder.create();
                dialog1.show();
            }

        });
        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations= com.google.android.material.R.style.Animation_Design_BottomSheetDialog;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }
//    public void editHS(HocSinh HS){
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference(dbHelper.ColecHocSinh);
//        Map<String, Object> updates = new HashMap<>();
//        updates.put(dbHelper.FieldTenHS, HS.getTen());
//        updates.put(dbHelper.FieldIDLopHoc, HS.getIDLopHoc());
//        myRef.child(HS.getMSHS()).updateChildren(updates);
//        Toast.makeText(this, "Chỉnh sửa học sinh thành công", Toast.LENGTH_SHORT).show();
//        GetDSHocSinh(lopHoc.getIdLopHoc());
//    }
    private void updateHocSinh(HocSinh HS) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(dbHelper.ColecHocSinh);
        Map<String, Object> updates = new HashMap<>();
        updates.put(dbHelper.FieldTenHS, HS.getTen());
        updates.put(dbHelper.FieldIDLopHoc, HS.getIDLopHoc());
        myRef.child(HS.getMSHS()).updateChildren(updates);
        Toast.makeText(this, "Chỉnh sửa học sinh thành công", Toast.LENGTH_SHORT).show();
        GetDSHocSinh(lopHoc.getIdLopHoc());
    }
    public void editHS(HocSinh HS){
        // Khởi tạo Firebase database reference
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference lopHocRef = database.getReference(dbHelper.ColecLopHoc);

        lopHocRef.child(HS.getIDLopHoc()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    updateHocSinh(HS);
                } else {
                    Toast.makeText(DanhSachHocSinh.this, "idLopHoc không tồn tại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Xử lý khi truy vấn bị hủy
            }
        });
    }
    public void deleteHS(String mshs){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        String path = dbHelper.ColecHocSinh+"/"+mshs;
        DatabaseReference nodeReference = databaseReference.child(path);
        nodeReference.removeValue();
        Toast.makeText(this, "Xóa học sinh thành công", Toast.LENGTH_SHORT).show();
        GetDSHocSinh(lopHoc.getIdLopHoc());
    }
    public void Back(){
        super.onBackPressed();
    }
}