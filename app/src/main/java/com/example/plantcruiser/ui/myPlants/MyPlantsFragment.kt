package com.example.plantcruiser.ui.myPlants

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.plantcruiser.R
import com.example.plantcruiser.databinding.MyPlantsFragmentBinding
import com.example.plantcruiser.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint

// fragment for presenting the MyPlants' items
@AndroidEntryPoint
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

        // add plant button navigates to add plant fragment
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

        // set plants in adapter as current observed plants list
        viewModel.plants.observe(viewLifecycleOwner) {
            adapter.setPlants(it)



        }

    }

    // listener for clicking a plant item card, moves to plant detail fragment and sends that plant's id
    override fun onPlantClick(plantId: Int) {
        findNavController().navigate(
            R.id.action_myPlantsFragment_to_myPlantItemFragment,
            bundleOf("id" to plantId)
        )
    }

}