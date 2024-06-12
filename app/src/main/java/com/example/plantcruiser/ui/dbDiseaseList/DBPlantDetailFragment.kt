package com.example.plantcruiser.ui.dbDiseaseList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.plantcruiser.data.models.Plant
import com.example.plantcruiser.databinding.DbPlantDetailsFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import com.example.plantcruiser.utils.autoCleared
import com.example.plantcruiser.utils.Error
import com.example.plantcruiser.utils.Loading
import com.example.plantcruiser.utils.Success

@AndroidEntryPoint
class DBPlantDetailFragment : Fragment() {
    private val viewModel : DBPlantViewModel by viewModels()

    private var binding : DbPlantDetailsFragmentBinding by autoCleared()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DbPlantDetailsFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.plant.observe(viewLifecycleOwner) {
            when(it.status) {
                is Success -> {
                    binding.progressBar.visibility = View.GONE
                    updatePlant(it.status.data!!)
                    binding.plantCl.visibility = View.VISIBLE
                }
                is Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.plantCl.visibility = View.GONE
                }
                is Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(),it.status.message,Toast.LENGTH_LONG).show()
                }
            }
        }
        arguments?.getInt("id")?.let {
            viewModel.setId(it)
        }
    }
    private fun updatePlant(plant: Plant) {
        binding.plantName.text = plant.common_name
        binding.plantIdText.text = plant.id.toString()
        binding.plantCycleText.text = plant.cycle
        binding.plantSunlightText.text = plant.sunlight.toString()
        binding.plantWateringText.text = plant.watering
        Glide.with(requireContext()).load(plant.default_image?.regular_url).circleCrop().into(binding.plantImage)
    }

}