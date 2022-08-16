package com.meow.thehood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.meow.thehood.AdaptersAndViewHolders.MessageAdapter;
import com.meow.thehood.Models.ChatList;
import com.meow.thehood.Models.Messages;
import com.meow.thehood.Models.ProfileData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChatPage extends AppCompatActivity {

    ShapeableImageView chatpfp;
    TextView chatusername;
    CardView send;
    EditText msg;
    String senderroom, recieverroom;
    List<Messages> msglist = new ArrayList<Messages>();
    MessageAdapter messageAdapter;
    RecyclerView chatrv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_page);

        getSupportActionBar().hide();

        ChatList chatList = (ChatList) getIntent().getSerializableExtra("data");
        ProfileData profileData = (ProfileData) getIntent().getSerializableExtra("profile");

        chatpfp = findViewById(R.id.chatpfp);
        chatusername = findViewById(R.id.chatusername);
        send = findViewById(R.id.send);
        msg = findViewById(R.id.msg);
        chatrv = findViewById(R.id.chatrv);

        messageAdapter = new MessageAdapter(this, msglist, profileData);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setStackFromEnd(true);
        chatrv.setLayoutManager(llm);
        chatrv.setAdapter(messageAdapter);

        senderroom = profileData.getUsername() + chatList.getUsername();
        recieverroom = chatList.getUsername() + profileData.getUsername();

        if (Uri.parse(chatList.getPfpchat()) != null) Picasso.get().load(Uri.parse(chatList.getPfpchat())).into(chatpfp);
        chatusername.setText(chatList.getUsername());

        DatabaseReference chatref = FirebaseDatabase.getInstance().getReference().child("chats").child(senderroom).child("messages");
        chatref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                msglist.clear();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    Messages messages = dataSnapshot.getValue(Messages.class);
                    msglist.add(messages);
                }
                messageAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msgsent = msg.getText().toString();
                if (msgsent.isEmpty()){
                    Toast.makeText(ChatPage.this, "Enter Something Meow!", Toast.LENGTH_SHORT).show();
                    return;
                }
                msg.setText("");
                Date date = new Date();
                Messages messages = new Messages(msgsent, profileData.getUsername(), date.getTime());
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                databaseReference.child("chats")
                        .child(senderroom)
                        .child("messages")
                        .push()
                        .setValue(messages).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                                reference.child("chats")
                                        .child(recieverroom)
                                        .child("messages")
                                        .push()
                                        .setValue(messages).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                            }
                                        });
                            }
                        });
            }
        });

    }
}