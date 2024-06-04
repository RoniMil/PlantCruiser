package com.example.plantcruiser

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.plantcruiser.databinding.DbMenuFragmentBinding


class DBFragment : Fragment() {
    private var _binding : DbMenuFragmentBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DbMenuFragmentBinding.inflate(inflater,container,false)


        return binding.root

    }
}