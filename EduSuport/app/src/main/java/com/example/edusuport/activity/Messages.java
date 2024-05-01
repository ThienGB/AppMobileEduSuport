package com.example.edusuport.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.edusuport.R;
import com.example.edusuport.adapter.MessagesAdapter;
import com.example.edusuport.model.MemoryData;
import com.example.edusuport.model.MessageList;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Messages extends AppCompatActivity {
    private final List<MessageList> messageLists = new ArrayList<>();
    private String phone="0942523074";
    private String name="Trinh thu phuòng";
    private String idCurUse="1";
    private int unseenMsg = 0;
    private String lastMsg = "";
    private String chatKey="";
    private boolean dataSet = false;
    private RecyclerView messageRecyclerView;
    private MessagesAdapter messageAdapter;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_message);
        final CircleImageView profilePicture = findViewById(R.id.profilePicture);
        messageRecyclerView = findViewById(R.id.msgRecylerView);

//        get intent data from login
        phone = getIntent().getStringExtra("phone");
        name = getIntent().getStringExtra("name");
      //  idCurUse = getIntent().getStringExtra("idCurUse");
//
        messageRecyclerView.setHasFixedSize(true);
        messageRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//
        //set adapter to recyclerview
        messageAdapter = new MessagesAdapter(messageLists,Messages.this);
        messageRecyclerView.setAdapter(messageAdapter);
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading.....");
        progressDialog.show();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                unseenMsg = 0;
                lastMsg= "";
                chatKey= "";
                for (DataSnapshot dataSnapshot : snapshot.child("giaovien").getChildren()){
                    final String Idpartner = dataSnapshot.getKey();
                    if (!Idpartner.equals(idCurUse)){
                        final String getName = dataSnapshot.child("ten").getValue(String.class);
                        final String getPicture = dataSnapshot.child("urlAva").getValue(String.class);
                        MessageList messageList = new MessageList( Idpartner,getName,"",lastMsg,getPicture,unseenMsg, databaseReference.getKey());
                        messageLists.add(messageList);

                    }
                }
               // messageRecyclerView.setAdapter(new MessagesAdapter(messageLists, Messages.this));
                messageRecyclerView.setAdapter(new MessagesAdapter(messageLists, Messages.this));
                messageAdapter.notifyDataSetChanged();Log.d("CHatchit id ",String.valueOf(messageLists));
                progressDialog.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}