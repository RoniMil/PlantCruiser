package com.example.plantcruiser.ui.myPlants

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.plantcruiser.data.models.MyPlant
import com.example.plantcruiser.databinding.MyPlantItemFragmentBinding
import com.example.plantcruiser.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPlantItemFragment : Fragment() {
    private val viewModel: MyPlantItemViewModel by viewModels()

    private var binding: MyPlantItemFragmentBinding by autoCleared()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MyPlantItemFragmentBinding.inflate(inflater, container, false)

        binding.editButton

        binding.removeButton

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.plant.observe(viewLifecycleOwner) {
            updatePlant(it)
        }
        arguments?.getInt("id")?.let {
            viewModel.setId(it)
            val fragment = EditMyPlantItemFragment()
            val bundle = Bundle()
            bundle.putInt("id", it)
            fragment.arguments = bundle
        }


    }


    private fun updatePlant(myPlant: MyPlant) {
        binding.plantName.text = myPlant.name
        binding.plantingDateText.text = myPlant.plantingDate
        binding.plantSunlightText.text = myPlant.sunlight
        binding.plantWateringText.text = myPlant.watering
        binding.plantFertilizingFrequencyText.text = myPlant.fertilizingFreq
        binding.plantDiseaseText.text = myPlant.disease
        Glide.with(requireContext()).load(myPlant.image).circleCrop().into(binding.plantImage)
    }
}