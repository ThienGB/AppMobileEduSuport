package com.example.edusuport.controllers;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.edusuport.R;
import com.example.edusuport.activity.DangTaiTaiLieuActivity;
import com.example.edusuport.activity.Home;
import com.example.edusuport.activity.Login;
import com.example.edusuport.model.Account;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.ktx.Firebase;

public class DangKiGV_AuController {
    FirebaseAuth firebaseAuth= FirebaseAuth.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    GoogleSignInClient mGoogleInClient;

    public interface UploadCallback {
        void onUploadComplete();
        void onUploadFailed(String errorMessage); // Optionally, handle upload failure

    }




    public void checkLogin_GV(String email, String password,Context context,UploadCallback callback){
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Login...");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            progressDialog.dismiss();
                            if(firebaseAuth.getCurrentUser().isEmailVerified()){
                                Toast.makeText(context,"Login thành công",Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(context, DangTaiTaiLieuActivity.class);
                                context.startActivity(intent);
                                myRef.child("user").child("giaoVien").child(firebaseAuth.getCurrentUser().getUid()).child("trangThai").setValue("valid");
                                callback.onUploadComplete();
                            }
                            else{
                                Toast.makeText(context,"Bạn chưa verify",Toast.LENGTH_SHORT).show();
                            }


                        }
                        else {
                            Toast.makeText(context,"Không tồn tại tài khoản",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    public void dangkiGG_GV(Context context,Intent data){
        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
        try {

            GoogleSignInAccount account = task.getResult(ApiException.class);
            AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
            firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                        Intent intent = new Intent(context, Home.class);
                        context.startActivity(intent);
                    }
                }
            });
        } catch (ApiException e) {
            e.printStackTrace();
            // Handle ApiException properly here
        }
    }
    public void dangKi_GV(String email, String password,Context context){
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Create new acc...");
        progressDialog.show();
        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    progressDialog.dismiss();
                                    Toast.makeText(context,"Đã có email gửi về, hãy verify",Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(context, Login.class);
                                    context.startActivity(intent);

                                    FirebaseUser current= firebaseAuth.getCurrentUser();
                                    Account gv=new Account(null,null,current.getPhotoUrl(),current.getPhoneNumber(),"giaoVien",null,null,"invalid");
                                    myRef.child("user").child("giaoVien").child(firebaseAuth.getCurrentUser().getUid()).setValue(gv);


                                }
                            });

                        }
                        else {
                            Toast.makeText(context,"ĐK thất bại",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    public void quenMK_GV(String email,Context context){
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Sending Email...");
        progressDialog.show();
            firebaseAuth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            progressDialog.dismiss();
                            if (task.isSuccessful()) {
                                Toast.makeText(context,"Đã có email gửi về, hãy check",Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(context,"Không gửi được email, có lỗi",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
             }



    public void logout_GV(){
        firebaseAuth.signOut();

    }
}
