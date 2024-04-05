package com.example.edusuport.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.edusuport.R;
import com.example.edusuport.model.TheLat;

import java.util.ArrayList;

public class TheLatAdapterGV extends ArrayAdapter {
    Activity context;
    private Context mContext;
    int resource;
    ArrayList<TheLat> List= new ArrayList<TheLat>();

    public TheLatAdapterGV(Context context, int resource, ArrayList<TheLat> list) {
        super(context, resource, list);
        List = list;
        mContext = context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View customView= convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        customView = inflater.inflate(R.layout.item_main_flashcard, null);
        //  ImageView imgHinh = (ImageView)  customView.findViewById(R.id.imgHinh);
        TextView cauhoi =(TextView) customView.findViewById(R.id.cauhoi_fc);
        TextView traloi =(TextView) customView.findViewById(R.id.traloi_fc);
        cauhoi.setText(List.get(position).getCauHoi());
        traloi.setText(List.get(position).getCauTraLoi());



        ImageButton btn= customView.findViewById(R.id.btn_moreOptFC);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopUpDialog(position);
            }
        });
        return customView;
    }
    private void showPopUpDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Thẻ: "+ List.get(position).getCauHoi());

        // Thêm các lựa chọn vào danh sách
        String[] options = {"Chỉnh sửa thẻ", "Xóa thẻ" };
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Xử lý khi một lựa chọn được chọn
                String selectedOption = options[which];
                Toast.makeText(mContext, "Bạn đã chọn: " + selectedOption, Toast.LENGTH_SHORT).show();
            }
        });

        // Hiển thị AlertDialog
        builder.create().show();
    }


}
