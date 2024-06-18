package com.example.plantcruiser.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.plantcruiser.R
import com.example.plantcruiser.databinding.MainMenuFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

// fragment for the main menu
@AndroidEntryPoint
class MainMenuFragment : Fragment() {
    private var _binding : MainMenuFragmentBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MainMenuFragmentBinding.inflate(inflater,container,false)

        // set listeners for all menu options and navigate accordingly
        binding.languageButton.setOnClickListener {
            findNavController().navigate(R.id.action_mainMenuFragment_to_languageDialogFragment)
        }

        binding.databaseButton.setOnClickListener{
            findNavController().navigate(R.id.action_mainMenuFragment_to_DBPlantListFragment)
        }

        binding.myPlantsButton.setOnClickListener {
            findNavController().navigate(R.id.action_mainMenuFragment_to_myPlantsFragment)
        }

        binding.suggestAPlantButton.setOnClickListener {
            findNavController().navigate(R.id.action_mainMenuFragment_to_suggestAPlantParamsSelectionFragment)
        }


        return binding.root
    }
}