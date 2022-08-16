package com.meow.thehood;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.meow.thehood.AdaptersAndViewHolders.PostAdapter;
import com.meow.thehood.AdaptersAndViewHolders.StoryAdapter;
import com.meow.thehood.Models.ChatList;
import com.meow.thehood.Models.PostData;
import com.meow.thehood.Models.ProfileData;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExploreScreen#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExploreScreen extends Fragment implements SelectListener {

    RecyclerView explorerv;
    List<PostData> list = new ArrayList<PostData>();
    FirebaseDBManager firebaseDBManager;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ExploreScreen() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ExploreScreen.
     */
    // TODO: Rename and change types and number of parameters
    public static ExploreScreen newInstance(String param1, String param2) {
        ExploreScreen fragment = new ExploreScreen();
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
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_explore_screen, container, false);

        explorerv = v.findViewById(R.id.explorerv);

        firebaseDBManager = new FirebaseDBManager(getContext(), "posts", this);
        firebaseDBManager.viewpostexp(explorerv);

        return v;
    }

    @Override
    public void onPFPClicked(PostData postData) {

    }

    @Override
    public void onStoryClicked(PostData postData) {

    }

    @Override
    public void onPostClicked(PostData postData) {
        startActivity(new Intent(getContext(), PostClickedPage.class).putExtra("data", postData));
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