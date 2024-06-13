package com.example.plantcruiser.ui.dbPlantList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.ui.text.toLowerCase
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.plantcruiser.R
import com.example.plantcruiser.data.models.Plant
import com.example.plantcruiser.databinding.DbPlantDetailsFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import com.example.plantcruiser.utils.autoCleared
import com.example.plantcruiser.utils.Error
import com.example.plantcruiser.utils.Loading
import com.example.plantcruiser.utils.Success
import java.util.Locale

@AndroidEntryPoint
class DBPlantDetailFragment : Fragment() {
    private val viewModel: DBPlantDetailViewModel by viewModels()

    private var binding: DbPlantDetailsFragmentBinding by autoCleared()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DbPlantDetailsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.plant.observe(viewLifecycleOwner) {
            when (it.status) {
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
                    Toast.makeText(requireContext(), it.status.message, Toast.LENGTH_LONG).show()
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
        binding.plantCycleText.text = getCycle(plant.cycle)
        binding.plantSunlightText.text = getSunlight(plant.sunlight)
        binding.plantWateringText.text = getWatering(plant.watering)
        Glide.with(requireContext()).load(plant.default_image?.regular_url).circleCrop()
            .into(binding.plantImage)
    }

    // helper function for localization of cycle values
    private fun getCycle(cycle: String): String {
        return when (cycle.lowercase()) {
            "perennial" -> getString(R.string.perennial)
            "herbaceous perennial" -> getString(R.string.herbaceous_perennial)
            "annual" -> getString(R.string.annual)
            "biennial" -> getString(R.string.biennial)
            else -> getString(R.string.biannual)
        }
    }

    // helper function for localization of watering values
    private fun getWatering(watering: String): String {
        return when (watering.lowercase()) {
            "frequent" -> getString(R.string.frequent)
            "average" -> getString(R.string.average)
            "none" -> getString(R.string.none)
            else -> getString(R.string.minimum)
        }
    }

    // helper function for localization of sunlight values
    private fun getSunlight(sunlightList: List<String>): String {
        return sunlightList.joinToString(separator = ", ") { sunlight ->
            when (sunlight.lowercase()) {
                in listOf("full shade", "sheltered") -> getString(R.string.full_shade)
                in listOf(
                    "part shade",
                    "filtered shade",
                    "deep shade"
                ) -> getString(R.string.part_shade)

                "part sun/part shade" -> getString(R.string.sun_part_shade)
                else -> getString(R.string.full_sun)
            }
        }
    }


}