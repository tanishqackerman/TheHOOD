package com.meow.thehood.AdaptersAndViewHolders;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.meow.thehood.Models.PostData;
import com.meow.thehood.R;
import com.meow.thehood.SelectListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ExploreAdapter extends RecyclerView.Adapter<ExploreViewHolder> {

    Context context;
    List<PostData> data;
    SelectListener listener;

    public ExploreAdapter(Context context, List<PostData> data, SelectListener listener) {
        this.context = context;
        this.data = data;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ExploreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ExploreViewHolder(LayoutInflater.from(context).inflate(R.layout.exploredesign, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ExploreViewHolder holder, int position) {
        if (Uri.parse(data.get(position).getPostpic()) != null) Picasso.get().load(Uri.parse(data.get(position).getPostpic())).into(holder.expic);
//        holder.postcard.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                listener.onPostClicked(data.get(position));
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}

class ExploreViewHolder extends RecyclerView.ViewHolder {
    CardView postcard;
    ImageView expic;

    public ExploreViewHolder(@NonNull View itemView) {
        super(itemView);
        expic = itemView.findViewById(R.id.expic);
        postcard = itemView.findViewById(R.id.postcard);
    }
}