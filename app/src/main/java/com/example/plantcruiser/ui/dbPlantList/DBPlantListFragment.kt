package com.example.plantcruiser.ui.dbPlantList

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
import com.example.plantcruiser.utils.Constants
import com.example.plantcruiser.utils.Error
import com.example.plantcruiser.utils.Loading
import com.example.plantcruiser.utils.Success
import com.example.plantcruiser.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint

// fragment for presenting the Database's items
@AndroidEntryPoint
class DBPlantListFragment : Fragment(), PlantsAdapter.PlantItemListener {

    private val viewModel: DBPlantListViewModel by viewModels()

    private var binding: DbPlantListFragmentBinding by autoCleared()

    private lateinit var adapter: PlantsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DbPlantListFragmentBinding.inflate(inflater, container, false)

        // listener for fetching more plants if the user reaches the bottom of the screen
        binding.recyclerViewPlants.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    viewModel.currentPlantDBPage.value?.let { currentPage ->
                        // limits data fetching to a maximum page for preventing max api calls
                        if (currentPage < Constants.MAX_PLANT_PAGE) {
                            // increments currentPage global var to enable another API call with new page value
                            viewModel.updateGlobalVariable(currentPage + 1)
                        }

                    }
                }

            }

        })


        return binding.root


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = PlantsAdapter(this)
        // set adapter to present the items in a grid with 3 columns
        binding.recyclerViewPlants.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.recyclerViewPlants.adapter = adapter

        // observes the plant list LiveData and updates UI based on its status
        viewModel.plants.observe(viewLifecycleOwner) {
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
                        Toast.makeText(requireContext(), it.status.message, Toast.LENGTH_LONG)
                            .show()
                    }

                }
        }
    }

    // listener for clicking a plant item card, moves to plant detail fragment and sends that plant's id
    override fun onPlantClick(plantId: Int) {
        findNavController().navigate(
            R.id.action_DBPlantListFragment_to_DBPlantDetailFragment,
            bundleOf("id" to plantId)
        )
    }

}









