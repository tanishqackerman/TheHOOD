package com.meow.thehood;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.meow.thehood.Models.ChatList;
import com.meow.thehood.Models.PostData;
import com.meow.thehood.Models.ProfileData;

public class LikedByPage extends AppCompatActivity implements SelectListener{

    RecyclerView likedrv;
    FirebaseDBManager firebaseDBManager;
    PostData postData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liked_by_page);

        postData = (PostData) getIntent().getSerializableExtra("data");

        likedrv = findViewById(R.id.likedrv);
        firebaseDBManager = new FirebaseDBManager(this, "posts", this);
        firebaseDBManager.viewWhoLikedDB(likedrv, postData);
    }

    @Override
    public void onPFPClicked(PostData postData) {

    }

    @Override
    public void onStoryClicked(PostData postData) {

    }

    @Override
    public void onPostClicked(PostData postData) {

    }

    @Override
    public void onLiked(PostData postData) {

    }

    @Override
    public void onUnLiked(PostData postData) {

    }

    @Override
    public void viewWhoLiked(PostData postData) {

    }

    @Override
    public void onChatClicked(ChatList chatList, ProfileData profileData) {

    }
}