package com.example.edusuport.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.edusuport.adapter.ChatAdapter;
import com.example.edusuport.adapter.MessagesAdapter;
import com.example.edusuport.model.ChatList;
import com.example.edusuport.model.MemoryData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;

import com.example.edusuport.R;

public class Chat extends AppCompatActivity {

    private List<ChatList> chatLists = new ArrayList<>();
    private String chatKey="";
    String getUserId = "";
    private RecyclerView chattingRecyclerView;
    private ChatAdapter chatAdapter;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private boolean loadingFirstTime=true;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_chat);

        final ImageView btnBack = findViewById(R.id.btnback);
        final TextView name = findViewById(R.id.username);
        final EditText msgEditText = findViewById(R.id.msgEditText);
        final CircleImageView profilePic = findViewById(R.id.profilePicture);
        final ImageView sendBtn = findViewById(R.id.btnSend);

        chattingRecyclerView = findViewById(R.id.chattingRecylerView);
        final String getName = getIntent().getStringExtra("name");
        final String getProfile = getIntent().getStringExtra("profilePic");
        chatKey = getIntent().getStringExtra("chat_key");
        final String getId = getIntent().getStringExtra("id");
        final String role = getIntent().getStringExtra("role");

        //get user ID
        getUserId = "21110611PH";//MemoryData.getData(Chat.this);
        name.setText(getName);
        if(!getProfile.isEmpty()){
            Picasso.get().load(getProfile).into(profilePic);
        }
        else {
            Picasso.get().load(R.drawable.profile).into(profilePic);
        }


        chattingRecyclerView.setHasFixedSize(true);
        chattingRecyclerView.setLayoutManager(new LinearLayoutManager(Chat.this));

        chatAdapter = new ChatAdapter(chatLists, Chat.this,getUserId);
        chattingRecyclerView.setAdapter(chatAdapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (chatKey.isEmpty()) {
                    chatKey = "1";
                    if (snapshot.hasChild("chat")) {
                        chatKey = String.valueOf(snapshot.child("chat").getChildrenCount() + 1);

                    }

                }
                if (snapshot.hasChild("chat")){
                    if (snapshot.child("chat").child(chatKey).hasChild("messages")){

                        chatLists.clear();
                        for (DataSnapshot msgSnapshot : snapshot.child("chat").child(chatKey).child("messages").getChildren()){
                            if (msgSnapshot.hasChild("msg") && msgSnapshot.hasChild("ID")){
                                final String msgTimestamps = msgSnapshot.getKey();
                                final String getPhone = msgSnapshot.child("ID").getValue(String.class);
                                final String getMsg = msgSnapshot.child("msg").getValue(String.class);

                                Timestamp timestamp = new Timestamp(Long.parseLong(msgTimestamps));
                                    Date date = new Date(timestamp.getTime());
                                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                                    SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("hh:mm aa", Locale.getDefault());

                                    ChatList chatList = new ChatList(getPhone, getName, getMsg, simpleDateFormat.format(date),simpleTimeFormat.format(date));
                                    chatLists.add(chatList);
//                                    if (loadingFirstTime || Long.parseLong(msgTimestamps) > Long.parseLong(MemoryData.getLastMsgTs(Chat.this, chatKey))){
//
//                                        loadingFirstTime=false;
//
//                                        MemoryData.saveLastMsg(msgTimestamps, chatKey, Chat.this);
//                                        chatAdapter.updateChatList(chatLists);
//                                        chattingRecyclerView.scrollToPosition(chatLists.size() - 1);
//                                    }
                                }

                            }
                        }
                    }

                    chatAdapter.notifyDataSetChanged();
                    int lastItemPosition = chatAdapter.getItemCount() - 1;
                    chattingRecyclerView.scrollToPosition(lastItemPosition);
                }



                @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String getTextMsg = msgEditText.getText().toString();

                //get current timestamps
                 String currentTImestam = String.valueOf(System.currentTimeMillis()).substring(0, 10);


                databaseReference.child("chat").child(chatKey).child("users_1").setValue(getUserId);
                databaseReference.child("chat").child(chatKey).child("users_2").setValue(getId);
                databaseReference.child("chat").child(chatKey).child("role_1").setValue("phuhuynh");
                databaseReference.child("chat").child(chatKey).child("role_2").setValue(role);
                databaseReference.child("chat").child(chatKey).child("messages").child(currentTImestam).child("msg").setValue(getTextMsg);
                databaseReference.child("chat").child(chatKey).child("messages").child(currentTImestam).child("ID").setValue(getUserId);
                msgEditText.setText("");

            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               finish();
            }
        });

    }

}