package com.example.edusuport.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.edusuport.R;
import com.example.edusuport.activity.DangTaiTaiLieu_MonActivity;
import com.example.edusuport.model.MonHoc;
import com.example.edusuport.model.TaiLieuHocTap;

import java.util.ArrayList;
import java.util.Objects;

public class TaiLieuHocTapAdapter  extends ArrayAdapter {
    Activity context;
    private Context mContext;
    int resource;
    ArrayList<TaiLieuHocTap> List= new ArrayList<TaiLieuHocTap>();

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
        ImageView img=(ImageView) customView.findViewById(R.id.ic_fileType);
        TextView txtTen =(TextView) customView.findViewById(R.id.name_tailieu);
        TextView txtDate =(TextView) customView.findViewById(R.id.date_dangtailieu);
        txtTen.setText(List.get(position).getTenTaiLieu());
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

        return customView;
    }




}
