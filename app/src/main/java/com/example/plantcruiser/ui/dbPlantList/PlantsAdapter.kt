package com.example.plantcruiser.ui.dbPlantList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.plantcruiser.data.models.Plant
import com.example.plantcruiser.databinding.ItemPlantBinding

// plant adapter for the DB list recycler view
class PlantsAdapter(private val listener: PlantItemListener) :
    RecyclerView.Adapter<PlantsAdapter.PlantViewHolder>() {

    private val plants = ArrayList<Plant>()

    // holder for plant item Class
    class PlantViewHolder(
        private val itemBinding: ItemPlantBinding,
        private val listener: PlantItemListener
    ) : RecyclerView.ViewHolder(itemBinding.root),
        View.OnClickListener {

        private lateinit var plant: Plant

        init {
            itemBinding.root.setOnClickListener(this)
        }

        // bind current plant to a cell in the recycler
        fun bind(item: Plant) {
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
    fun setPlants(plants: Collection<Plant>) {
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

    // listener interface for clicking the plants from DB list
    interface PlantItemListener {
        fun onPlantClick(plantId: Int)
    }

}