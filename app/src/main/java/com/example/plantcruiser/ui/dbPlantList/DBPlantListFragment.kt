package com.example.plantcruiser.ui.dbPlantList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.plantcruiser.databinding.DbPlantListFragmentBinding
import com.example.plantcruiser.utils.Loading
import com.example.plantcruiser.utils.Success
import com.example.plantcruiser.utils.Error
import dagger.hilt.android.AndroidEntryPoint
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.plantcruiser.R
import com.example.plantcruiser.utils.Constants
import com.example.plantcruiser.utils.autoCleared

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

        binding.recyclerViewPlants.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    viewModel.currentPlantDBPage.value?.let { currentPage ->
                        if (currentPage < Constants.MAX_PLANT_PAGE) {
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
        binding.recyclerViewPlants.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.recyclerViewPlants.adapter = adapter

        viewModel.plants.observe(viewLifecycleOwner) { resource ->
            resource?.let {
                when (it.status) {
                    is Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }

                    is Success -> {
                        binding.progressBar.visibility = View.GONE
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
    }


    override fun onPlantClick(plantId: Int) {
        findNavController().navigate(
            R.id.action_DBPlantListFragment_to_DBPlantDetailFragment,
            bundleOf("id" to plantId)
        )
    }

}









