package com.example.edusuport.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.edusuport.R;
import com.example.edusuport.model.TaiLieuHocTap;

import java.util.ArrayList;

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
        TextView txtTen =(TextView) customView.findViewById(R.id.name_tailieu);
        TextView txtDate =(TextView) customView.findViewById(R.id.date_dangtailieu);
        txtTen.setText(List.get(position).getTenTaiLieu());
        txtDate.setText("Upload ngày"+List.get(position).getThoiGian());

        ImageButton btn= customView.findViewById(R.id.btn_moreOptTaiLieuFile);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopUpDialog();
            }
        });
        return customView;
    }
    private void showPopUpDialog() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
//        builder.setTitle("Chọn lựa");
//
//        // Thêm các lựa chọn vào danh sách
//        String[] options = {"Lựa chọn 1", "Lựa chọn 2", "Lựa chọn 3"};
//        builder.setItems(options, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                // Xử lý khi một lựa chọn được chọn
//                String selectedOption = options[which];
//                Toast.makeText(mContext, "Bạn đã chọn: " + selectedOption, Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        // Hiển thị AlertDialog
//        builder.create().show();


    }


}
