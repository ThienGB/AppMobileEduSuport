package com.example.edusuport.activity;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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
    private String phone;
    private String name;
    private String id;
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
        //messageRecyclerView = findViewById(R.id.msgRecylerView);

//        get intent data from login
        phone = getIntent().getStringExtra("phone");
        name = getIntent().getStringExtra("name");
        id = getIntent().getStringExtra("id");
//
//        messageRecyclerView.setHasFixedSize(true);
//        messageRecyclerView.setLayoutManager(new LinearLayoutManager(this));
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
                messageLists.clear();
                unseenMsg = 0;
                lastMsg= "";
                chatKey= "";
                for (DataSnapshot dataSnapshot : snapshot.child("users").getChildren()){
                    final String getPhone = dataSnapshot.getKey();
                    dataSet = false;
                    if (!getPhone.equals(phone)){
                        final String getName = dataSnapshot.child("name").getValue(String.class);
                        final String getPicture = dataSnapshot.child("profilePic").getValue(String.class);

                        databaseReference.child("chat").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                int getChatCounts = (int)snapshot.getChildrenCount();

                                if (getChatCounts > 0) {
                                    for (DataSnapshot dataSnapshot1 : snapshot.getChildren()){
                                        final String getKey = dataSnapshot1.getKey();
                                        chatKey = getKey;

                                        if (dataSnapshot1.hasChild("user_1") && dataSnapshot1.hasChild("user_2") && dataSnapshot1.hasChild("messages")){
                                            final String getUserOne = dataSnapshot1.child("user_1").getValue(String.class);
                                            final String getUserTwo = dataSnapshot1.child("user_2").getValue(String.class);

                                            if ((getUserOne.equals(getPhone) && getUserTwo.equals(phone)) || (getUserOne.equals(phone) && getUserTwo.equals(getPhone))){
                                                for (DataSnapshot chatDataSnapshot : dataSnapshot1.child("messages").getChildren()){
                                                    final long getMessageKey = Long.parseLong(chatDataSnapshot.getKey());
                                                    final long getLastSeenMessage = Long.parseLong(MemoryData.getLastMsgTs(Messages.this, getKey));

                                                    lastMsg = chatDataSnapshot.child("msg").getValue(String.class);
                                                    if (getMessageKey > getLastSeenMessage){
                                                        unseenMsg++;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }

                                if (dataSet){
                                    dataSet=true;
                                    MessageList messageList = new MessageList(getName, getPhone,lastMsg,getPicture,unseenMsg,chatKey);
                                    messageLists.add(messageList);
                                    messageAdapter.updateData(messageLists);
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }
                }
                messageRecyclerView.setAdapter(new MessagesAdapter(messageLists, Messages.this));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}