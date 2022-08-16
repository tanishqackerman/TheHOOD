package com.meow.thehood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.meow.thehood.Models.PostData;
import com.meow.thehood.Models.ProfileData;
import com.squareup.picasso.Picasso;

public class PostClickedPage extends AppCompatActivity {

    ShapeableImageView pfp;
    ImageView postpic, star;
    TextView username, caption, time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_clicked_page);

        PostData data = (PostData) getIntent().getSerializableExtra("data");

        pfp = findViewById(R.id.pfp);
        postpic = findViewById(R.id.postpic);
        star = findViewById(R.id.star);
        username = findViewById(R.id.username);
        caption = findViewById(R.id.caption);
        time = findViewById(R.id.posttime);

        if (Uri.parse(data.getPfp()) != null) Picasso.get().load(Uri.parse(data.getPfp())).into(pfp);
        if (Uri.parse(data.getPostpic()) != null) Picasso.get().load(Uri.parse(data.getPostpic())).into(postpic);
        username.setText(data.getUsername());
        caption.setText(data.getCaption());
        time.setText(data.getTime());
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("profiles");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    ProfileData profileData = dataSnapshot.getValue(ProfileData.class);
                    if (profileData.getUsername().equals(username.getText().toString())) {
                        if (profileData.isVerified()) star.setImageResource(R.drawable.verified);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}