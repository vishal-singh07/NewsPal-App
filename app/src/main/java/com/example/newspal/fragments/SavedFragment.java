package com.example.newspal.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.newspal.NewsClickListener;
import com.example.newspal.R;
import com.example.newspal.adapters.SavedNewsAdapter;
import com.example.newspal.model.Article;
import com.example.newspal.viewmodel.MainActivityViewModel;

import java.util.ArrayList;
import java.util.List;

public class SavedFragment extends Fragment {
    private static final String TAG = "Saved";
    private ArrayList<Article> articles;
    private SavedNewsAdapter newsAdapter;
    private RecyclerView recyclerView;
    private MainActivityViewModel mainActivityViewModel;
    private SwipeRefreshLayout refreshLayout;
    public SavedFragment() {
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
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        articles = new ArrayList<>();
        recyclerView = view.findViewById(R.id.newsRV);
        getSavedArticles();
        refreshLayout = view.findViewById(R.id.refreshlayout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getSavedArticles();
                refreshLayout.setRefreshing(false);
                Toast.makeText(getActivity(), "Updated!", Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void getSavedArticles()
    {
        mainActivityViewModel.getSavedArticles().observe(getViewLifecycleOwner(), new Observer<List<Article>>() {
            @Override
            public void onChanged(List<Article> savedArticles) {
                articles = (ArrayList<Article>) savedArticles;
                newsAdapter = new SavedNewsAdapter(getActivity(),articles,listener);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView.setAdapter(newsAdapter);
                newsAdapter.notifyDataSetChanged();
            }
        });
    }
    public final NewsClickListener listener = new NewsClickListener() {
        @Override
        public void onClick(Article news) {
            NavController navController = NavHostFragment.findNavController(getParentFragment());
            navController.navigate(SavedFragmentDirections.actionSavedFragmentToSavedFullNewsFragment(news));
        }
    };
}