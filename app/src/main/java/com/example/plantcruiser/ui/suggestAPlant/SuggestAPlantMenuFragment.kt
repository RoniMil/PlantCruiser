package com.example.plantcruiser.ui.suggestAPlant

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.plantcruiser.R
import com.example.plantcruiser.databinding.SuggestAPlantMenuFragmentBinding
import com.example.plantcruiser.utils.autoCleared
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SuggestAPlantMenuFragment : Fragment() {
//    private val viewModel: MyPlantItemViewModel by viewModels()

    private var binding: SuggestAPlantMenuFragmentBinding by autoCleared()

    private var location: Location? = null

//    @Inject
//    lateinit var fusedLocationClient: FusedLocationProviderClient
//
//    private lateinit var locationCallback: LocationCallback



//    val temperature
//
//    val light


    @Inject
    lateinit var fusedLocationClient: FusedLocationProviderClient

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SuggestAPlantMenuFragmentBinding.inflate(inflater, container, false)

        val locationPermissionsLauncher: ActivityResultLauncher<String> =
            registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { it ->
                if (it) {
                    if (ActivityCompat.checkSelfPermission(
                            requireContext(),
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        fusedLocationClient.lastLocation.addOnSuccessListener {
                            location = it
                        }
                    }

                } else {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.no_camera_permissions_msg),
                        Toast.LENGTH_SHORT
                    ).show()
                }


            }


        binding.environmentSensorButton.setOnClickListener {
            requestLocationPermissions(locationPermissionsLauncher)
            println(location)
        }




        return binding.root
    }

    private fun requestLocationPermissions(launcher: ActivityResultLauncher<String>) {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            launcher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        } else {
            fusedLocationClient.lastLocation.addOnSuccessListener {
                location = it
            }

        }
    }

}