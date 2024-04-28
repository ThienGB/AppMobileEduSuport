package com.example.edusuport.activity;

import static android.view.View.GONE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.edusuport.R;
import com.example.edusuport.controllers.DangKiGV_AuController;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class Login extends AppCompatActivity {

    RadioGroup rb_role;
    Button btn_dk,btn_dn;
    TextView txtQuenMK;
    EditText tk,mk;
    TextInputLayout tkhint;

    String role="GV";
    boolean isAllFieldsChecked = false;
    DangKiGV_AuController dangKiGVAuController=new DangKiGV_AuController();
    public static final String SHARED_PREFS="sharePrefs";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.form_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.form_login), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getComponet();
        handelClick();
        checkBoxRemeber();

    }

    private void getComponet(){
        rb_role=(RadioGroup) findViewById(R.id.radioBtn_role);
        btn_dk=(Button) findViewById(R.id.btn_dkGmail);
        btn_dn=(Button) findViewById(R.id.btn_dn);
        txtQuenMK=findViewById(R.id.txtQuenMK);
        tk=findViewById(R.id.txtTK);
        mk=findViewById(R.id.txtMK);
        tkhint=findViewById(R.id.txtLogTKHint);

        tk.setText("");
        mk.setText("");
    }
    private void handelClick(){

        rb_role.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                tk.setText("");
                mk.setText("");
                if(checkedId==R.id.radioBtn_GV){
                    role="GV";
                    btn_dk.setVisibility(View.VISIBLE);
                    tkhint.setHint("Email giáo viên");
                    tk.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                } else if (checkedId==R.id.radioBtn_PH) {
                    role="PH";
                    btn_dk.setVisibility(View.GONE);
                    tkhint.setHint("Mã số phụ huynh");
                    tk.setInputType(InputType.TYPE_CLASS_NUMBER);
                }
                else
                {
                    role="HS";
                    btn_dk.setVisibility(View.GONE);
                    tk.setInputType(InputType.TYPE_CLASS_NUMBER);
                    tkhint.setHint("Mã số sinh viên");
                }
            }
        });

        btn_dk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Login.this,RegisterAccount.class);
                //intent.putExtra("idMon",monHoc.getIdMon());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        btn_dn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAllFieldsChecked = CheckAllFields();
                if (isAllFieldsChecked) {
                    if(Objects.equals(role, "GV")){
                        dangKiGVAuController.checkLogin_GV(tk.getText().toString(), mk.getText().toString(), Login.this, new DangKiGV_AuController.UploadCallback() {
                            @Override
                            public void onUploadComplete() {
                                Toast.makeText(Login.this, "GV", Toast.LENGTH_SHORT).show();
                                SharedPreferences sharedPreferences=getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
                                SharedPreferences.Editor editor= sharedPreferences.edit();
                                editor.putString("name","true");
                                editor.apply();

                            }

                            @Override
                            public void onUploadFailed(String errorMessage) {

                            }
                        });

                    } else if (Objects.equals(role, "PH")) {
                        Toast.makeText(Login.this, "PH", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(Login.this, "HS", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
        txtQuenMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dangKiGVAuController.quenMK_GV("phuongtrinhhoaian@gmail.com",Login.this);
            }
        });



    }
    private void checkBoxRemeber(){
        SharedPreferences sharedPreferences=getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        String check= sharedPreferences.getString("name","");
        if(check.equals("true")){
            Toast.makeText(Login.this,check,Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(Login.this, DangTaiTaiLieuActivity.class);
            startActivity(intent);
            finish();
        }
    }
    private boolean CheckAllFields() {
        if (tk.length() == 0) {
            tk.setError("This field is required");
            return false;
        }

        if (mk.length() == 0) {
            mk.setError("This field is required");
            return false;
        }

         if (mk.length() < 6) {
            mk.setError("Password must be minimum 6 characters");
            return false;
        }
        if (tk.getText().toString().contains(" ")) {
            tk.setError("Spaces are not allowed");
            return false;
        }

// Kiểm tra xem tk có đúng định dạng email hay không
        if (!Patterns.EMAIL_ADDRESS.matcher(tk.getText().toString()).matches() && Objects.equals(role, "GV")) {
            tk.setError("Invalid email address");
            return false;
        }
        // after all validation return true.
        return true;
    }
}