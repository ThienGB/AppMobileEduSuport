package com.example.edusuport.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.edusuport.R;
import com.example.edusuport.databinding.ActivityEditInformationBinding;
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
import java.util.Locale;
import java.util.UUID;

public class EditInformationActivity extends AppCompatActivity {
    ActivityEditInformationBinding binding;
    GiaoVien giaoVien = Home.giaoVien;
    private FloatingActionButton btnImg;
    private Uri imgPath;

    String phone, userID;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;

    public static final String[] languages = {"Select Language", "Tiếng Việt", "English"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
binding = ActivityEditInformationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.edtName.setEnabled(false);
        binding.edtEmail.setEnabled(false);
        binding.edtPhone.setEnabled(false);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, languages);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        binding.spinnerLanguage.setAdapter(adapter);


        binding.spinnerLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedLanguage = languages[position];

                if (selectedLanguage.equals("Tiếng Việt")) {
                    setLocal(EditInformationActivity.this, "vi");
                    finish();
                    startActivity(getIntent());
                } else if (selectedLanguage.equals("English")) {
                    setLocal(EditInformationActivity.this, "en");
                    finish();
                    startActivity(getIntent());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Xử lý khi không có giá trị nào được chọn
            }
        });

        binding.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.edtName.setEnabled(true);
                binding.edtPhone.setEnabled(true);
                binding.btnEdit.setImageResource(R.drawable.icon_save);
            }
        });

        showUserData();
        binding.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.btnEdit.setImageResource(R.drawable.icon_edit);
                SaveImage();
            }
        });

        binding.floatingActionButton2.setOnClickListener(v -> {
            Intent photoIntent = new Intent(Intent.ACTION_PICK);
            photoIntent.setType("image/*");
            startActivityForResult(photoIntent, 1);
        });

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditInformationActivity.this, Home.class);
                startActivity(intent);
            }
        });
    }

    //
    public void changePhone() {
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        if (TextUtils.isEmpty(binding.edtPhone.getText())) {
            Toast.makeText(this, "Số điện thoại không được để trống.", Toast.LENGTH_SHORT).show();
        } else {
            firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference = firebaseDatabase.getReference().child("user");
            databaseReference.child(id).child("phone").setValue(binding.edtPhone.getText().toString());
            phone = binding.edtPhone.getText().toString();
            Toast.makeText(this, "Cập nhật thành công.", Toast.LENGTH_SHORT).show();
        }
    }

    private void showUserData() {
        binding.edtName.setText(giaoVien.getTenGiaoVien());
        binding.edtEmail.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
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
        binding.imgAvt.setImageBitmap(bitmap);
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
                                String tennew=binding.edtName.getText().toString();
                                updateProfilePicture(task.getResult().toString(),tennew);
                            }
                        }
                    });
                    Toast.makeText(EditInformationActivity.this, "Upload Successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(EditInformationActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateProfilePicture(String url,String tennew ) {
        FirebaseDatabase.getInstance().getReference("giaovien/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/urlAva").setValue(url);
        FirebaseDatabase.getInstance().getReference("giaovien/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/ten").setValue(tennew);
    }
    public void setLocal(Activity activity, String langCode) {
        Locale locale = new Locale(langCode);
        locale.setDefault(locale);
        Resources resources = activity.getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());
    }
}