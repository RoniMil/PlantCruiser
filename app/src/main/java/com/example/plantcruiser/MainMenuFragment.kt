package com.example.plantcruiser

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.plantcruiser.databinding.MainMenuFragmentBinding


class MainMenuFragment : Fragment() {
    private var _binding : MainMenuFragmentBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MainMenuFragmentBinding.inflate(inflater,container,false)

        binding.databaseButton.setOnClickListener{
            findNavController().navigate(R.id.action_mainMenuFragment_to_DBFragment)
        }
        return binding.root
    }
}