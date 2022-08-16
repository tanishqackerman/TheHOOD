package com.meow.thehood.AdaptersAndViewHolders;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.meow.thehood.Models.ChatList;
import com.meow.thehood.Models.ProfileData;
import com.meow.thehood.R;
import com.meow.thehood.SelectListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ChatListAdapter extends RecyclerView.Adapter<LikedViewHolder> {

    Context context;
    List<ChatList> data;
    SelectListener listener;

    public ChatListAdapter(Context context, List<ChatList> data, SelectListener listener) {
        this.context = context;
        this.data = data;
        this.listener = listener;
    }

    @NonNull
    @Override
    public LikedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LikedViewHolder(LayoutInflater.from(context).inflate(R.layout.likedbydesign, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LikedViewHolder holder, int position) {
        holder.username.setText(data.get(position).getUsername());
        if (Uri.parse(data.get(position).getPfpchat()) != null) Picasso.get().load(Uri.parse(data.get(position).getPfpchat())).into(holder.pfpliked);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        holder.likedcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("profiles");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                            ProfileData profileData = dataSnapshot.getValue(ProfileData.class);
                            if (profileData.getEmail().equals(user.getEmail())) {
                                listener.onChatClicked(data.get(position), profileData);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}

//class LikedViewHolder extends RecyclerView.ViewHolder {
//
//    ShapeableImageView pfpliked;
//    TextView username;
//    CardView likedcard;
//
//    public LikedViewHolder(@NonNull View itemView) {
//        super(itemView);
//
//        pfpliked = itemView.findViewById(R.id.pfpliked);
//        username = itemView.findViewById(R.id.usernameliked);
//        likedcard = itemView.findViewById(R.id.likedcard);
//    }
//}
