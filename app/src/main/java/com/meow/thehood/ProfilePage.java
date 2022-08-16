package com.meow.thehood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.meow.thehood.Models.PostData;
import com.meow.thehood.Models.ProfileData;
import com.squareup.picasso.Picasso;

public class ProfilePage extends AppCompatActivity {

    PostData postData;
    ShapeableImageView profilepfp;
    ImageView star;
    TextView fullname, username, bio, posts, following, followers;
    CardView follow;
    FirebaseDBManager firebaseDBManager = new FirebaseDBManager(this, "profiles");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        profilepfp = findViewById(R.id.profilepfp);
        fullname = findViewById(R.id.fullname);
        username = findViewById(R.id.username);
        bio = findViewById(R.id.bio);
        posts = findViewById(R.id.posts);
        following = findViewById(R.id.following);
        followers = findViewById(R.id.followers);
        star = findViewById(R.id.star);
        follow = findViewById(R.id.follow);

        postData = (PostData) getIntent().getSerializableExtra("data");

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Getting Profile...");
        progressDialog.show();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("profiles");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    ProfileData profileData = dataSnapshot.getValue(ProfileData.class);
                    if (postData.getUsername().equals(profileData.getUsername())) {
                        fullname.setText(profileData.getName());
                        username.setText(profileData.getUsername());
                        bio.setText(profileData.getBio());
                        posts.setText(profileData.getPosts() + "");
                        followers.setText(profileData.getFollowers() + "");
                        following.setText(profileData.getFollowing() + "");
                        if (Uri.parse(profileData.getPfp()) != null) Picasso.get().load(Uri.parse(profileData.getPfp())).into(profilepfp);
                        if (profileData.isVerified()) star.setImageResource(R.drawable.verified);
                    }
                }
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                firebaseDBManager.followSomeone(postData.getUsername());
            }
        });
    }
}