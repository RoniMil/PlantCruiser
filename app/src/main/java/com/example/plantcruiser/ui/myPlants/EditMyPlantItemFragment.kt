package com.example.plantcruiser.ui.myPlants

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.plantcruiser.R
import com.example.plantcruiser.data.models.MyPlant
import com.example.plantcruiser.databinding.AddEditMyPlantItemFragmentBinding
import com.example.plantcruiser.utils.HelperFunctions
import com.example.plantcruiser.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class EditMyPlantItemFragment : Fragment() {
    private val viewModel: MyPlantItemViewModel by viewModels()

    private var binding: AddEditMyPlantItemFragmentBinding by autoCleared()

    private var selectedImageBitmap: Bitmap? = null

    private var id : Int? = null


    private val cameraLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val imageBitmap = result.data?.extras?.get("data") as Bitmap
                selectedImageBitmap = imageBitmap
                binding.plantImage.setImageBitmap(imageBitmap)
            }
        }

    private val galleryLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val imageUri = result.data?.data
                imageUri?.let {
                    val imageBitmap =
                        MediaStore.Images.Media.getBitmap(
                            requireContext().contentResolver,
                            imageUri
                        )
                    selectedImageBitmap = imageBitmap
                    binding.plantImage.setImageBitmap(imageBitmap)
                }
            }


        }

    private val cameraPermissionsLauncher: ActivityResultLauncher<String> =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) {
            if (it)
                openCamera()
            else
                Toast.makeText(
                    requireContext(),
                    getString(R.string.no_camera_permissions_msg),
                    Toast.LENGTH_SHORT
                ).show()
        }

    private val galleryPermissionsLauncher: ActivityResultLauncher<String> =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) {
            if (it)
                openGallery()
            else
                Toast.makeText(
                    requireContext(),
                    getString(R.string.no_camera_permissions_msg),
                    Toast.LENGTH_SHORT
                ).show()
        }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AddEditMyPlantItemFragmentBinding.inflate(inflater, container, false)

        binding.selectImageButton.setOnClickListener {
            openImagePicker()
            binding.plantImage.setImageBitmap(selectedImageBitmap)
        }

        id = arguments?.getInt("id")

        binding.apply {
            plantNameText.setText(arguments?.getString("name"))
            plantingDateText.setText(arguments?.getString("plantingDate"))
            plantDiseaseText.setText(arguments?.getString("disease"))
            plantFertilizingFrequencyText.setText(arguments?.getString("fertilizingFreq"))
            plantSunlightText.setText(arguments?.getString("sunlight"))
            plantWateringText.setText(arguments?.getString("watering"))
            plantImage.setImageBitmap(HelperFunctions.toBitmap(arguments?.getByteArray("image")))
        }

        binding.finishButton.setOnClickListener {
            if (binding.plantNameText.text.toString().isNotEmpty()) {
                val plant = MyPlant(
                    id!!,
                    binding.plantNameText.text.toString(),
                    selectedImageBitmap,
                    binding.plantingDateText.text.toString(),
                    binding.plantSunlightText.text.toString(),
                    binding.plantWateringText.text.toString(),
                    binding.plantFertilizingFrequencyText.text.toString(),
                    binding.plantDiseaseText.text.toString()
                )

                viewModel.updateMyPlant(plant)
                findNavController().navigate(R.id.action_editMyPlantItemFragment_to_myPlantsFragment)
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


    private fun openImagePicker() {
        val options = arrayOf<CharSequence>(
            getString(R.string.take_picture),
            getString(R.string.choose_from_gallery),
            getString(R.string.dialog_cancel)
        )
        val builder = androidx.appcompat.app.AlertDialog.Builder(requireContext())
        builder.setTitle(getString(R.string.select_image))
        builder.setItems(options) { dialog, which ->
            when (which) {
                0 ->
                    if (ActivityCompat.checkSelfPermission(
                            requireContext(),
                            Manifest.permission.CAMERA
                        ) != PackageManager.PERMISSION_GRANTED
                    )
                        cameraPermissionsLauncher.launch(Manifest.permission.CAMERA)
                    else
                        openCamera()

                1 ->
                    if (ActivityCompat.checkSelfPermission(
                            requireContext(),
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        ) != PackageManager.PERMISSION_GRANTED
                    )
                        galleryPermissionsLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                    else
                        openGallery()


                else -> dialog.dismiss()
            }
        }
        val dialog = builder.create()
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
        dialog.show()
    }

    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraLauncher.launch(cameraIntent)
    }

    private fun openGallery() {
        val galleryIntent =
            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        galleryLauncher.launch(galleryIntent)
    }


}


