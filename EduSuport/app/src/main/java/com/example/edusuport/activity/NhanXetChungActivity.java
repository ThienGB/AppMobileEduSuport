package com.example.edusuport.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.edusuport.R;

public class NhanXetChungActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhan_xet_chung);
    }

    public static class NhanXetCaNhanActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_nhan_xet_ca_nhan);
        }
    }
}