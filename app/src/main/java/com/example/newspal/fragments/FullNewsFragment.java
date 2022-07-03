package com.example.newspal.fragments;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.newspal.R;
import com.example.newspal.model.Article;
import com.example.newspal.viewmodel.MainActivityViewModel;

public class FullNewsFragment extends Fragment {
    private Article selectedNews;
    private ImageView fullNewsImage;
    private TextView fullNewsTitle,fullNewsDescription;
    private MainActivityViewModel mainActivityViewModel;
    private Button fullNewsBtn;
    private ImageButton saveBtn,shareBtn;
    public FullNewsFragment() {
        // Required empty public constructor

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainActivityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_full_news, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Article selectedNews = FullNewsFragmentArgs.fromBundle(getArguments()).getSelectedNews();
        saveBtn = view.findViewById(R.id.saveBtn);
        shareBtn = view.findViewById(R.id.shareBtn);
        int nightModeFlags = getContext().getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        if(nightModeFlags== Configuration.UI_MODE_NIGHT_YES)
        {
            saveBtn.setImageResource(R.drawable.ic_baseline_bookmark_border_light_24);
            shareBtn.setImageResource(R.drawable.ic_baseline_share_light_24);
        }
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Saved!!", Toast.LENGTH_SHORT).show();
                mainActivityViewModel.saveArticle(selectedNews);
            }
        });
        fullNewsImage = view.findViewById(R.id.fullNewsImage);
        fullNewsDescription = view.findViewById(R.id.fullNewsDescription);
        fullNewsTitle = view.findViewById(R.id.fullNewsTitle);
        fullNewsBtn = view.findViewById(R.id.fullNewsBtn);
        if(selectedNews.getUrlToImage()==null)
            Glide.with(view)
                    .load(R.drawable.image)
                    .into(fullNewsImage);
        else
            Glide.with(view)
                    .load(selectedNews.getUrlToImage())
                    .into(fullNewsImage);
        fullNewsTitle.setText(selectedNews.getTitle());
        fullNewsDescription.setText(selectedNews.getDescription());
        fullNewsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = NavHostFragment.findNavController(getParentFragment());
                navController.navigate(FullNewsFragmentDirections.actionFullNewsFragmentToWebViewFragment(selectedNews.getUrl()));
            }
        });
        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_SUBJECT, "Sharing URL");
                i.putExtra(Intent.EXTRA_TEXT, selectedNews.getUrl());
                startActivity(Intent.createChooser(i, "Share URL"));
            }
        });
    }
}