package com.example.plantcruiser.ui.suggestAPlant

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
import com.example.plantcruiser.databinding.SuggestAPlantListFragmentBinding
import com.example.plantcruiser.utils.Error
import com.example.plantcruiser.utils.Loading
import com.example.plantcruiser.utils.Success
import com.example.plantcruiser.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint

// fragment for presenting the suggestions' items
@AndroidEntryPoint
class SuggestAPlantListFragment : Fragment(), SuggestedPlantsAdapter.PlantItemListener {

    private val viewModel: SuggestAPlantListViewModel by viewModels()

    private var binding: SuggestAPlantListFragmentBinding by autoCleared()

    private lateinit var adapter: SuggestedPlantsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SuggestAPlantListFragmentBinding.inflate(inflater, container, false)

        viewModel.deleteSuggestions()

        return binding.root


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = SuggestedPlantsAdapter(this)
        // set adapter to present the items in a grid with 3 columns
        binding.recyclerViewPlants.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.recyclerViewPlants.adapter = adapter

        // observes the plant list LiveData and updates UI based on its status
        viewModel.plants.observe(viewLifecycleOwner) { it ->
            when (it.status) {
                is Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is Success -> {
                    binding.progressBar.visibility = View.GONE
                    // if null just present an empty list (no plants)
                    adapter.setPlants(it.status.data ?: emptyList())
                }

                is Error -> {
                    binding.progressBar.visibility = View.GONE
                }

            }

        }
        // sets the options variable in viewModel with the user's selections
        arguments?.let {
            viewModel.setOptions(
                it.getString("indoor"),
                it.getString("sunlight"),
                it.getString("hardiness")
            )
        }
    }


    // listener for clicking a plant item card, moves to plant detail fragment and sends that plant's id
    override fun onPlantClick(plantId: Int) {
        findNavController().navigate(
            R.id.action_suggestAPlantListFragment_to_suggestAPlantDetailFragment,
            bundleOf("id" to plantId)
        )
    }
}