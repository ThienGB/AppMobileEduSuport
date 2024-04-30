package com.example.edusuport.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.edusuport.DBHelper.DBHelper;
import com.example.edusuport.R;
import com.example.edusuport.adapter.HocSinhAdapter;
import com.example.edusuport.databinding.ActivityNhapDiemChungBinding;
import com.example.edusuport.model.DonXinNghiHoc;
import com.example.edusuport.model.HocSinh;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NhapDiemChungActivity extends AppCompatActivity {

    DBHelper dbHelper;
    ActivityNhapDiemChungBinding binding;
    ArrayAdapter<HocSinhAdapter> adapter;
    ArrayList<HocSinh> listHS = new ArrayList<>();

    private String IDLH = "12B3";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNhapDiemChungBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dbHelper = new DBHelper();
        AddEvents();
        GetDonXinhPhep(IDLH);
    }
    public void GetDonXinhPhep(String IDLopHoc){
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

                        HocSinh don = new HocSinh(Mshs, Ten, IDLopHoc);
                        listHS.add(don);
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
        binding.lstHocSinh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(NhapDiemChungActivity.this, NhapDiemCaNhanActivity.class);
                intent.putExtra("hocSinh", listHS.get(position));
                startActivity(intent);
            }
        });
        binding.searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Xử lý khi người dùng gửi truy vấn tìm kiếm
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
            if (hocSinh.getMSHS().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(hocSinh);
            }
        }
        return filteredList;
    }
}