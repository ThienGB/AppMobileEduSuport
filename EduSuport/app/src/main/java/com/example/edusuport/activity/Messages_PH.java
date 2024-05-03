package com.example.edusuport.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.SearchView;

import com.example.edusuport.R;
import com.example.edusuport.adapter.LopHoc_IdGV_Nav_Adapter;
import com.example.edusuport.adapter.MessagesAdapter;
import com.example.edusuport.adapter.ViewHolderClick;
import com.example.edusuport.controllers.LopHocController;
import com.example.edusuport.controllers.MessController;
import com.example.edusuport.model.LopHoc;
import com.example.edusuport.model.MessageList;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Messages_PH extends AppCompatActivity {
    public List<MessageList> messageLists = new ArrayList<>();
    ImageView xemthemlophoc;
    private RadioGroup rb_role;String textRole="HS";
    private String phone="0942523074";
    private String name="Trinh thu phuòng";
    private String idCurUse="21110611PH";
    private int unseenMsg = 0;
    private String lastMsg = "";
    private String chatKey="";
    private boolean dataSet = false;
    private RecyclerView messageRecyclerView;
    SearchView filter;
    private MessagesAdapter messageAdapter;
    ValueEventListener eventListener;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    String IDLop = "10A1";

    String IDGiaoVien="1";
    MessController messController=new MessController();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_ph);
        final CircleImageView profilePicture = findViewById(R.id.profilePicture);
        messageRecyclerView = findViewById(R.id.msgRecylerView);
        filter= findViewById(R.id.filter);

//        get intent data from login
        phone = getIntent().getStringExtra("phone");
        name = getIntent().getStringExtra("name");
        //  idCurUse = getIntent().getStringExtra("idCurUse");
//
        chooseRole();
        filter.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<MessageList> temp=new ArrayList<MessageList>();
                for(MessageList file: messageLists){
                    if(file.getName().toLowerCase().contains(newText.toLowerCase()) ||file.getIdpartner().toLowerCase().contains(newText.toLowerCase())){
                        temp.add(file);
                    }
                }
                messageAdapter = new MessagesAdapter(temp,Messages_PH.this);
                messageRecyclerView.setAdapter(messageAdapter);
                messageAdapter.notifyDataSetChanged();
                return false;
            }
        });
        messageRecyclerView.setHasFixedSize(true);
        messageRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//
        //set adapter to recyclerview

        messageAdapter = new MessagesAdapter(messageLists,Messages_PH.this);
        messageRecyclerView.setAdapter(messageAdapter);
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading.....");
        progressDialog.show();
        messageLists.clear();

        messController.loadPartner_idCuren(idCurUse,messageRecyclerView,Messages_PH.this);
        progressDialog.dismiss();

        messageAdapter.updateData(messageLists);


    }

    private void chooseRole() {
        rb_role=findViewById(R.id.radioBtn_role);
        messageLists.clear();
        xemthemlophoc = (ImageView) findViewById(R.id.xemthem_lophoc);
        xemthemlophoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messageLists.clear();
                Log.d("gizzz","hehe");
                messController.loadPartner_idCuren(idCurUse,messageRecyclerView,Messages_PH.this);

            }
        });
        rb_role.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.radioBtn_HS){
                    reloadCycleHS(idCurUse,IDLop);
                } else {
                    reloadCycleGV(idCurUse,IDLop);
                }

            }
        });
    }
    private void reloadCycleHS(String idCurUse,String idLopHoc){
        messageLists.clear();
        messController.getHS_idPH(idCurUse, new MessController.DataRetrievedCallback_Message() {
            @Override
            public void onDataRetrieved(MessageList MessageList) {
                messageLists.add(MessageList);
                messageAdapter = new MessagesAdapter(messageLists,Messages_PH.this);
                messageRecyclerView.setAdapter(messageAdapter);
                Log.d("List mới",String.valueOf(messageLists));
            }
        });


    }

    private void reloadCycleGV(String idCurUse,String idLopHoc){
        messageLists.clear();
        messController.getGV(idCurUse,idLopHoc, new MessController.DataRetrievedCallback_Message() {
            @Override
            public void onDataRetrieved(MessageList MessageList) {
                messageLists.add(MessageList);
                messageAdapter = new MessagesAdapter(messageLists,Messages_PH.this);
                messageRecyclerView.setAdapter(messageAdapter);
                Log.d("List mới",String.valueOf(messageLists));
            }
        });


    }


}