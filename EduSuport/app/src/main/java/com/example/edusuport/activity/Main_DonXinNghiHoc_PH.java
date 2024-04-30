package com.example.edusuport.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.edusuport.R;

import java.util.Calendar;

public class Main_DonXinNghiHoc_PH extends AppCompatActivity {

    Button btnNgayNghi;

    TextView tvNgayNghi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_don_xin_nghi_hoc_ph);
        findView();
        HandleAllClick();
    }

    private void findView(){
        btnNgayNghi=(Button) findViewById(R.id.chon_ngay_nghi);
        tvNgayNghi=(TextView) findViewById(R.id.hien_ngay_nghi);
    }
    private void HandleAllClick(){
        ClickBtnNgayNghi();
    }
    private void ClickBtnNgayNghi(){
        btnNgayNghi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();

                // on below line we are getting
                // our day, month and year.
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                // on below line we are creating a variable for date picker dialog.
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        // on below line we are passing context.
                        Main_DonXinNghiHoc_PH.this,new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // on below line we are setting date to our text view.
                                tvNgayNghi.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        },
                        // on below line we are passing year,
                        // month and day for selected date in our date picker.
                        year, month, day);
                // at last we are calling show to
                // display our date picker dialog.
                datePickerDialog.show();
            }
        });
    }
}