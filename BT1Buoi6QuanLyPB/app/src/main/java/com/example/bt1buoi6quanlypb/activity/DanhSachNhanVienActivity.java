package com.example.bt1buoi6quanlypb.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.bt1buoi6quanlypb.R;
import com.example.bt1buoi6quanlypb.adapter.NhanVienAdapter;
import com.example.bt1buoi6quanlypb.model.NhanVien;
import com.example.bt1buoi6quanlypb.model.PhongBan;

import java.util.ArrayList;

public class DanhSachNhanVienActivity extends Activity {
    TextView txtmsg;
    ImageButton btnBack;
    ListView lvNhanVien;
    ArrayList<NhanVien> arrNhanVien = null;
    NhanVienAdapter adapter =null;
    PhongBan pb = null;
    private NhanVien nvSelected = null;
    private int position = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_nhan_vien);
        txtmsg = findViewById(R.id.txtmsg);
        btnBack =findViewById(R.id.btnback);
        lvNhanVien = findViewById(R.id.lstNV);
        getDataFromMain();
        addEvents();
        registerForContextMenu(lvNhanVien);
    }

    public void getDataFromMain(){
        Intent i = getIntent();
        Bundle b = i.getBundleExtra("DATA");
        pb = (PhongBan) b.getSerializable("PHONGBAN");
        arrNhanVien = pb.getListNhanVien();
        adapter = new NhanVienAdapter(this, R.layout.layout_item_custom, arrNhanVien);
        lvNhanVien.setAdapter(adapter);
        txtmsg.setText("DS nhân viên [" + pb.getTen()+"]");
    }

    public void addEvents(){
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doUpdateToMain();
            }
        });
        lvNhanVien.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int agr0, long id) {
                nvSelected =arrNhanVien.get(agr0);
                position = agr0;
                return false;
            }
        });
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.contextmenu_nhanvien, menu);
    }
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item){
        if (item.getItemId() == R.id.menuSuaNV)
            doSuaNhanVien();
        else if (item.getItemId() == R.id.menuChuyenPB)
            doChuyenPhongBan();
        else if (item.getItemId() == R.id.menuXoaNV)
            doXoaNhanVien();
        return super.onContextItemSelected(item);
    }
    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==MainActivity.SUA_NHAN_VIEN_THANHCONG){
            Bundle  b = data.getBundleExtra("DATA");
            NhanVien nv = (NhanVien) b.getSerializable("NHANVIEN");
            arrNhanVien.set(position, nv);
            adapter.notifyDataSetChanged();
        }
        else if(resultCode == MainActivity.CHUYENPHONG_THANHCONG){
            arrNhanVien.remove(nvSelected);
            adapter.notifyDataSetChanged();
        }
    }
    public void doSuaNhanVien(){
        Intent i= new Intent(this, SuaNhanVienActivity.class);
        Bundle b = new Bundle();
        b.putSerializable("NHANVIEN", nvSelected);
        i.putExtra("DATA", b);
        startActivityForResult(i, MainActivity.MO_ACTIVITY_SUA_NHAN_VIEN);
    }
    public void doChuyenPhongBan(){
        Intent i= new Intent(this, ChuyenPhongBanActivity.class);
        Bundle b = new Bundle();
        b.putSerializable("NHANVIEN", nvSelected);
        i.putExtra("DATA", b);
        startActivityForResult(i, MainActivity.MO_ACTIVITY_CHUYENPHONG);
    }
    public void doXoaNhanVien(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Hỏi xóa");
        builder.setMessage("Bạn có chắc chắn muốn xóa ["+nvSelected.getTen()+"]");
        builder.setIcon(android.R.drawable.ic_input_delete);
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                arrNhanVien.remove(nvSelected);
                adapter.notifyDataSetChanged();
            }
        });
        builder.show();
    }
    public void doUpdateToMain(){
        Intent i = getIntent();
        Bundle b = new Bundle();
        b.putSerializable("PHONGBAN", pb);
        i.putExtra("DATA", b);
        setResult(MainActivity.CAPNHAT_DS_NHAN_VIEN_THANHCONG, i);
        finish();
    }
}
