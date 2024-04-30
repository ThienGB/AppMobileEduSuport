package com.example.edusuport.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.edusuport.R;

import java.util.Locale;

public class ChangeLanguage extends AppCompatActivity {

    Spinner spinner;
    ImageView imgBack = findViewById(R.id.btnback);
    public static final String[] languages = {"Select Language", "Tiếng Việt", "English"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.form_editinformation);

//        spinner = findViewById(R.id.spinner);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,languages);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(adapter);
//        spinner.setSelection(0);
//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String selectedLang = parent.getItemAtPosition(position).toString();
//                if (selectedLang.equals("Tiếng Việt")){
//                    setLocal(ChangeLanguage.this,"vi");
//                    finish();
//                    startActivity(getIntent());
//                } else if (selectedLang.equals("English")){
//                    setLocal(ChangeLanguage.this,"en");
//                    finish();
//                    startActivity(getIntent());
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//
//        imgBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(ChangeLanguage.this, EditInformation.class);
//                startActivity(intent);
//            }
//        });
//    }
//
//
//    public void setLocal(Activity activity, String langCode){
//        Locale locale = new Locale(langCode);
//        locale.setDefault(locale);
//        Resources resources = activity.getResources();
//        Configuration config = resources.getConfiguration();
//        config.setLocale(locale);
//        resources.updateConfiguration(config,resources.getDisplayMetrics());
//    }
    }
}
