package com.example.edusuport.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.edusuport.R;
import com.example.edusuport.activity.DangTaiTaiLieu_MonActivity;
import com.example.edusuport.controllers.DangTaiTaiLieuController;
import com.example.edusuport.model.MonHoc;
import com.example.edusuport.model.TaiLieuHocTap;

import java.util.ArrayList;
import java.util.Objects;

public class TaiLieuHocTapAdapter  extends ArrayAdapter {
    Activity context;
    private Context mContext;
    int resource;
    ArrayList<TaiLieuHocTap> List= new ArrayList<TaiLieuHocTap>();
    DangTaiTaiLieuController dt=new DangTaiTaiLieuController();

    public TaiLieuHocTapAdapter(Context context, int resource, ArrayList<TaiLieuHocTap> list) {
        super(context, resource, list);
        List = list;
        mContext = context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View customView= convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        customView = inflater.inflate(R.layout.item_tab_listtailieu, null);
        //  ImageView imgHinh = (ImageView)  customView.findViewById(R.id.imgHinh);
        TextView id=(TextView) customView.findViewById(R.id.idTaiLieu);
        ImageView img=(ImageView) customView.findViewById(R.id.ic_fileType);
        TextView txtTen =(TextView) customView.findViewById(R.id.name_tailieu);
        TextView txtDate =(TextView) customView.findViewById(R.id.date_dangtailieu);
        txtTen.setText(List.get(position).getTenTaiLieu());
        id.setText(List.get(position).getIdTaiLieu());
        txtDate.setText("Upload ngày"+List.get(position).getThoiGian());
        String ext= List.get(position).getFileType();

        if(Objects.equals(ext, ".pdf")){
            img.setImageResource(R.drawable.icon_pdf);
        } else if (Objects.equals(ext, ".pptx")) {
            img.setImageResource(R.drawable.icon_ppt);
        }
        else if (Objects.equals(ext, ".xlsx") || Objects.equals(ext, ".xls")) {
            img.setImageResource(R.drawable.icon_excel);
        }
        else if (Objects.equals(ext, ".mp4")) {
            img.setImageResource(R.drawable.icon_mp4);
        }
        else if (Objects.equals(ext, ".doc") || Objects.equals(ext, ".docx")) {
            img.setImageResource(R.drawable.icon_word);
        }
        else{

            img.setImageResource(R.drawable.icon_imgfile);
        }
        Log.d("test icon",ext.toString());

        ImageView moreOption= (ImageView) customView.findViewById(R.id.btn_moreOptFile);
        moreOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showBottomSheet(txtTen.getText().toString(),img.getDrawable(),id.getText().toString(),ext);
            }
        });

        customView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showBottomSheet(txtTen.getText().toString(),img.getDrawable(),id.getText().toString(),ext);
                return false;
            }
        });
        return customView;
    }


    public void showBottomSheet(String tenFile, Drawable hinhfile,String id,String ext){
        final Dialog dialog=new Dialog(mContext);
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
                android.app.AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                LayoutInflater layoutInflater = LayoutInflater.from(mContext);
                builder.setTitle("Thay đổi tên tài liệu: ");

                final View alertView = layoutInflater.inflate(R.layout.activity_sua_tenfiletl, null);
                builder.setView(alertView);

                EditText txtTenFile=(EditText) alertView.findViewById(R.id.sua_txtTenFile);
                txtTenFile.setText(tenFile);
                txtTenFile.setTextColor(ContextCompat.getColor(mContext, R.color.black_low_emp));
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogmini, int which) {
                        String tenNew= txtTenFile.getText().toString();
                        editTL(id,tenFile,tenNew);

                        dialog.dismiss();

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Xác nhận xóa");
                builder.setMessage("Bạn có chắc chắn muốn xóa không?");

                // Nút xác nhận
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogmini, int which) {
                        // Gọi hàm deleteTL() để xóa tài liệu
                        deleteTL(id, ext);
                        dialogmini.dismiss();
                        dialog.dismiss();
                    }
                });

                // Nút hủy
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss(); // Đóng hộp thoại xác nhận
                    }
                });

                // Hiển thị hộp thoại xác nhận
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }

        });
        tai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadFile(id);
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations= com.google.android.material.R.style.Animation_Design_BottomSheetDialog;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    public void deleteTL(String id,String ext){

        dt.deleteTaiLieu_idTaiLieu(id, ext, new DangTaiTaiLieuController.UploadCallback() {
            @Override
            public void onUploadComplete() {
                Toast.makeText(mContext, "Hoàn tất xóa", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUploadFailed(String errorMessage) {
                Toast.makeText(mContext, "Xóa thất bại", Toast.LENGTH_SHORT).show();
            }
        },mContext);
        ((DangTaiTaiLieu_MonActivity) mContext).reLoadListf();

    }

    public void editTL(String id,String tenOld,String tenNew){

        Toast.makeText(mContext, tenNew, Toast.LENGTH_SHORT).show();
        dt.editTenTaiLieu(id, tenNew, new DangTaiTaiLieuController.UploadCallback() {
            @Override
            public void onUploadComplete() {
                Toast.makeText(mContext, "Đã thay đổi tên tài liệu", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUploadFailed(String errorMessage) {
                Toast.makeText(mContext, "Thay đổi tên thất bại", Toast.LENGTH_SHORT).show();
            }
        });
        ((DangTaiTaiLieu_MonActivity) mContext).reLoadListf();
    }
    public void downloadFile(String id){

        dt.getTaiLieu_idTaiLieu(id, new DangTaiTaiLieuController.DataRetrievedCallback_String() {
            @Override
            public void onDataRetrieved(String url) {
                Toast.makeText(mContext, url, Toast.LENGTH_SHORT).show();
                Intent intent= new Intent(Intent.ACTION_VIEW);
                intent.setType("*/*");
                intent.setData(Uri.parse(url));
                mContext.startActivity(intent);
            }
        });


    }

}
