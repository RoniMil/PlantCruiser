package com.example.plantcruiser.ui.suggestAPlant

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.plantcruiser.R
import com.example.plantcruiser.databinding.SuggestAPlantMenuFragmentBinding
import com.example.plantcruiser.utils.autoCleared
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SuggestAPlantMenuFragment : Fragment() {

    private var binding: SuggestAPlantMenuFragmentBinding by autoCleared()

    private var location: Location? = null

    @Inject
    lateinit var fusedLocationClient: FusedLocationProviderClient

    private val locationPermissionsLauncher: ActivityResultLauncher<String> =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) {
            if (it) {
                if (ActivityCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    getAndSetLocation(fusedLocationClient = fusedLocationClient)
                }

            } else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.no_location_permission),
                    Toast.LENGTH_SHORT
                ).show()
            }


        }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SuggestAPlantMenuFragmentBinding.inflate(inflater, container, false)




        binding.environmentSensorButton.setOnClickListener {
            requestLocationPermissions(locationPermissionsLauncher)
        }




        return binding.root
    }

    private fun requestLocationPermissions(launcher: ActivityResultLauncher<String>) {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            launcher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
        } else {
            getAndSetLocation(fusedLocationClient = fusedLocationClient)

        }
    }

    private fun getAndSetLocation(fusedLocationClient: FusedLocationProviderClient) {
        viewLifecycleOwner.lifecycleScope.launch {
            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                fusedLocationClient.getCurrentLocation(
                    Priority.PRIORITY_LOW_POWER,
                    CancellationTokenSource().token,
                ).addOnSuccessListener {
                    it?.let { fetchedLocation ->
                        location = fetchedLocation
                    }
                }
            } else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.no_location_permission),
                    Toast.LENGTH_SHORT
                ).show()
            }

        }
    }
}






