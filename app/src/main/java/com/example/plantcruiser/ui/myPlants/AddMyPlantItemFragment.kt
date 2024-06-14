package com.example.plantcruiser.ui.myPlants

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.plantcruiser.R
import com.example.plantcruiser.data.models.MyPlant
import com.example.plantcruiser.databinding.AddMyPlantItemFragmentBinding
import com.example.plantcruiser.utils.Constants
import com.example.plantcruiser.utils.HelperFunctions
import com.example.plantcruiser.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AddMyPlantItemFragment : Fragment() {
    private val viewModel: MyPlantItemViewModel by viewModels()

    private var binding: AddMyPlantItemFragmentBinding by autoCleared()

    private var selectedImageBitmap: Bitmap? = null

    private val cameraLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                selectedImageBitmap = result.data?.extras?.get("data") as Bitmap?
            }
        }

    private val galleryLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val selectedImageUri = result.data?.data
                //selectedImageBitmap = HelperFunctions.uriToBitmap(requireContext(), selectedImageUri)

            }
        }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AddMyPlantItemFragmentBinding.inflate(inflater, container, false)

        binding.plantNameText

        binding.plantingDateText

        binding.plantDiseaseText

        binding.plantSunlightText

        binding.plantWateringText

        binding.plantFertilizingFrequencyText

        binding.selectImageButton.setOnClickListener {
            HelperFunctions.openImagePicker(this)
        }

        binding.finishButton.setOnClickListener {
            val plantingDate = binding.plantingDateText.text.toString()
            if (binding.plantNameText.text.toString().isNotEmpty()) {
                val newPlant = MyPlant(
                    id = 0, // Assuming auto-generate, so setting to 0 or not at all if the ID is not required here.
                    name = binding.plantNameText.text.toString(),
                    image = selectedImageBitmap,
                    plantingDate = plantingDate,
                    sunlight = binding.plantSunlightText.text.toString(),
                    watering = binding.plantWateringText.text.toString(),
                    fertilizingFreq = binding.plantFertilizingFrequencyText.text.toString(),
                    disease = binding.plantDiseaseText.text.toString()
                )
                viewModel.insert(newPlant)
                findNavController().navigate(R.id.action_addMyPlantItemFragment_to_myPlantsFragment)
            } else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.empty_plant_msg),
                    Toast.LENGTH_SHORT
                ).show()
            }


        }
        return binding.root
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            Constants.REQUEST_IMAGE_CAPTURE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    HelperFunctions.openCamera(this)
                } else {
                    // Permission denied
                }
            }
            Constants.REQUEST_IMAGE_GALLERY -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    HelperFunctions.openGallery(this)
                } else {
                    // Permission denied
                }
            }
        }
    }


}


