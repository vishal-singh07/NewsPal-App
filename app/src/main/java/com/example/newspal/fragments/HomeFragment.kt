package com.example.newspal.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.newspal.NewsApplication
import com.example.newspal.R
import com.example.newspal.adapters.NewsAdapter
import com.example.newspal.databinding.FragmentHomeBinding
import com.example.newspal.repository.NewsApiStatus
import com.example.newspal.viewmodels.MainViewModel
import com.example.newspal.viewmodels.MainViewModelFactory

class HomeFragment : Fragment() {

    val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(
            (activity?.application as NewsApplication).database,
        )
    }

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = NewsAdapter {
            this.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToFullNewsFragment(it))
        }
        binding.newsRV.adapter = adapter
        getTopHeadlines()
        binding.newsRV.layoutManager = LinearLayoutManager(this.context)
        PagerSnapHelper().attachToRecyclerView(binding.newsRV)
        binding.refreshlayout.setOnRefreshListener {
            getTopHeadlines()
            binding.refreshlayout.isRefreshing = false
            Toast.makeText(activity,"Updated!", Toast.LENGTH_SHORT)
        }
    }

    private fun getTopHeadlines() {
        getStatus()
        val preferences: SharedPreferences = this.requireContext().getSharedPreferences("My_Shared_Pref", Context.MODE_PRIVATE)
        val country = preferences.getString("country", "in")
        viewModel.getAllArticles(category = "general",country!!, this.requireContext().getString(R.string.api_key)).observe(this.viewLifecycleOwner) { articles ->
            articles.let {
                adapter.setNewsList(articles)
            }
        }
    }

    private fun getStatus() {
        viewModel.status.observe(this.viewLifecycleOwner) { status ->
            status.let {
                if(status==NewsApiStatus.ERROR)
                {
                    binding.newsRV.visibility = View.GONE
                    binding.statusImage.visibility = View.VISIBLE
                    binding.statusImage.setImageResource(R.drawable.ic_connection_error)
                }
                else {
                    binding.statusImage.visibility = View.GONE
                    binding.newsRV.visibility = View.VISIBLE
                }
            }

        }
    }




}