package com.example.edusuport.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.edusuport.model.GiaoVien;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

import com.example.edusuport.R;

public class EditInformation extends AppCompatActivity {
    GiaoVien giaoVien = Home.giaoVien;
    private Button btnSave, btnUpdate, btnBack;
    private FloatingActionButton btnImg;
    private ImageView imgAvt;
    private Uri imgPath;
    private EditText edtName, edtEmail, edtPhone;
    private ImageButton btnEdit;
    String phone, userID;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.form_information);

        btnImg = findViewById(R.id.floatingActionButton2);
        btnEdit = findViewById(R.id.btnEdit);

        edtEmail.setEnabled(false);
        edtPhone.setEnabled(false);


        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtName.setEnabled(true);
                edtPhone.setEnabled(true);
                btnEdit.setImageResource(R.drawable.icon_save);
            }
        });

        edtName = findViewById(R.id.edtName);
        edtEmail = findViewById(R.id.edtEmail);
        edtPhone = findViewById(R.id.edtPhone);
        imgAvt = findViewById(R.id.imageView);
        showUserData();
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnEdit.setImageResource(R.drawable.icon_edit);
                SaveImage();
            }
        });

        btnImg.setOnClickListener(v -> {
            Intent photoIntent = new Intent(Intent.ACTION_PICK);
            photoIntent.setType("image/*");
            startActivityForResult(photoIntent, 1);
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditInformation.this, Home.class);
                startActivity(intent);
            }
        });
    }

    //
    public void changePhone() {
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        if (TextUtils.isEmpty(edtPhone.getText())) {
            Toast.makeText(this, "Số điện thoại không được để trống.", Toast.LENGTH_SHORT).show();
        } else {
            firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference = firebaseDatabase.getReference().child("user");
            databaseReference.child(id).child("phone").setValue(edtPhone.getText().toString());
            phone = edtPhone.getText().toString();
            Toast.makeText(this, "Cập nhật thành công.", Toast.LENGTH_SHORT).show();
        }
    }

    private void showUserData() {
        edtName.setText(giaoVien.getTenGiaoVien());
        edtEmail.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            imgPath = data.getData();
            getImageInImgView();
        }
    }

    private void getImageInImgView() {

        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imgPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        imgAvt.setImageBitmap(bitmap);
    }

    private void SaveImage() {
        FirebaseStorage.getInstance().getReference("Images/" + UUID.randomUUID().toString()).putFile(imgPath).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {
                    task.getResult().getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                String tennew=edtName.getText().toString();
                                updateProfilePicture(task.getResult().toString(),tennew);
                            }
                        }
                    });
                    Toast.makeText(EditInformation.this, "Upload Successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(EditInformation.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateProfilePicture(String url,String tennew ) {
        FirebaseDatabase.getInstance().getReference("giaovien/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/urlAva").setValue(url);
        FirebaseDatabase.getInstance().getReference("giaovien/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/ten").setValue(tennew);
    }
}