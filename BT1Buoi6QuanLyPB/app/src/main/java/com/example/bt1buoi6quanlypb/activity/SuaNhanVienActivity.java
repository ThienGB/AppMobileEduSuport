package com.example.bt1buoi6quanlypb.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.annotation.Nullable;

import com.example.bt1buoi6quanlypb.R;
import com.example.bt1buoi6quanlypb.model.NhanVien;

public class SuaNhanVienActivity extends Activity {
    EditText editMa, editTen;
    RadioButton radNam;
    Button btnClear, btnSave;
    NhanVien nv = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_nhan_vien);
        getFormWidgets();
        addEvents();
    }
    public void getFormWidgets(){
        editMa = findViewById(R.id.editMaNV);
        editTen = findViewById(R.id.editTenNV);
        radNam =findViewById(R.id.radNam);
        editMa.setEnabled(false);
        editTen.requestFocus();

        btnClear = findViewById(R.id.btnXoaTrang);
        btnSave = findViewById(R.id.btnNhap);
    }
    public void getDefaultData(){
        Intent i = getIntent();
        Bundle b = i.getBundleExtra("DATA");
        nv = (NhanVien) b.getSerializable("NHANVIEN");
        editMa.setText(nv.getMa());
        editTen.setText(nv.getTen());
        radNam.setChecked(!nv.isGioitinh());

    }
    public void addEvents(){
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doXoaTrang();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLuuNhanVien();
            }
        });
    }
    public void doXoaTrang(){
        editTen.setText("");
        editTen.requestFocus();
    }
    public void doLuuNhanVien(){
        NhanVien nv = new NhanVien();
        nv.setMa(editMa.getText().toString());
        nv.setTen(editTen.getText().toString());
        nv.setGioitinh(!radNam.isChecked());
        Intent i = getIntent();
        Bundle bunder = new Bundle();
        bunder.putSerializable("NHANVIEN", nv);
        i.putExtra("DATA", bunder);
        setResult(MainActivity.SUA_NHAN_VIEN_THANHCONG, i);
        finish();
    }
}
