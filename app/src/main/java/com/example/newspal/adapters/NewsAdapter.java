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

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {
    Context context;
    List<Article> articles;
    NewsClickListener listener;

    public NewsAdapter(Context context, List<Article> articles, NewsClickListener listener) {
        this.context = context;
        this.articles = articles;
        this.listener = listener;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NewsViewHolder(LayoutInflater.from(context).inflate(R.layout.news_item_rv,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        String imageUri = articles.get(position).getUrlToImage();
        if(imageUri==null)
            Glide.with(holder.itemView)
                    .load(R.drawable.image)
                    .into(holder.newsImage);


        else
        Glide.with(holder.itemView)
                .load(imageUri)
                .into(holder.newsImage);
        holder.newsHeadline.setText(articles.get(position).getTitle());
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
        ImageView newsImage;
        TextView newsHeadline;
        CardView newsContainer;
        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            newsImage = itemView.findViewById(R.id.newsImage);
            newsHeadline = itemView.findViewById(R.id.newsHeadLine);
            newsContainer = itemView.findViewById(R.id.newsContainer);
        }
    }
}
