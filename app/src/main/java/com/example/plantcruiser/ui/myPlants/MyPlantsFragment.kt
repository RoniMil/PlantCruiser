package com.example.plantcruiser.ui.myPlants

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.plantcruiser.R
import com.example.plantcruiser.databinding.DbPlantListFragmentBinding
import com.example.plantcruiser.databinding.MyPlantsFragmentBinding
import com.example.plantcruiser.ui.dbPlantList.DBPlantListViewModel
import com.example.plantcruiser.ui.dbPlantList.PlantsAdapter
import com.example.plantcruiser.utils.Constants
import com.example.plantcruiser.utils.Error
import com.example.plantcruiser.utils.Loading
import com.example.plantcruiser.utils.Success
import com.example.plantcruiser.utils.autoCleared

class MyPlantsFragment : Fragment() , MyPlantsAdapter.PlantItemListener {
    private val viewModel: MyPlantsViewModel by viewModels()

    private var binding: MyPlantsFragmentBinding by autoCleared()

    private lateinit var adapter: MyPlantsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MyPlantsFragmentBinding.inflate(inflater, container, false)

        binding.addButton.setOnClickListener {
            findNavController().navigate(R.id.action_myPlantsFragment_to_addMyPlantItemFragment)
        }

        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = MyPlantsAdapter(this)
        binding.recyclerViewPlants.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.recyclerViewPlants.adapter = adapter

    }


    override fun onPlantClick(plantId: Int) {
        findNavController().navigate(
            R.id.action_myPlantsFragment_to_myPlantItemFragment,
            bundleOf("id" to plantId)
        )
    }

}