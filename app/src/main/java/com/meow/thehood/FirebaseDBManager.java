package com.meow.thehood;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.meow.thehood.AdaptersAndViewHolders.ChatListAdapter;
import com.meow.thehood.AdaptersAndViewHolders.ExploreAdapter;
import com.meow.thehood.AdaptersAndViewHolders.LikedAdapter;
import com.meow.thehood.AdaptersAndViewHolders.PostAdapter;
import com.meow.thehood.Models.ChatList;
import com.meow.thehood.Models.PostData;
import com.meow.thehood.Models.PostLikedBy;
import com.meow.thehood.Models.ProfileData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class FirebaseDBManager {

    Context context;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    String folder;
    SelectListener listener;
    List<PostData> list = new ArrayList<PostData>();
    List<String> listliked = new ArrayList<String>();
    List<ChatList> listchat = new ArrayList<ChatList>();
    PostAdapter postAdapter = new PostAdapter(context, list, listener);
    ExploreAdapter exploreAdapter = new ExploreAdapter(context, list, listener);
    boolean bool;

    public FirebaseDBManager(Context context, String folder) {
        this.context = context;
        this.folder = folder;
    }
    public FirebaseDBManager(Context context, String folder, SelectListener listener) {
        this.context = context;
        this.folder = folder;
        this.listener = listener;
    }

    public void viewpost(RecyclerView rv) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Getting Posts...");
        progressDialog.show();
        databaseReference = FirebaseDatabase.getInstance().getReference(folder);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    PostData postData = dataSnapshot.getValue(PostData.class);
                    list.add(postData);
                }
                Collections.reverse(list);
                postAdapter = new PostAdapter(context, list, listener);
                rv.setHasFixedSize(true);
                rv.setLayoutManager(new GridLayoutManager(context, 1));
                rv.setAdapter(postAdapter);
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void viewpostexp(RecyclerView rv) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Getting Posts...");
        progressDialog.show();
        databaseReference = FirebaseDatabase.getInstance().getReference(folder);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    PostData postData = dataSnapshot.getValue(PostData.class);
                    list.add(postData);
                }
                Collections.reverse(list);
                exploreAdapter = new ExploreAdapter(context, list, listener);
                rv.setHasFixedSize(true);
                rv.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                rv.setAdapter(exploreAdapter);
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void uploadpost(Context context, Uri data, String caption, String time, String username, Uri pfp, int likes, int comments) {
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Uploading...");
        progressDialog.show();
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference(folder);
        StorageReference reference = storageReference.child(folder + "/" + caption + " " + time + ".png");
        reference.putFile(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uri = taskSnapshot.getStorage().getDownloadUrl();
                        while (!uri.isComplete());
                        Uri url = uri.getResult();
                        String pid = databaseReference.push().getKey();
                        PostData postData = new PostData(url.toString(), caption, time, username, pfp.toString(), likes, comments, pid);
                        databaseReference.child(pid).setValue(postData);
                        Toast.makeText(context, "Uploaded!", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        double progress = (100.0 * snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                        progressDialog.setMessage("Uploaded: " + (int) progress + "%");
                    }
                });
    }

    public void createAccount(Context context, String name, String username, String email, String bio, Uri data) {
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference(folder);
        StorageReference reference = storageReference.child(folder + "/" + username + ".png");
        reference.putFile(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uri = taskSnapshot.getStorage().getDownloadUrl();
                        while (!uri.isComplete());
                        Uri url = uri.getResult();
                        ProfileData profileData = new ProfileData(url.toString(), name, username, bio, email, false, 0, 0, 0);
                        databaseReference.child(username).setValue(profileData);
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

                    }
                });
    }

    public void viewWhoLikedDB(RecyclerView rv, PostData postData) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Getting Who all Liked this Post...");
        progressDialog.show();
        databaseReference = FirebaseDatabase.getInstance().getReference(folder + "/" + postData.getPostid() + "/postLikedBy");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    listliked.add(dataSnapshot.getValue().toString());
                }
                Collections.reverse(list);
                LikedAdapter likedAdapter = new LikedAdapter(context, listliked, listener);
                rv.setHasFixedSize(true);
                rv.setLayoutManager(new GridLayoutManager(context, 1));
                rv.setAdapter(likedAdapter);
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void followSomeone(String username) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        DatabaseReference dr = FirebaseDatabase.getInstance().getReference("profiles");
        dr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    ProfileData profileData = dataSnapshot.getValue(ProfileData.class);
                    if (user.getEmail().equals(profileData.getEmail())) {
                        profileData.setFollowing(profileData.getFollowing() + 1);
                        HashMap<String, Object> hm = new HashMap<String, Object>();
                        hm.put("following", profileData.getFollowing());
                        dr.child(profileData.getUsername()).updateChildren(hm).addOnCompleteListener(new OnCompleteListener() {
                            @Override
                            public void onComplete(@NonNull Task task) {
                                if (!task.isSuccessful()) Toast.makeText(context, "Follow Failed!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else if (profileData.getUsername().equals(username)) {
                        profileData.setFollowing(profileData.getFollowers() + 1);
                        HashMap<String, Object> hm = new HashMap<String, Object>();
                        hm.put("followers", profileData.getFollowers());
                        dr.child(profileData.getUsername()).updateChildren(hm).addOnCompleteListener(new OnCompleteListener() {
                            @Override
                            public void onComplete(@NonNull Task task) {
                                if (!task.isSuccessful()) Toast.makeText(context, "Follow Failed!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void chats(RecyclerView rv) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Getting Chats...");
        progressDialog.show();
        databaseReference = FirebaseDatabase.getInstance().getReference(folder);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    ProfileData profileData = dataSnapshot.getValue(ProfileData.class);
                    if (!profileData.getEmail().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())){
                        ChatList chatList = new ChatList(profileData.getUsername(), profileData.getPfp());
                        listchat.add(chatList);
                    }
                }
                ChatListAdapter chatListAdapter = new ChatListAdapter(context, listchat, listener);
                rv.setHasFixedSize(true);
                rv.setLayoutManager(new GridLayoutManager(context, 1));
                rv.setAdapter(chatListAdapter);
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
