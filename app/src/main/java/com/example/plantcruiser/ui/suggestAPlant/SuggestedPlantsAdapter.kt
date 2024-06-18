package com.example.plantcruiser.ui.suggestAPlant

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.plantcruiser.data.models.SuggestedPlant
import com.example.plantcruiser.databinding.ItemPlantBinding

// plant adapter for the suggested plant list recycler view
class SuggestedPlantsAdapter(private val listener: PlantItemListener) :
    RecyclerView.Adapter<SuggestedPlantsAdapter.PlantViewHolder>() {

    private val plants = ArrayList<SuggestedPlant>()

    // holder for suggested plant item Class
    class PlantViewHolder(
        private val itemBinding: ItemPlantBinding,
        private val listener: PlantItemListener
    ) : RecyclerView.ViewHolder(itemBinding.root),
        View.OnClickListener {

        private lateinit var plant: SuggestedPlant

        init {
            itemBinding.root.setOnClickListener(this)
        }

        // bind current plant to a cell in the recycler
        fun bind(item: SuggestedPlant) {
            this.plant = item
            itemBinding.plantName.text = item.common_name
            Glide.with(itemBinding.root)
                .load(item.default_image?.regular_url)
                .into(itemBinding.plantImage)
        }

        override fun onClick(v: View?) {
            listener.onPlantClick(plant.id)
        }


    }

    // sets the plants to show in the recycler
    fun setPlants(plants: Collection<SuggestedPlant>) {
        this.plants.clear()
        this.plants.addAll(plants)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantViewHolder {
        val binding = ItemPlantBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlantViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: PlantViewHolder, position: Int) =
        holder.bind(plants[position])


    override fun getItemCount() = plants.size


    // listener interface for clicking the plants from suggested plant list
    interface PlantItemListener {
        fun onPlantClick(plantId : Int)
    }

}