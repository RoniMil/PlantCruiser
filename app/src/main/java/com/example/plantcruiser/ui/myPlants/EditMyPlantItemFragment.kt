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

// fragment for editing an existing plant from the my plants list
@AndroidEntryPoint
class EditMyPlantItemFragment : Fragment() {
    private val viewModel: MyPlantItemViewModel by viewModels()

    private var binding: AddEditMyPlantItemFragmentBinding by autoCleared()

    private var selectedImageBitmap: Bitmap? = null

    private var id: Int? = null

    // launcher for opening camera
    private val cameraLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val imageBitmap = result.data?.extras?.get("data") as Bitmap
                // set the image to be uploaded to the item as the one taken with the camera
                selectedImageBitmap = imageBitmap
                binding.plantImage.setImageBitmap(imageBitmap)
            }
        }

    // launcher for opening gallery
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
                    // set the image to be uploaded to the item as the one chosen in the gallery
                    selectedImageBitmap = imageBitmap
                    binding.plantImage.setImageBitmap(imageBitmap)
                }
            }


        }

    // launcher for requesting camera permissions
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

    // launcher for requesting gallery permissions
    private val galleryPermissionsLauncher: ActivityResultLauncher<String> =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) {
            if (it)
                openGallery()
            else
                Toast.makeText(
                    requireContext(),
                    getString(R.string.no_gallery_permissions_msg),
                    Toast.LENGTH_SHORT
                ).show()
        }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AddEditMyPlantItemFragmentBinding.inflate(inflater, container, false)

        // listener for triggering the select image dialog from gallery or camera
        binding.selectImageButton.setOnClickListener {
            openImagePicker()
            // preserves null as the image if no image was chosen
            binding.plantImage.setImageBitmap(selectedImageBitmap)
        }

        // save the plant's id and image from before edit (passed as bundle from details fragment)
        id = arguments?.getInt("id")
        selectedImageBitmap = HelperFunctions.toBitmap(arguments?.getByteArray("image"))

        // save the plant's details from before edit to be presented to the user when entering edit mode
        binding.apply {
            plantNameText.setText(arguments?.getString("name"))
            plantingDateText.setText(arguments?.getString("plantingDate"))
            plantDiseaseText.setText(arguments?.getString("disease"))
            plantFertilizingFrequencyText.setText(arguments?.getString("fertilizingFreq"))
            plantSunlightText.setText(arguments?.getString("sunlight"))
            plantWateringText.setText(arguments?.getString("watering"))
            plantImage.setImageBitmap(selectedImageBitmap)
        }

        // listener for editing the chosen plant in the myPlants DB
        binding.finishButton.setOnClickListener {
            // ensure plant is not empty, else show the user a toast
            if (binding.plantNameText.text.toString().isNotEmpty()) {
                val plant = MyPlant(
                    id!!, // ensures the same plant object in the data base will be modified with the new data
                    binding.plantNameText.text.toString(),
                    selectedImageBitmap,
                    binding.plantingDateText.text.toString(),
                    binding.plantSunlightText.text.toString(),
                    binding.plantWateringText.text.toString(),
                    binding.plantFertilizingFrequencyText.text.toString(),
                    binding.plantDiseaseText.text.toString()
                )
                viewModel.updateMyPlant(plant)
                // return to myPlants list fragment
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

    // function for showing the image picker dialog to the user
    private fun openImagePicker() {
        val options = arrayOf<CharSequence>(
            getString(R.string.take_picture),
            getString(R.string.choose_from_gallery),
            getString(R.string.dialog_cancel)
        )
        val builder = androidx.appcompat.app.AlertDialog.Builder(requireContext())
        builder.setTitle(getString(R.string.select_image))
        // options are either camera or gallery
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

    // function for opening camera
    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraLauncher.launch(cameraIntent)
    }

    // function for opening gallery
    private fun openGallery() {
        val galleryIntent =
            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        galleryLauncher.launch(galleryIntent)
    }


}


