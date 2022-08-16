package com.meow.thehood.AdaptersAndViewHolders;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
import com.meow.thehood.R;
import com.meow.thehood.SelectListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class PostAdapter extends RecyclerView.Adapter<PostViewHolder> {

    Context context;
    List<PostData> data;
    SelectListener listener;

    public PostAdapter(Context context, List<PostData> data, SelectListener listener) {
        this.context = context;
        this.data = data;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PostViewHolder(LayoutInflater.from(context).inflate(R.layout.postdesign, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        if (Uri.parse(data.get(position).getPostpic()) != null) Picasso.get().load(Uri.parse(data.get(position).getPostpic())).into(holder.postpic);
        if (Uri.parse(data.get(position).getPfp()) != null) Picasso.get().load(Uri.parse(data.get(position).getPfp())).into(holder.pfp);
        holder.username.setText(data.get(position).getUsername());
        holder.posttime.setText(data.get(position).getTime());
        holder.caption.setText(data.get(position).getCaption());
        holder.nooflikes.setText(data.get(position).getLikes() + "");
        holder.noofcomments.setText(data.get(position).getComments() + "");
        holder.pfpname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onPFPClicked(data.get(position));
            }
        });
        holder.viewwholiked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.viewWhoLiked(data.get(position));
            }
        });
        holder.like.setImageResource(R.drawable.ic_baseline_star_border_24);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("profiles");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    ProfileData profileData = dataSnapshot.getValue(ProfileData.class);
                    if (profileData.getEmail().equals(user.getEmail())) {
                        holder.commentbtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("posts").child(data.get(position).getPostid()).child("commenttext");
                                databaseReference.child(profileData.getUsername()).setValue(holder.postcomment.getText().toString());
                                data.get(position).setComments(data.get(position).getComments() + 1);
                                HashMap<String, Object> hm = new HashMap<String, Object>();
                                hm.put("comments", data.get(position).getComments());
                                databaseReference = FirebaseDatabase.getInstance().getReference().child("posts");
                                databaseReference.child(data.get(position).getPostid()).updateChildren(hm).addOnCompleteListener(new OnCompleteListener() {
                                    @Override
                                    public void onComplete(@NonNull Task task) {
                                        if (!task.isSuccessful()) Toast.makeText(context, "Comment Failed!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });
                    }
                    if (data.get(position).getUsername().equals(profileData.getUsername())) if (profileData.isVerified()) holder.star.setImageResource(R.drawable.verified);
                    if (profileData.getEmail().equals(user.getEmail())) {
                        DatabaseReference dr = FirebaseDatabase.getInstance().getReference().child("posts").child(data.get(position).getPostid()).child("postLikedBy");
                        dr.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                                    if (Objects.equals(dataSnapshot.getKey(), profileData.getUsername())) {
                                        holder.like.setImageResource(R.drawable.ic_baseline_star_24);
                                        holder.like.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                listener.onUnLiked(data.get(position));
                                                dr.child(profileData.getUsername()).removeValue();
                                            }
                                        });
                                    } else {
                                        holder.like.setImageResource(R.drawable.ic_baseline_star_border_24);
                                        holder.like.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                listener.onLiked(data.get(position));
                                                dr.child(profileData.getUsername()).setValue(profileData.getUsername());
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
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}

class PostViewHolder extends RecyclerView.ViewHolder {

    CardView postcard, viewwholiked, viewcomments, commentbtn;
    ShapeableImageView pfp;
    ImageView postpic, like, send, comment, star;
    TextView username, posttime, caption, nooflikes, noofcomments;
    LinearLayout pfpname;
    EditText postcomment;

    public PostViewHolder(@NonNull View itemView) {
        super(itemView);
        postcard = itemView.findViewById(R.id.postcard);
        pfp = itemView.findViewById(R.id.pfp);
        postpic = itemView.findViewById(R.id.postpic);
        like = itemView.findViewById(R.id.like);
        send = itemView.findViewById(R.id.send);
        comment = itemView.findViewById(R.id.comment);
        username = itemView.findViewById(R.id.username);
        pfpname = itemView.findViewById(R.id.pfpname);
        posttime = itemView.findViewById(R.id.posttime);
        caption = itemView.findViewById(R.id.caption);
        postcomment = itemView.findViewById(R.id.postcomment);
        star = itemView.findViewById(R.id.star);
        nooflikes = itemView.findViewById(R.id.nooflikes);
        noofcomments = itemView.findViewById(R.id.noofcomments);
        viewwholiked = itemView.findViewById(R.id.viewwholiked);
        viewcomments = itemView.findViewById(R.id.viewcomments);
        commentbtn = itemView.findViewById(R.id.commentbtn);
    }
}