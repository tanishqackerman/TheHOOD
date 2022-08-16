package com.meow.thehood;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.meow.thehood.AdaptersAndViewHolders.PostAdapter;
import com.meow.thehood.AdaptersAndViewHolders.StoryAdapter;
import com.meow.thehood.Models.ChatList;
import com.meow.thehood.Models.PostData;
import com.meow.thehood.Models.ProfileData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeScreen#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeScreen extends Fragment implements SelectListener{

    RecyclerView postrv, storyrv;
    PostAdapter postAdapter;
    StoryAdapter storyAdapter;
    List<PostData> list = new ArrayList<PostData>();
    FirebaseDBManager firebaseDBManager;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeScreen() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeScreen.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeScreen newInstance(String param1, String param2) {
        HomeScreen fragment = new HomeScreen();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home_screen, container, false);

        postrv = v.findViewById(R.id.postrv);
        storyrv = v.findViewById(R.id.storyrv);

        firebaseDBManager = new FirebaseDBManager(getContext(), "posts", this);
        firebaseDBManager.viewpost(postrv);

        return v;
    }

    @Override
    public void onPFPClicked(PostData postData) {
        startActivity(new Intent(getContext(), ProfilePage.class).putExtra("data", postData));
    }

    @Override
    public void onStoryClicked(PostData postData) {

    }

    @Override
    public void onPostClicked(PostData postData) {

    }

    @Override
    public void onLiked(PostData postData) {
        postData.setLikes(postData.getLikes() + 1);
        HashMap<String, Object> hm = new HashMap<String, Object>();
        hm.put("likes", postData.getLikes());
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("posts");
        databaseReference.child(postData.getPostid()).updateChildren(hm).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (!task.isSuccessful()) Toast.makeText(getContext(), "Like Failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onUnLiked(PostData postData) {
        postData.setLikes(postData.getLikes() - 1);
        HashMap<String, Object> hm = new HashMap<String, Object>();
        hm.put("likes", postData.getLikes());
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("posts");
        databaseReference.child(postData.getPostid()).updateChildren(hm).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (!task.isSuccessful()) Toast.makeText(getContext(), "Failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void viewWhoLiked(PostData postData) {
        startActivity(new Intent(getContext(), LikedByPage.class).putExtra("data", postData));
    }

    @Override
    public void onChatClicked(ChatList chatList, ProfileData profileData) {

    }
}