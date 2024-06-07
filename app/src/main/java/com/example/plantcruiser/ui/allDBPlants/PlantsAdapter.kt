package com.example.plantcruiser.ui.allDBPlants

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.plantcruiser.data.models.Plant
import com.example.plantcruiser.databinding.ItemPlantBinding

class PlantsAdapter() :
    RecyclerView.Adapter<PlantsAdapter.PlantViewHolder>() {

    private val plants = ArrayList<Plant>()

    class PlantViewHolder(private val itemBinding: ItemPlantBinding) :
        RecyclerView.ViewHolder(itemBinding.root),
        View.OnClickListener {

        private lateinit var plant: Plant

        init {
            itemBinding.root.setOnClickListener(this)
        }

        fun bind(item: Plant) {
            this.plant = item
            itemBinding.plantName.text = item.name
            Glide.with(itemBinding.root)
                .load(item.image)
                .circleCrop()
                .into(itemBinding.plantImage)
        }

        override fun onClick(v: View?) {

        }


    }

    fun setPlants(plants: Collection<Plant>) {
        this.plants.clear()
        this.plants.addAll(plants)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantViewHolder {
        val binding = ItemPlantBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlantViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlantViewHolder, position: Int) =
        holder.bind(plants[position])


    override fun getItemCount() = plants.size

}