package com.example.edusuport.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TabHost;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.edusuport.R;
import com.example.edusuport.adapter.NhomTheAdapter;
import com.example.edusuport.adapter.TaiLieuHocTapAdapter;
import com.example.edusuport.controllers.DangTaiTaiLieuController;
import com.example.edusuport.model.NhomThe;
import com.example.edusuport.model.TaiLieuHocTap;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ServerValue;

import java.util.ArrayList;

public class DangTaiTaiLieu_MonActivity extends AppCompatActivity {

    TextView textTenMonHoc;
    ListView lvFile;
    GridView gdCard;
    ImageButton imgBack;
    FloatingActionButton btnuploadfileTL,btnAddFlashCard;
    SearchView filterFile;
    TaiLieuHocTapAdapter taiLieuHocTapAdapter;
    ArrayList<TaiLieuHocTap> listf=new ArrayList<TaiLieuHocTap>();
    NhomTheAdapter nhomTheAdapter;
    String idMon, tenFile;
    Intent data=null;

    DangTaiTaiLieuController dangTaiTaiLieuController=new DangTaiTaiLieuController();;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_tai_tai_lieu_mon);
        loadTabs();
        doFormWidgetsFile();
        doFormWidgetsFlasCard();
        clickBack();
    }

    private void clickBack(){
        imgBack=(ImageButton) findViewById(R.id.back_TaiLieuMain);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DangTaiTaiLieu_MonActivity.this,DangTaiTaiLieuActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    public void loadTabs(){

        textTenMonHoc = (TextView) findViewById(R.id.textTenMonHoc);
        idMon=getIntent().getStringExtra("idMon");
        dangTaiTaiLieuController.getMonHoc_idmon(idMon, new DangTaiTaiLieuController.DataRetrievedCallback_String() {
            @Override
            public void onDataRetrieved(String tenmon) {
                textTenMonHoc.setText(tenmon);
            }
        });

        Log.d("idMon", String.valueOf(idMon));
        //Lấy Tabhost id ra trước /
        final TabHost tab =(TabHost) findViewById(R.id.tabHost_tailieu);
        tab.setup();
        TabHost.TabSpec spec;
        spec= tab.newTabSpec("t1");
        spec.setContent(R.id.tab1);
        spec.setIndicator("File");
        tab.addTab(spec);
        spec= tab.newTabSpec("t2");
        spec.setContent(R.id.tab2);
        spec.setIndicator ("FlashCard");
        tab.addTab(spec);
        tab.setCurrentTab(0);
        tab.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String arg0) {
                String s = "Tab tag="+arg0+"; index=" + tab.getCurrentTab();
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void doFormWidgetsFile()
    {

        lvFile=(ListView) findViewById(R.id.lv_fileTaiLieu);
        btnuploadfileTL=(FloatingActionButton) findViewById(R.id.btn_uploadfileTL);
        filterFile =(SearchView) findViewById(R.id.filterFile);
        lvFile.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(DangTaiTaiLieu_MonActivity.this, "huhu", Toast.LENGTH_SHORT).show();
                TextView tenfile=view.findViewById(R.id.name_tailieu);
                ImageView hinhfile=view.findViewById(R.id.ic_fileType);
                showBottomSheet(tenfile.getText().toString(),hinhfile.getDrawable());

            }
        });

        filterFile.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<TaiLieuHocTap> temp=new ArrayList<TaiLieuHocTap>();
                for(TaiLieuHocTap file: listf){
                    if(file.getTenTaiLieu().toLowerCase().contains(newText.toLowerCase())){
                        temp.add(file);
                    }
                }
                taiLieuHocTapAdapter = new TaiLieuHocTapAdapter(DangTaiTaiLieu_MonActivity.this,R.layout.item_tab_listtailieu, temp );
                lvFile.setAdapter(taiLieuHocTapAdapter);
                taiLieuHocTapAdapter.notifyDataSetChanged();
                return false;
            }
        });
        dangTaiTaiLieuController.getListViewTaiLieu(idMon, "1", new DangTaiTaiLieuController.DataRetrievedCallback_File() {
            @Override
            public void onDataRetrieved(ArrayList<TaiLieuHocTap> FileList) {
                listf=FileList;
                taiLieuHocTapAdapter = new TaiLieuHocTapAdapter(DangTaiTaiLieu_MonActivity.this,R.layout.item_tab_listtailieu, listf );
                lvFile.setAdapter(taiLieuHocTapAdapter);
            }

        });

        btnuploadfileTL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.app.AlertDialog.Builder builder = new AlertDialog.Builder(DangTaiTaiLieu_MonActivity.this);
                LayoutInflater layoutInflater = LayoutInflater.from(DangTaiTaiLieu_MonActivity.this);
                builder.setTitle("Thêm tài liệu: ");

                final View alertView = layoutInflater.inflate(R.layout.activity_click_upfile_gv, null);
                builder.setView(alertView);

                EditText txtTenFile=(EditText) alertView.findViewById(R.id.txtTenFile);
                LinearLayout upFile=(LinearLayout) alertView.findViewById(R.id.btn_upNewfile);
                tenFile=txtTenFile.getText().toString();


                builder.setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(data!=null && !txtTenFile.getText().toString().equals("")){
                            String ext="."+getFileExtension(data.getData());
                            dangTaiTaiLieuController.createNewFileTaiLieu_idmon(idMon, txtTenFile.getText().toString(), data.getData(), ext, "1", new DangTaiTaiLieuController.UploadCallback() {
                                @Override
                                public void onUploadComplete() {
                                    listf.clear();
                                    dangTaiTaiLieuController.getListViewTaiLieu(idMon, "1", new DangTaiTaiLieuController.DataRetrievedCallback_File() {

                                     @Override
                                        public void onDataRetrieved(ArrayList<TaiLieuHocTap> FileList) {

                                            listf = FileList;
                                            taiLieuHocTapAdapter.notifyDataSetChanged();
                                        }
                                    });
                                    Toast.makeText(DangTaiTaiLieu_MonActivity.this, "Đăng thành công", Toast.LENGTH_LONG).show();
                                }

                                @Override
                                public void onUploadFailed(String errorMessage) {

                                }
                            });
                            data=null;
                            // Gọi callback trực tiếp khi việc thêm đã hoàn tất

                        }
                        else {
                            Toast.makeText(DangTaiTaiLieu_MonActivity.this, "Nhập chưa đầy đủ", Toast.LENGTH_LONG).show();
                        }

                    }
                });

                upFile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent=new Intent();
                        intent.setType("*/*");
                        intent.setAction(intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent,"Select PDF File"),1);
                    }
                });


                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });


    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        String mimeType = contentResolver.getType(uri);
        String fileExtension = null;
        if (mimeType != null) {
            fileExtension = mimeTypeMap.getExtensionFromMimeType(mimeType);
        }
        return fileExtension;
    }
    public void showBottomSheet(String tenFile,Drawable hinhfile){
        final Dialog dialog=new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.botsheet_tailieufile);

        TableRow sua=dialog.findViewById(R.id.bot_chinhsuaFile);
        TableRow xoa=dialog.findViewById(R.id.bot_xoaFile);
        TableRow tai=dialog.findViewById(R.id.bot_taiFile);
        TextView ten=dialog.findViewById(R.id.bot_nameFile);
        ImageView hinh=dialog.findViewById(R.id.bot_hinhfile);

        ten.setText(tenFile);
        hinh.setImageDrawable(hinhfile);
        sua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        tai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations= com.google.android.material.R.style.Animation_Design_BottomSheetDialog;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK && data!=null && data.getData()!=null  ){
            final ProgressDialog progressDialog=new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            this.data=data;
            progressDialog.dismiss();

        }


    }

    public void doFormWidgetsFlasCard()
    {
        gdCard=(GridView) findViewById(R.id.grid_flashcard);
        ArrayList<NhomThe> list=new ArrayList<NhomThe>();
        list.add(new NhomThe( "1","Thẻ vip","2"));
        list.add(new NhomThe( "2","300 bài code thiếu nhi","2"));
        list.add(new NhomThe( "3","Học soạn giáo án cùng cô giáo","2"));
        list.add(new NhomThe( "4","Kiếm tiền không khó","2"));
        list.add(new NhomThe( "5","Kiếm tiền khó","2"));
        list.add(new NhomThe( "6","Thẻ vip","2"));



        nhomTheAdapter = new NhomTheAdapter(DangTaiTaiLieu_MonActivity.this,R.layout.item_tab_flashcard, list );
        gdCard.setAdapter(nhomTheAdapter);

    }

}