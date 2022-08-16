package com.meow.thehood.AdaptersAndViewHolders;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.meow.thehood.Models.PostData;
import com.meow.thehood.R;
import com.meow.thehood.SelectListener;

import java.util.List;

public class StoryAdapter extends RecyclerView.Adapter<StoryViewHolder> {

    Context context;
    List<PostData> data;
    SelectListener listener;

    public StoryAdapter(Context context, List<PostData> data, SelectListener listener) {
        this.context = context;
        this.data = data;
        this.listener = listener;
    }

    @NonNull
    @Override
    public StoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StoryViewHolder(LayoutInflater.from(context).inflate(R.layout.storydesign, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull StoryViewHolder holder, int position) {
//        holder.pfpstory.setImageResource(data.get(position).getPfp());
        holder.usernamestory.setText(data.get(position).getUsername());
        holder.storycard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onStoryClicked(data.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}

class StoryViewHolder extends RecyclerView.ViewHolder {

    CardView storycard;
    ImageView pfpstory;
    TextView usernamestory;

    public StoryViewHolder(@NonNull View itemView) {
        super(itemView);
        storycard = itemView.findViewById(R.id.storycard);
        pfpstory = itemView.findViewById(R.id.pfpstory);
        usernamestory = itemView.findViewById(R.id.usernamestory);
    }
}