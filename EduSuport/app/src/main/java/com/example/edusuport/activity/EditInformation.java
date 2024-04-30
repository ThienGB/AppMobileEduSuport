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
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

import com.example.edusuport.R;

public class EditInformation extends AppCompatActivity {

    private Button btnSave, btnUpdate;
    private FloatingActionButton btnImg;
    private ImageView imgAvt;
    private Uri imgPath;
    private TextInputEditText edtId, edtName, edtPhone, edtClass;
    String phone, userID;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.form_editinformation);

        edtId = findViewById(R.id.edtID);
        edtName = findViewById(R.id.editName);
        edtPhone = findViewById(R.id.editphone);
        edtClass = findViewById(R.id.editIdClass);
        btnImg = findViewById(R.id.floatingActionButton2);
        edtId.setEnabled(false);
        edtName.setEnabled(false);
        edtClass.setEnabled(false);
       // btnSave = findViewById(R.id.btnSaveImage);
        btnUpdate = findViewById(R.id.btnUpdate);
        imgAvt = findViewById(R.id.imgAvatar);
        showUserData();
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePhone();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveImage();
            }
        });

        btnImg.setOnClickListener(v -> {
            Intent photoIntent = new Intent(Intent.ACTION_PICK);
            photoIntent.setType("image/*");
            startActivityForResult(photoIntent, 1);
        });
    }

    public void changePhone() {
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        if (TextUtils.isEmpty(edtPhone.getText())) {
            Toast.makeText(this, "Số điện thoại không được để trống.", Toast.LENGTH_SHORT).show();
        } else {
            firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference = firebaseDatabase.getReference().child("users");
            databaseReference.child(id).child("phone").setValue(edtPhone.getText().toString());
            phone = edtPhone.getText().toString();
            Toast.makeText(this, "Cập nhật thành công.", Toast.LENGTH_SHORT).show();
        }
    }

    private void showUserData() {
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String name = intent.getStringExtra("name");
        String mphone = intent.getStringExtra("phone");
        String idClass = intent.getStringExtra("class");

        edtId.setText(id);
        edtName.setText(name);
        edtPhone.setText(mphone);
        edtClass.setText(idClass);

//        userID = edtId.getText().toString();
//        phone = edtPhone.getText().toString();
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
                                updateProfilePicture(task.getResult().toString());
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

    private void updateProfilePicture(String url) {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("users");
        databaseReference.child("7336").child("profilePic").setValue(url);
    }
}