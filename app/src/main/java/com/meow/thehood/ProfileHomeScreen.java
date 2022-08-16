package com.meow.thehood;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.meow.thehood.Models.ProfileData;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileHomeScreen#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileHomeScreen extends Fragment {

    ShapeableImageView profilepfp;
    TextView fullname, username, bio, posts, following, followers;
    CardView signout, deleteaccount, deletepost;
    ImageView star;

    FirebaseDBManager firebaseDBManager = new FirebaseDBManager(getContext(), "profiles");

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileHomeScreen() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileScreem.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileHomeScreen newInstance(String param1, String param2) {
        ProfileHomeScreen fragment = new ProfileHomeScreen();
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
        View v = inflater.inflate(R.layout.fragment_profile_home_screen, container, false);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        profilepfp = v.findViewById(R.id.profilepfp);
        fullname = v.findViewById(R.id.fullname);
        username = v.findViewById(R.id.username);
        signout = v.findViewById(R.id.signout);
        bio = v.findViewById(R.id.bio);
        posts = v.findViewById(R.id.posts);
        following = v.findViewById(R.id.following);
        followers = v.findViewById(R.id.followers);
        star = v.findViewById(R.id.star);
        deleteaccount = v.findViewById(R.id.deleteaccount);
        deletepost = v.findViewById(R.id.deletepost);

        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Getting Profile...");
        progressDialog.show();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("profiles");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    ProfileData profileData = dataSnapshot.getValue(ProfileData.class);
                    if (profileData.getEmail().equals(user.getEmail())) {
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

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                startActivity(new Intent(getContext(), LoginPage.class));
            }
        });

        deleteaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), DeleteAccountPage.class));
            }
        });

        return v;
    }
}