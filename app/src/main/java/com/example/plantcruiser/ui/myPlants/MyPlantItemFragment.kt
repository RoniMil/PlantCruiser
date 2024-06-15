package com.example.plantcruiser.ui.myPlants

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.plantcruiser.R
import com.example.plantcruiser.data.models.MyPlant
import com.example.plantcruiser.databinding.MyPlantItemFragmentBinding
import com.example.plantcruiser.utils.HelperFunctions
import com.example.plantcruiser.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPlantItemFragment : Fragment() {
    private val viewModel: MyPlantItemViewModel by viewModels()

    private var binding: MyPlantItemFragmentBinding by autoCleared()

    private var plantObserver: Observer<MyPlant?>? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MyPlantItemFragmentBinding.inflate(inflater, container, false)

        binding.editButton.setOnClickListener {
            val plant = viewModel.plant.value!!
            arguments?.getInt("id")?.let {
                findNavController().navigate(
                    R.id.action_myPlantItemFragment_to_editMyPlantItemFragment,
                    bundleOf(
                        "id" to it,
                        "name" to plant.name,
                        "plantingDate" to plant.plantingDate,
                        "disease" to plant.disease,
                        "fertilizingFreq" to plant.fertilizingFreq,
                        "sunlight" to plant.sunlight,
                        "watering" to plant.watering,
                        "image" to HelperFunctions.fromBitmap(plant.image)
                    )
                )
            }

        }

        binding.removeButton.setOnClickListener {
            val plant = viewModel.plant.value!!

            val builder = AlertDialog.Builder(requireContext())

            // set the title of the dialog according to the current language
            builder.setTitle(getString(R.string.remove_plant_dialog))
            builder.setMessage(getString(R.string.remove_plant_dialog_text))

            // if accepted and language was changed, call on changeLocale method to change language
            builder.setPositiveButton(R.string.dialog_accept) { dialog, _ ->
                viewModel.deleteMyPlant(plant)
                dialog.dismiss()
                findNavController().navigate(R.id.action_myPlantItemFragment_to_myPlantsFragment)
            }


            // if cancelled, do nothing
            builder.setNegativeButton(R.string.dialog_cancel) { dialog, _ -> dialog.dismiss() }

            val dialog = builder.create()
            dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
            dialog.show()
        }


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        plantObserver = Observer { plant : MyPlant? ->
            if (plant != null) {
                updatePlant(plant)
            } else {
                // Remove the observer if the plant is null
                viewModel.plant.removeObserver(plantObserver!!)
            }
        }

        viewModel.plant.observe(viewLifecycleOwner, plantObserver!!)

        arguments?.getInt("id")?.let {
            viewModel.setId(it)
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