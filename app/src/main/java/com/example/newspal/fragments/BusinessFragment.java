package com.example.newspal.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.newspal.NewsClickListener;
import com.example.newspal.R;
import com.example.newspal.adapters.NewsAdapter;
import com.example.newspal.model.Article;
import com.example.newspal.viewmodel.MainActivityViewModel;

import java.util.ArrayList;
import java.util.List;

public class BusinessFragment extends Fragment {
    private RecyclerView recyclerView;
    private NewsAdapter newsAdapter;
    private ArrayList<Article> articles;
    private ArrayList<Article> savedArticles = new ArrayList<>();
    private MainActivityViewModel mainActivityViewModel;
    public String category = "business";
    public BusinessFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.newsRV);
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
        getTopHeadlines(category);
        SwipeRefreshLayout refreshLayout = view.findViewById(R.id.refreshlayout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getTopHeadlines(category);
                refreshLayout.setRefreshing(false);
                Toast.makeText(getActivity(), "Updated!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getTopHeadlines(String category) {
        mainActivityViewModel.getAllArticles(category).observe(getViewLifecycleOwner(), new Observer<List<Article>>() {
            @Override
            public void onChanged(List<Article> moviesFromLiveData) {
                articles = (ArrayList<Article>) moviesFromLiveData;
                newsAdapter = new NewsAdapter(getActivity(),articles,listener);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView.setAdapter(newsAdapter);
                newsAdapter.notifyDataSetChanged();

            }
        });
    }
    public final NewsClickListener listener = new NewsClickListener() {
        @Override
        public void onClick(Article news) {
//            mainActivityViewModel.setSelectedNews(news);
            NavController navController = NavHostFragment.findNavController(getParentFragment());
            navController.navigate(BusinessFragmentDirections.actionBusinessFragmentToFullNewsFragment(news));
        }
    };
}