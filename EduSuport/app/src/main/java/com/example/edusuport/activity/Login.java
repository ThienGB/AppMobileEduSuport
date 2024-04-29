package com.example.edusuport.activity;

import static android.view.View.GONE;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.edusuport.R;
import com.example.edusuport.controllers.DangKiGV_AuController;
import com.example.edusuport.controllers.DangTaiTaiLieuController;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Objects;

public class Login extends AppCompatActivity {

    RadioGroup rb_role;
    Button btn_dk,btn_dn,btn_dkgg;
    TextView txtQuenMK;
    EditText tk,mk;
    TextInputLayout tkhint;

    String role="GV";
    boolean isAllFieldsChecked = false;
    DangKiGV_AuController dangKiGVAuController=new DangKiGV_AuController();
    public static final String SHARED_PREFS="sharePrefs";
    boolean isAllFieldsCheckedFogetGV=true;

    int RC_SIGNIN_GG =40;
    GoogleSignInClient mGoogleInClient;
    FirebaseAuth firebaseAuth= FirebaseAuth.getInstance();




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
        btn_dkgg=findViewById(R.id.btn_dkGG);

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
                showQuenMKGV();
            }
        });
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("1061030003947-m39q1a9866ot9n6npd2adj4lb3bs3ghf.apps.googleusercontent.com")
                .requestEmail()
                .build();
        mGoogleInClient=GoogleSignIn.getClient(Login.this,gso);
        btn_dkgg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent= mGoogleInClient.getSignInIntent();
                startActivityForResult(intent,RC_SIGNIN_GG);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGNIN_GG) {
            if (data != null) {

                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                try {

                    GoogleSignInAccount account = task.getResult(ApiException.class);
                    AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
                    firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                                Intent intent = new Intent(Login.this, Home.class);
                                startActivity(intent);
                            }
                        }
                    });
                } catch (ApiException e) {
                    e.printStackTrace();
                    // Handle ApiException properly here
                }
            } else {
                Toast.makeText(Login.this,"má",Toast.LENGTH_SHORT).show();

            }
        }
    }


    private void showQuenMKGV() {
        android.app.AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
        LayoutInflater layoutInflater = LayoutInflater.from(Login.this);
        builder.setTitle("Lấy lại mật khẩu: ");

        final View alertView = layoutInflater.inflate(R.layout.activity_click_quen_mkgv, null);
        builder.setView(alertView);

        EditText edEmailGV=(EditText) alertView.findViewById(R.id.emailGV);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogmini, int which) {
                if (!Patterns.EMAIL_ADDRESS.matcher(edEmailGV.getText().toString()).matches() ) {

                    Toast.makeText(Login.this,"Đề nghị nhập một email đã đăng kí",Toast.LENGTH_SHORT).show();
                }
                else {
                    dangKiGVAuController.quenMK_GV(edEmailGV.getText().toString(),Login.this);
                }

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
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