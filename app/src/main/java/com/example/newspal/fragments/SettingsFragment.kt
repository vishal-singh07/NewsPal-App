package com.example.newspal.fragments

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.example.newspal.data.SettingsDataStore
import com.example.newspal.data.dataStore
import com.example.newspal.databinding.FragmentSettingsBinding


class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private lateinit var settingsDataStore: SettingsDataStore
    private var country = "in"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSettingsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        settingsDataStore = SettingsDataStore(this.requireContext().dataStore)
        val nightModeFlags = requireContext().resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        if (nightModeFlags == Configuration.UI_MODE_NIGHT_YES) {
            binding.themeSwitch.isChecked = true
        }
        binding.countryCodePicker.setOnCountryChangeListener {
            //TODO set country using preference datastore
//            val context = this.requireContext()
//            country = binding.countryCodePicker.selectedCountryNameCode.toLowerCase()
//            lifecycleScope.launch {
//                settingsDataStore.saveCountryToPreferencesStore(country, context)
//            }
//            findNavController().navigateUp()
            val prefs = this.requireContext().getSharedPreferences("My_Shared_Pref", Context.MODE_PRIVATE)
            val editor = prefs.edit()
            editor.putString("country", binding.countryCodePicker.selectedCountryNameCode.lowercase())
            editor.apply()
        }

        binding.themeSwitch.setOnCheckedChangeListener { compoundButton, isChecked ->
            if(isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

    }
}