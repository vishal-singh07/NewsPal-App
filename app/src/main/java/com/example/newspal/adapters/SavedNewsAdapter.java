package com.example.newspal.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.newspal.NewsClickListener;
import com.example.newspal.R;
import com.example.newspal.model.Article;

import java.util.List;

public class SavedNewsAdapter extends RecyclerView.Adapter<SavedNewsAdapter.NewsViewHolder> {
    Context context;
    List<Article> articles;
    NewsClickListener listener;

    public SavedNewsAdapter(Context context, List<Article> articles, NewsClickListener listener) {
        this.context = context;
        this.articles = articles;
        this.listener = listener;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NewsViewHolder(LayoutInflater.from(context).inflate(R.layout.saved_news_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        String imageUri = articles.get(position).getUrlToImage();
        if(imageUri==null)
            Glide.with(holder.itemView)
                    .load(R.drawable.image)
                    .into(holder.ivSaved);
        else
        Glide.with(holder.itemView)
                .load(imageUri)
                .into(holder.ivSaved);
        holder.tvSavedNews.setText(articles.get(position).getTitle());
        holder.newsContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(articles.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {
        CardView newsContainer;
        ImageView ivSaved;
        TextView tvSavedNews;
        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
           ivSaved = itemView.findViewById(R.id.ivSaved);
            tvSavedNews = itemView.findViewById(R.id.tvSavedTitle);
            newsContainer = itemView.findViewById(R.id.cardView);
        }
    }
}
