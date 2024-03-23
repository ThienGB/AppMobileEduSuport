package com.example.bt1buoi6quanlypb.activity;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaCommunicationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.annotation.Nullable;

import com.example.bt1buoi6quanlypb.R;
import com.example.bt1buoi6quanlypb.model.NhanVien;

import java.security.interfaces.EdECPrivateKey;

public class ThemNhanVienActivity extends Activity {
    private Button btnXoaTrang, btnLuuNhanVien;
    private EditText edtManv, editTenNV;
    private RadioButton radNam;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_nhan_vien);
        getFormWidgets();
        addEvents();
    }
    public void getFormWidgets(){
        btnXoaTrang = findViewById(R.id.btnXoaTrang);
        btnLuuNhanVien = findViewById(R.id.btnNhap);
        edtManv = findViewById(R.id.editMaNV);
        editTenNV = findViewById(R.id.editTenNV);
        radNam = findViewById(R.id.radNam);
    }
    public void addEvents(){
        btnXoaTrang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doXoaTrang();
            }
        });
        btnLuuNhanVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLuuNhanVien();
            }
        });
    }
    public void doXoaTrang(){
        edtManv.setText("");
        editTenNV.setText("");
        edtManv.requestFocus();
    }
    public void doLuuNhanVien(){
        NhanVien nv = new NhanVien();
        nv.setMa(edtManv.getText().toString());
        nv.setTen(editTenNV.getText().toString());
        nv.setGioitinh(!radNam.isChecked());
        Intent i = getIntent();
        Bundle bunder = new Bundle();
        bunder.putSerializable("NHANVIEN", nv);
        i.putExtra("DATA", bunder);
        setResult(MainActivity.THEM_NHAN_VIEN_THANHCONG, i);
        finish();
    }
}
