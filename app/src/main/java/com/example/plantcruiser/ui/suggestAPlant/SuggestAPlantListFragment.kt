package com.example.plantcruiser.ui.suggestAPlant

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
import com.example.plantcruiser.R
import com.example.plantcruiser.databinding.SuggestAPlantListFragmentBinding
import com.example.plantcruiser.utils.Error
import com.example.plantcruiser.utils.Loading
import com.example.plantcruiser.utils.Success
import com.example.plantcruiser.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint

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
        binding.recyclerViewPlants.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.recyclerViewPlants.adapter = adapter

        viewModel.plants.observe(viewLifecycleOwner) { it ->
            when (it.status) {
                is Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is Success -> {
                    binding.progressBar.visibility = View.GONE
                    it.status.data?.let {
                        adapter.setPlants(it)
                    } ?:run {
                        adapter.setPlants(emptyList())
                        Toast.makeText(requireContext(), getString(R.string.no_suggested_plants_msg), Toast.LENGTH_LONG)
                            .show()
                        
                    }
                }

                is Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), getString(R.string.no_suggested_plants_msg), Toast.LENGTH_LONG)
                        .show()
                }

            }

        }
        arguments?.let {
            viewModel.setOptions(
                it.getString("indoor"),
                it.getString("sunlight"),
                it.getString("hardiness")
            )
        }
    }


    override fun onPlantClick(plantId: Int) {
        findNavController().navigate(
            R.id.action_suggestAPlantListFragment_to_suggestAPlantDetailFragment,
            bundleOf("id" to plantId)
        )
    }
}