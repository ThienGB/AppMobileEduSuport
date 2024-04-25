package com.example.edusuport.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.edusuport.DBHelper.DBHelper;
import com.example.edusuport.R;
import com.example.edusuport.adapter.DonXinNghiHocAdapter;
import com.example.edusuport.databinding.ActivityDuyetDonXinNghiHocBinding;
import com.example.edusuport.model.DonXinNghiHoc;
import com.example.edusuport.model.MonHoc;

import java.sql.Date;
import java.sql.Timestamp;

import com.example.edusuport.model.ThuGopY;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Set;

public class DuyetDonXinNghiHocActivity extends AppCompatActivity {

    DBHelper dbHelper;
    ActivityDuyetDonXinNghiHocBinding binding;
    ArrayList<DonXinNghiHoc> listDon = new ArrayList<>();
    ArrayAdapter<DonXinNghiHoc> adapter;
    androidx.appcompat.widget.SearchView searchView;
    Calendar calendar;
    Timestamp selectedTimestamp;
    String IDLop = "12B3";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDuyetDonXinNghiHocBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        dbHelper = new DBHelper();
        calendar = Calendar.getInstance();
        GetDonXinhPhep(IDLop);
        AddEvents();
    }

    public void showDatePickerDialog(View v) {
        int year, month, day;
        if (selectedTimestamp != null) {
            // Nếu đã có selectedTimestamp, sử dụng ngày từ nó
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(selectedTimestamp.getTime());
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);
        } else {
            // Nếu không, sử dụng ngày hiện tại
            Calendar c = Calendar.getInstance();
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);
        }

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        binding.searchView.setQueryHint(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        // Đặt giờ và phút thành 0
                        calendar.set(Calendar.HOUR_OF_DAY, 0);
                        calendar.set(Calendar.MINUTE, 0);
                        calendar.set(Calendar.SECOND, 0);
                        calendar.set(Calendar.MILLISECOND, 0);
                        selectedTimestamp = new Timestamp(calendar.getTimeInMillis());
                        filterByDate(selectedTimestamp);
                        binding.searchView.requestFocus();
                    }
                }, year, month, day);
        datePickerDialog.show();
    }
    public void filterByDate(Timestamp selectedDate) {
        ArrayList<DonXinNghiHoc> filteredList = new ArrayList<>();
        for (DonXinNghiHoc item : listDon) {
            if (isSameDate(item.getThoiGian(), selectedDate)) {
                filteredList.add(item);
            }
        }
        SetData(filteredList);
        Log.d("ListFillert", "ID: " + filteredList.size());


    }
    private boolean isSameDate(Timestamp timestamp1, Timestamp timestamp2) {
        // Kiểm tra xem hai Timestamp có null không
        Log.d("Date", "ID: " + timestamp1+ " và " + timestamp2);
        if (timestamp1 == null || timestamp2 == null) {
            return false;
        }

        // Lấy ngày tháng năm từ Timestamps
        Date date1 = new Date(timestamp1.getTime());
        Date date2 = new Date(timestamp2.getTime());

        // Lấy ngày tháng năm từ Date
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateString1 = dateFormat.format(date1);
        String dateString2 = dateFormat.format(date2);

        // So sánh ngày giống nhau
        return dateString1.equals(dateString2);
    }
    public void GetDonXinhPhep(String IDLopHoc){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(dbHelper.ColecDonXinNghiHoc);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listDon = new ArrayList<>();
                for (DataSnapshot monHocSnapshot : dataSnapshot.getChildren()) {
                    if (monHocSnapshot.child(dbHelper.FieldIDLopHoc).getValue(String.class).equals(IDLopHoc))
                    {
                        String idDon = monHocSnapshot.getKey();
                        String mshs = monHocSnapshot.child(dbHelper.FieldMSHS).getValue(String.class);
                        String lydo = monHocSnapshot.child(dbHelper.FieldLyDo).getValue(String.class);
                        long timestampLong = monHocSnapshot.child(dbHelper.FieldThoiGian).getValue(Long.class);
                        String trangthai = monHocSnapshot.child(dbHelper.FieldTrangThai).getValue(String.class);
                        Timestamp thoigian = new Timestamp(timestampLong);
                        DonXinNghiHoc don = new DonXinNghiHoc(idDon, mshs, lydo, thoigian, trangthai);
                        listDon.add(don);
                        Log.d("Don Xin Phep", "ID: " + thoigian + ", Lý do: " + lydo);
                    }
                }
                SetData(listDon);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
    public void SetData(ArrayList<DonXinNghiHoc> list){
        adapter = new DonXinNghiHocAdapter(this, R.layout.list_don_xin_phep_item, list);
        binding.lsvDonXinPhep.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    public void AddEvents(){
        binding.btnCancelFillerDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetData(listDon);
            }
        });
        binding.lsvDonXinPhep.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(DuyetDonXinNghiHocActivity.this, DonXinPhepNghiHocActivity.class);
                intent.putExtra("donXinNghiHoc", listDon.get(position));
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
                ArrayList<DonXinNghiHoc> filteredThuGopYList = filter(listDon, newText);
                SetData(filteredThuGopYList);
                Log.d("ListFillert", "ID: " + filteredThuGopYList.size());
                return true;
            }
        });
    }
    private ArrayList<DonXinNghiHoc> filter(ArrayList<DonXinNghiHoc> thuGopYList, String query) {
        ArrayList<DonXinNghiHoc> filteredList = new ArrayList<>();
        for (DonXinNghiHoc donXinNghiHoc : thuGopYList) {
            if (donXinNghiHoc.getMSHS().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(donXinNghiHoc);
            }
        }
        return filteredList;
    }
}