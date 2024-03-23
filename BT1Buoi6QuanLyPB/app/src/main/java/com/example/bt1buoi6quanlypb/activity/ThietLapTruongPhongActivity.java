package com.example.bt1buoi6quanlypb.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.Nullable;

import com.example.bt1buoi6quanlypb.R;
import com.example.bt1buoi6quanlypb.model.ChucVu;
import com.example.bt1buoi6quanlypb.model.NhanVien;
import com.example.bt1buoi6quanlypb.model.PhongBan;

import java.util.ArrayList;

public class ThietLapTruongPhongActivity extends Activity {
    ListView lvTruongPhong, lvPhoPhong;
    ArrayList<NhanVien> arrNvForTP = new ArrayList<NhanVien>();
    ArrayAdapter<NhanVien> adapterForTP;
    ArrayList<NhanVien> arrNvForPP = new ArrayList<NhanVien>();
    ArrayAdapter<NhanVien> adapterForPP;
    ImageButton btnApply;
    int lastChecked = -1;
    PhongBan pb = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thiet_lap_truong_phong);
        getFormWidgets();
    }
    public void getFormWidgets(){
        lvTruongPhong = findViewById(R.id.lstTruongPhong);
        lvTruongPhong.setTextFilterEnabled(true);
        lvTruongPhong.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        lvTruongPhong.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            boolean somethingChecked = false;
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                arrNvForTP.get(position);
                if(somethingChecked){
                    CheckedTextView cv = (CheckedTextView) view;
                    cv.setChecked(false);
                }
                CheckedTextView cv = (CheckedTextView) view;
                if (!cv.isChecked()){
                    cv.setChecked(true);
                    arrNvForTP.get(position).setChucvu(ChucVu.TruongPhong);
                }else {
                    arrNvForTP.get(position).setChucvu(ChucVu.NhanVien);
                }
                lastChecked = position;
                somethingChecked = true;
            }
        });
        lvPhoPhong = findViewById(R.id.lstPhoPhong);
        lvPhoPhong.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckedTextView cv = (CheckedTextView) view;
                if(!cv.isChecked()){
                    cv.setChecked(true);
                    arrNvForPP.get(position).setChucvu(ChucVu.PhoPhong);
                }else{
                    cv.setChecked(false);
                    arrNvForTP.get(position).setChucvu(ChucVu.NhanVien);
                }
            }
        });
        adapterForTP = new ArrayAdapter<NhanVien>(this, android.R.layout.simple_list_item_single_choice, arrNvForTP);
        adapterForPP = new ArrayAdapter<NhanVien>(this, android.R.layout.simple_list_item_single_choice, arrNvForPP);
        lvTruongPhong.setAdapter(adapterForTP);
        lvPhoPhong.setAdapter(adapterForPP);
        Intent i = getIntent();
        Bundle b = i.getBundleExtra("DATA");
        pb = (PhongBan) b.getSerializable("PHONGBAN");
        addNvToListTP(pb);
        addNvToListPP(pb);
        adapterForTP.notifyDataSetChanged();
        adapterForPP.notifyDataSetChanged();

        btnApply = findViewById(R.id.btnApply);
        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doApply();
            }
        });
    }
    public void doApply(){
        Intent i = getIntent();
        Bundle bunder = new Bundle();
        bunder.putSerializable("PHONGBAN", pb);
        i.putExtra("DATA", bunder);
        setResult(MainActivity.THIET_LAP_TP_PP_THANHCONG, i);
        finish();
    }
    public void addNvToListTP(PhongBan pb){
        arrNvForTP.clear();
        for (NhanVien nv:pb.getListNhanVien()){
            arrNvForTP.add(nv);
        }
    }
    public void addNvToListPP(PhongBan pb){
        arrNvForPP.clear();
        for (NhanVien nv:pb.getListNhanVien()){
            arrNvForPP.add(nv);
        }
    }
}
