package com.example.edusuport.activity;

import android.content.Context;
import android.content.DialogInterface;
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

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.edusuport.DBHelper.DBHelper;
import com.example.edusuport.databinding.ActivityEditInformationBinding;
import com.example.edusuport.databinding.ActivityEditInformationPhhsBinding;
import com.example.edusuport.model.GiaoVien;
import com.example.edusuport.model.PhuHuynh;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import com.example.edusuport.R;
import com.squareup.picasso.Picasso;

public class EditInformation_PH extends AppCompatActivity {
    ActivityEditInformationPhhsBinding binding;
    PhuHuynh phuHuynh = HomePhActivity.phuHuynh;
    private FloatingActionButton btnImg;
    private Uri imgPath;
    boolean isEdit=false;
    boolean isUserInteracted = false;
    public static final String[] languages = {"Tiếng Việt", "English"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditInformationPhhsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if(!phuHuynh.getUrl().isEmpty()){
            Picasso.get().load(phuHuynh.getUrl()).into(binding.imgAvt);
        }
        else {
            Picasso.get().load(R.drawable.profile).into(binding.imgAvt);
        }
        binding.edtName.setEnabled(false);
        binding.edtMSPH.setEnabled(false);
        binding.edtPhone.setEnabled(false);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, languages);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        binding.spinnerLanguage.setAdapter(adapter);
        if (phuHuynh.getNgonNgu().equals("") || phuHuynh.getNgonNgu().equals("vi")){
            binding.spinnerLanguage.setSelection(0);
        }
        else
            binding.spinnerLanguage.setSelection(1);

        binding.spinnerLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (isUserInteracted) {
                    String selectedLanguage = languages[position];
                    String ngonNgu ="";
                    if (selectedLanguage.equals("Tiếng Việt")) {
                        setLocaleFromDatabase("vi");
                        ngonNgu = "vi";
                        Toast.makeText(EditInformation_PH.this, "Đổi ngôn ngữ thành công", Toast.LENGTH_SHORT).show();
                    } else if (selectedLanguage.equals("English")) {
                        setLocaleFromDatabase("en");
                        ngonNgu = "en";
                        Toast.makeText(EditInformation_PH.this, "Change Language Successfully", Toast.LENGTH_SHORT).show();
                    }
                    DBHelper dbHelper = new DBHelper();
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference lophocRef = database.getReference(dbHelper.ColecPhuHuynh).child(phuHuynh.getMSPH());
                    Map<String, Object> updates = new HashMap<>();
                    updates.put("ngonNgu", ngonNgu);
                    lophocRef.updateChildren(updates);
                }else{
                    isUserInteracted = true;
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

                if(!isEdit){
                    binding.btnEdit.setImageResource(R.drawable.icon_save);
                    isEdit=true;
                    binding.edtName.findFocus();
                    binding.edtName.setEnabled(true);
                    binding.edtPhone.setEnabled(true);
                    binding.edtName.setBackgroundResource(R.drawable.background_subject_radius_click);
                    binding.edtPhone.setBackgroundResource(R.drawable.background_subject_radius_click);

                }
                else {
                    binding.btnEdit.setImageResource(R.drawable.icon_edit);
                    //SaveData();
                    binding.edtName.setEnabled(false);
                    binding.edtPhone.setEnabled(false);
                    binding.edtName.setBackgroundResource(R.drawable.background_subject_radius);
                    binding.edtPhone.setBackgroundResource(R.drawable.background_subject_radius);
                    changePhone();
                }

            }
        });

        showUserData();

        binding.floatingActionButton2.setOnClickListener(v -> {
            Intent photoIntent = new Intent(Intent.ACTION_PICK);
            photoIntent.setType("image/*");
            startActivityForResult(photoIntent, 1);
        });

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditInformation_PH.this, HomePhActivity.class);
                startActivity(intent);
            }
        });
        binding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditInformation_PH.this);
                builder.setTitle("Xác nhận đăng xuất");
                builder.setMessage("Bạn có chắc chắn muốn đăng xuất không?");

                builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        HomePhActivity.phuHuynh =null;
                        Intent intent=new Intent(EditInformation_PH.this, Login.class);
                        startActivity(intent);
                    }
                });

                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss(); // Đóng hộp thoại khi người dùng chọn hủy
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

    }

    //
    public void changePhone() {

        if (TextUtils.isEmpty(binding.edtPhone.getText())|| TextUtils.isEmpty(binding.edtName.getText())) {
            Toast.makeText(this, "Số điện thoại không được để trống.", Toast.LENGTH_SHORT).show();
        } else {
            FirebaseDatabase.getInstance().getReference("phuhuynh/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/ten").setValue(binding.edtName.getText().toString());
            //   FirebaseDatabase.getInstance().getReference("giaovien/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/ten").setValue(binding.edtName.getText().toString());
            Toast.makeText(this, "Cập nhật thành công.", Toast.LENGTH_SHORT).show();
        }
    }

    private void showUserData() {
        binding.edtName.setText(phuHuynh.getTen());
        binding.edtMSPH.setText(phuHuynh.getMSPH());
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
            binding.imgAvt.setImageBitmap(bitmap);
            SaveImage();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
                    Toast.makeText(EditInformation_PH.this, "Upload Successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(EditInformation_PH.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateProfilePicture(String url,String tennew ) {
        FirebaseDatabase.getInstance().getReference("phuhuynh/" + phuHuynh.getMSPH() + "/urlAva").setValue(url);

    }
    private void setLocaleFromDatabase(String selectedLanguage) {
        Configuration config = getResources().getConfiguration();
        Locale locale = new Locale(selectedLanguage);
        config.setLocale(locale);
        // Tạo một Context mới với Configuration mới
        Context newContext = createConfigurationContext(config);
        // Cập nhật ngôn ngữ cho ứng dụng
        Resources resources = newContext.getResources();
        getResources().updateConfiguration(config, resources.getDisplayMetrics());
    }
}