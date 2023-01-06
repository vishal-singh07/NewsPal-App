package com.example.newspal.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newspal.NewsApplication
import com.example.newspal.adapters.SavedNewsAdapter
import com.example.newspal.databinding.FragmentHomeBinding
import com.example.newspal.viewmodels.MainViewModel
import com.example.newspal.viewmodels.MainViewModelFactory

class SavedFragment : Fragment() {
    val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(
            (activity?.application as NewsApplication).database,
        )
    }

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: SavedNewsAdapter

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
        adapter = SavedNewsAdapter {
            this.findNavController().navigate(SavedFragmentDirections.actionSavedFragmentToSavedFullNewsFragment(it))
        }
        binding.newsRV.adapter = adapter
        getSavedArticles()
        binding.newsRV.layoutManager = LinearLayoutManager(this.context)
        binding.refreshlayout.setOnRefreshListener {
            getSavedArticles()
            binding.refreshlayout.isRefreshing = false
            Toast.makeText(activity,"Updated!", Toast.LENGTH_SHORT)
        }
    }

    private fun getSavedArticles() {
        viewModel.getSavedArticles().observe(this.viewLifecycleOwner) { articles ->
            articles.let {
                adapter.setNewsList(articles)
            }
        }
    }
}