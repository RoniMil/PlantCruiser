package com.example.plantcruiser.ui.suggestAPlant

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.plantcruiser.R
import com.example.plantcruiser.databinding.SuggestAPlantParamsSelectionFragmentBinding
import com.example.plantcruiser.utils.autoCleared
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SuggestAPlantParamsSelectionFragment : Fragment(), SensorEventListener {

    private var binding: SuggestAPlantParamsSelectionFragmentBinding by autoCleared()

    private var location: Location? = null


    private lateinit var sensorManager: SensorManager

    private var lightSensor: Sensor? = null

    private var ambientTempSensor: Sensor? = null

    private var light: Float? = null
    private var ambientTemp: Float? = null


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
        binding = SuggestAPlantParamsSelectionFragmentBinding.inflate(inflater, container, false)

        sensorManager = requireContext().getSystemService(Context.SENSOR_SERVICE) as SensorManager
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
        ambientTempSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)

        binding.environmentSensorButton.setOnClickListener {
            light?.let {
                setLightLevelAndIndoor(light!!)
            } ?: run {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.no_light_sensor_msg),
                    Toast.LENGTH_SHORT
                ).show()
            }
            ambientTemp?.let {
                binding.temperatureText.setText(ambientTemp.toString())
            } ?: run {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.no_ambient_temp_sensor_msg),
                    Toast.LENGTH_SHORT
                ).show()
            }

        }


        binding.finishButton.setOnClickListener {
            val indoor = binding.indoorsSwitch.isChecked.toString()
            val sunlight = setSunlight()
            val hardiness =
                getHardinessZone(binding.temperatureText.text.toString().toFloatOrNull())

            findNavController().navigate(
                R.id.action_suggestAPlantParamsSelectionFragment_to_suggestAPlantListFragment,
                bundleOf("indoor" to indoor, "hardiness" to hardiness, "sunlight" to sunlight)
            )
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

    override fun onResume() {
        super.onResume()
        if (lightSensor != null) {
            sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL)
        }
        if (ambientTempSensor != null) {
            sensorManager.registerListener(
                this,
                ambientTempSensor,
                SensorManager.SENSOR_DELAY_NORMAL
            )
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        val type = event?.sensor?.type
        when (type) {
            Sensor.TYPE_LIGHT -> light = event.values[0]
            Sensor.TYPE_AMBIENT_TEMPERATURE -> ambientTemp = event.values[0]
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    private fun setLightLevelAndIndoor(lightLevel: Float) {
        binding.apply {
            when {
                lightLevel <= 10 -> {
                    fullShade.isChecked = true
                    indoorsSwitch.isChecked = true
                }

                lightLevel > 10 && lightLevel <= 100 -> {
                    partShade.isChecked = true
                    indoorsSwitch.isChecked = true

                }

                lightLevel > 100 && lightLevel <= 1000 -> {
                    sunPartShade.isChecked = true
                    indoorsSwitch.isChecked = false
                }

                lightLevel > 1000 -> {
                    fullSun.isChecked = true
                    indoorsSwitch.isChecked = false
                }
            }
        }
    }

    private fun getHardinessZone(tempCelsius: Float?): String {
        tempCelsius?.let {
            val normalizedTemp = it - 40
            return when {
                normalizedTemp <= -45.6 -> "1-9"
                normalizedTemp <= -40.0 -> "2-9"
                normalizedTemp <= -34.4 -> "3-9"
                normalizedTemp <= -28.9 -> "4-9"
                normalizedTemp <= -23.3 -> "5-9"
                normalizedTemp <= -17.8 -> "6-9"
                normalizedTemp <= -12.2 -> "7-9"
                normalizedTemp <= -6.7 -> "8-9"
                normalizedTemp <= -1.1 -> "9-9"
                normalizedTemp <= 4.4 -> "10-13"
                normalizedTemp <= 10.0 -> "11-13"
                normalizedTemp <= 15.6 -> "12-13"
                else -> "13-13"
            }

        } ?: run {
            return ""
        }

    }

    private fun setSunlight(): String {
        if (binding.fullShade.isChecked) return "full_shade"
        if (binding.partShade.isChecked) return "part_shade"
        if (binding.sunPartShade.isChecked) return "sun-part_shade"
        if (binding.fullSun.isChecked) return "full_sun"
        return ""
    }


}









