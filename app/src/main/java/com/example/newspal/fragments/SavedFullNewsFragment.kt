package com.example.newspal.fragments

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.newspal.NewsApplication
import com.example.newspal.R
import com.example.newspal.databinding.FragmentSavedFullNewsBinding
import com.example.newspal.viewmodels.MainViewModel
import com.example.newspal.viewmodels.MainViewModelFactory

class SavedFullNewsFragment : Fragment() {

    val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(
            (activity?.application as NewsApplication).database,
        )
    }

    private var _binding: FragmentSavedFullNewsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSavedFullNewsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val nightModeFlags = requireContext().resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        if (nightModeFlags == Configuration.UI_MODE_NIGHT_YES) {
            binding.delBtn.setImageResource(R.drawable.ic_baseline_delete_forever_light_24)
            binding.shareBtn.setImageResource(R.drawable.ic_baseline_share_light_24)
        }
        val article = SavedFullNewsFragmentArgs.fromBundle(requireArguments()).selectedNews
        binding.delBtn.setOnClickListener {
            Toast.makeText(activity, "Deleted!!", Toast.LENGTH_SHORT).show()
           viewModel.deleteArticle(article)
            findNavController().navigateUp()
        }
        binding.fullNewsTitle.text = article.title.toString()
        binding.fullNewsDescription.text = article.description.toString()
        val imgUrl = article.urlToImage
        if(imgUrl==null) {
            binding.fullNewsImage.load(R.drawable.ic_broken_image)
        } else {
            val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
            binding.fullNewsImage.load(imgUri) {
                placeholder(R.drawable.loading_animation)
            }
        }
        binding.fullNewsBtn.setOnClickListener {
            this.findNavController().navigate(SavedFullNewsFragmentDirections.actionSavedFullNewsFragmentToWebViewFragment(article.url!!))
        }
        binding.shareBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_SUBJECT, "Sharing URL")
            intent.putExtra(Intent.EXTRA_TEXT, article.url)
            startActivity(Intent.createChooser(intent, "Share URL"))
        }
    }
}