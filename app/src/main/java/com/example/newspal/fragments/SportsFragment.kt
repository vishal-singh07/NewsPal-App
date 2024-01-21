package com.example.newspal.fragments
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
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
import com.example.newspal.adapters.NewsAdapter
import com.example.newspal.databinding.FragmentHomeBinding
import com.example.newspal.viewmodels.MainViewModel
import com.example.newspal.viewmodels.MainViewModelFactory

class SportsFragment : Fragment() {


    val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(
            (activity?.application as NewsApplication).database,
        )
    }

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = NewsAdapter {
            this.findNavController().navigate(SportsFragmentDirections.actionSportsFragmentToFullNewsFragment(it))
        }
        binding.newsRV.adapter = adapter
        getTopHeadlines()
        binding.newsRV.layoutManager = LinearLayoutManager(this.context)
        PagerSnapHelper().attachToRecyclerView(binding.newsRV)
        binding.refreshlayout.setOnRefreshListener {
            getTopHeadlines()
            binding.refreshlayout.isRefreshing = false
            Toast.makeText(activity,"Updated!",Toast.LENGTH_SHORT).show()
        }
    }

    private fun getTopHeadlines() {
        val preferences: SharedPreferences = this.requireContext().getSharedPreferences("My_Shared_Pref", Context.MODE_PRIVATE)
        val country = preferences.getString("country", "in")
        viewModel.getAllArticles(category = "sports",country!!).observe(this.viewLifecycleOwner) { articles ->
            articles.let {
                adapter.setNewsList(articles)
            }
        }
    }



}