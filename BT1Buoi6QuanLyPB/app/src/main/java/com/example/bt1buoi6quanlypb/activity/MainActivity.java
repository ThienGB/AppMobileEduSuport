package com.example.bt1buoi6quanlypb.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.bt1buoi6quanlypb.R;
import com.example.bt1buoi6quanlypb.adapter.PhongBanAdapter;
import com.example.bt1buoi6quanlypb.model.ChucVu;
import com.example.bt1buoi6quanlypb.model.NhanVien;
import com.example.bt1buoi6quanlypb.model.PhongBan;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final int MO_ACTIVITY_THEM_NHAN_VIEN = 1;
    public static final int MO_ACTIVITY_SUA_NHAN_VIEN = 2;
    public static final int THEM_NHAN_VIEN_THANHCONG = 3;
    public static final int SUA_NHAN_VIEN_THANHCONG= 4;
    public static final int XEM_DS_NHAN_VIEN= 5;
    public static final int CAPNHAT_DS_NHAN_VIEN_THANHCONG = 6;
    public static final int MO_ACTIVITY_THIET_LAP_TP_PP = 7;
    public static final int THIET_LAP_TP_PP_THANHCONG = 8;
    public static final int MO_ACTIVITY_CHUYENPHONG = 9;
    public static final int CHUYENPHONG_THANHCONG = 10;

    private Button btnLuuPb;
    private EditText edtMaPb, edtTenPb;
    private ListView lvpb;
    private static ArrayList<PhongBan> arrPhongBan = new ArrayList<PhongBan>();
    private PhongBanAdapter adapterPb = null;
    private  PhongBan pbSelected = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getFormWidgets();
        addEvents();
        fakeData();
    }
    public void fakeData(){
        NhanVien nv = null;
        PhongBan pb = new PhongBan("pb1", "Kỹ thuật");
        nv= new NhanVien("nv1", "Hoàng Công Thiện", false);
        nv.setChucvu(ChucVu.TruongPhong);
        pb.themNV(nv);

        nv= new NhanVien("nv2", "Phạm Bá Thành", false);
        nv.setChucvu(ChucVu.PhoPhong);
        pb.themNV(nv);

        nv= new NhanVien("nv3", "Trương Hoàng Kim Ngân", true);
        nv.setChucvu(ChucVu.NhanVien);
        pb.themNV(nv);

        arrPhongBan.add(pb);
        pb= new PhongBan("pb2", "Dịch vụ");
        arrPhongBan.add(pb);
        pb= new PhongBan("pb3", "Truyền thông");
        arrPhongBan.add(pb);
        nv= new NhanVien("m1", "Phạm Công Hươởng", false);
        nv.setChucvu(ChucVu.NhanVien);
        pb.themNV(nv);
        nv= new NhanVien("m2", "Trần Kim Chon", false);
        nv.setChucvu(ChucVu.TruongPhong);
        pb.themNV(nv);
        adapterPb.notifyDataSetChanged();
    }
    public void getFormWidgets(){
        btnLuuPb = findViewById(R.id.btnLuuPb);
        edtMaPb = findViewById(R.id.editMaPB);
        edtTenPb = findViewById(R.id.editTenPB);
        lvpb = findViewById(R.id.lstPB);
        adapterPb = new PhongBanAdapter(this, R.layout.layout_item_custom, arrPhongBan);
        lvpb.setAdapter(adapterPb);
        registerForContextMenu(lvpb);

    }
    public void addEvents(){
        btnLuuPb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLuuPhongBan();
            }
        });
        lvpb.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                pbSelected = arrPhongBan.get(position);
                return false;
            }
        });
    }
    public void doLuuPhongBan(){
        PhongBan pb = new PhongBan(edtMaPb.getText().toString(), edtTenPb.getText().toString());
        arrPhongBan.add(pb);
        adapterPb.notifyDataSetChanged();
        edtTenPb.setText("");
        edtMaPb.setText("");
        edtMaPb.requestFocus();
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.contextmenu_phongban, menu);
    }
    @Override
    public  boolean onContextItemSelected(@NonNull MenuItem item){
        if (item.getItemId() == R.id.menuThemNV)
            doThemNhanVien();
        else if (item.getItemId() == R.id.menuDanhSachNV)
            doDanhSachNhanVien();
        else if (item.getItemId() == R.id.menuTruongPhong)
            doThietLapLanhDao();
        else if (item.getItemId() == R.id.menuXoaPB)
            doXoaPhongBan();
        return super.onContextItemSelected(item);
    }

    public void doThemNhanVien(){
        Intent i =new Intent(this, ThemNhanVienActivity.class);
        startActivityForResult(i, MO_ACTIVITY_THEM_NHAN_VIEN);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == THEM_NHAN_VIEN_THANHCONG){
            Bundle bundle = data.getBundleExtra("DATA");
            NhanVien nv = (NhanVien) bundle.getSerializable("NHANVIEN");
            pbSelected.themNV(nv);
            adapterPb.notifyDataSetChanged();
        }else if(resultCode == THIET_LAP_TP_PP_THANHCONG || resultCode == CAPNHAT_DS_NHAN_VIEN_THANHCONG){
            Bundle bundle = data.getBundleExtra("DATA");
            PhongBan pb = (PhongBan) bundle.getSerializable("PHONGBAN");
            pbSelected.getListNhanVien().clear();
            pbSelected.getListNhanVien().addAll(pb.getListNhanVien());
            adapterPb.notifyDataSetChanged();

        }
    }
    public void doDanhSachNhanVien(){
        Intent i = new Intent(this, DanhSachNhanVienActivity.class);
        Bundle b = new Bundle();
        b.putSerializable("PHONGBAN", pbSelected);
        i.putExtra("DATA", b);
        startActivityForResult(i, XEM_DS_NHAN_VIEN);
    }
    public void doThietLapLanhDao(){
        Intent i = new Intent(this, ThietLapTruongPhongActivity.class);
        Bundle b = new Bundle();
        b.putSerializable("PHONGBAN", pbSelected);
        i.putExtra("DATA", b);
        startActivityForResult(i, MO_ACTIVITY_THIET_LAP_TP_PP);
    }
    public void doXoaPhongBan(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Hỏi đáp");
        builder.setMessage("Bạn có chắc muốn xóa [" + pbSelected.getTen() + "]");
        builder.setIcon(android.R.drawable.ic_input_delete);
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                arrPhongBan.remove(pbSelected);
                adapterPb.notifyDataSetChanged();
            }
        });
        builder.show();
        Bundle b = new Bundle();
    }
    public static ArrayList<PhongBan> getListPhongBan(){
        return arrPhongBan;
    }
}