package com.example.plantcruiser.ui.dbPlantList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.plantcruiser.databinding.DbPlantListFragmentBinding
import com.example.plantcruiser.utils.Loading
import com.example.plantcruiser.utils.Success
import dagger.hilt.android.AndroidEntryPoint
import androidx.navigation.fragment.findNavController
import com.example.plantcruiser.R

@AndroidEntryPoint
class DBPlantListFragment : Fragment(), PlantsAdapter.PlantItemListener {

    private val viewModel: DBPlantListViewModel by viewModels()

    private var _binding: DbPlantListFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: PlantsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DbPlantListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = PlantsAdapter(this)
        binding.recyclerViewPlants.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.recyclerViewPlants.adapter = adapter

        viewModel.plants.observe(viewLifecycleOwner) {
            when (it.status) {
                is Loading -> binding.progressBar.visibility = View.VISIBLE
                is Success -> {
                    binding.progressBar.visibility = View.GONE
                    adapter.setPlants(it.status.data!!)
                }

                is Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), it.status.message, Toast.LENGTH_LONG)
                        .show()
                }

            }


        }
    }
    override fun onPlantClick(plantId: Int) {
        findNavController().navigate(
            R.id.action_DBPlantListFragment_to_plantDetailFragment,
            bundleOf("id" to plantId))
    }
}





