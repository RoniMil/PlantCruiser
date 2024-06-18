package com.example.plantcruiser.ui.suggestAPlant

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.plantcruiser.R
import com.example.plantcruiser.databinding.SuggestAPlantParamsSelectionFragmentBinding
import com.example.plantcruiser.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint

// fragment for allowing the user to choose parameters for suggestions
@AndroidEntryPoint
class SuggestAPlantParamsSelectionFragment : Fragment(), SensorEventListener {

    private var binding: SuggestAPlantParamsSelectionFragmentBinding by autoCleared()

    // sensors for environment sensors option, with manager for them
    private lateinit var sensorManager: SensorManager
    private var lightSensor: Sensor? = null
    private var ambientTempSensor: Sensor? = null

    // vars to hold the light and ambient temperature values to pass as query params
    private var light: Float? = null
    private var ambientTemp: Float? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SuggestAPlantParamsSelectionFragmentBinding.inflate(inflater, container, false)

        // init sensor manager and retrieve sensors from android device
        sensorManager = requireContext().getSystemService(Context.SENSOR_SERVICE) as SensorManager
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
        ambientTempSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)

        // listener for the environment sensor option, triggers retrieving light and temp information from device if possible
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

        // listener for finish button, sets argument values and passes them to suggest a plant list fragment
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

    // register the sensors for sensor manager
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

    // unregister the sensors to conserve power
    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    // measures sensors output
    override fun onSensorChanged(event: SensorEvent?) {
        val type = event?.sensor?.type
        when (type) {
            Sensor.TYPE_LIGHT -> light = event.values[0]
            Sensor.TYPE_AMBIENT_TEMPERATURE -> ambientTemp = event.values[0]
        }
    }

    // required override, does nothing
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    // sets light and indoor arguments based on light level retrieved by sensor
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

                else -> {
                    fullSun.isChecked = true
                    indoorsSwitch.isChecked = false
                }
            }
        }
    }

    // normalizes given temperatures and matches to plant hardiness scale
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
        // if null, return empty string
        } ?: run {
            return ""
        }

    }

    // returns the button that's pressed as value, empty string if no button is selected.
    private fun setSunlight(): String {
        if (binding.fullShade.isChecked) return "full_shade"
        if (binding.partShade.isChecked) return "part_shade"
        if (binding.fullSun.isChecked) return "full_sun"
        return ""
    }


}









