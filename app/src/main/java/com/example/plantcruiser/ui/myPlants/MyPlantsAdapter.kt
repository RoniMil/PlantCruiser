package com.example.plantcruiser.ui.myPlants

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.plantcruiser.data.models.MyPlant
import com.example.plantcruiser.databinding.ItemPlantBinding

// plant adapter for the myplants list recycler view
class MyPlantsAdapter(private val listener: PlantItemListener) :
    RecyclerView.Adapter<MyPlantsAdapter.PlantViewHolder>() {

    private val plants = ArrayList<MyPlant>()

    // holder for MyPlant item Class
    class PlantViewHolder(
        private val itemBinding: ItemPlantBinding,
        private val listener: PlantItemListener
    ) : RecyclerView.ViewHolder(itemBinding.root),
        View.OnClickListener {

        private lateinit var plant: MyPlant

        init {
            itemBinding.root.setOnClickListener(this)
        }

        // bind current plant to a cell in the recycler
        fun bind(item: MyPlant) {
            this.plant = item
            itemBinding.plantName.text = item.name
            Glide.with(itemBinding.root)
                .load(item.image)
                .into(itemBinding.plantImage)
        }

        override fun onClick(v: View?) {
            listener.onPlantClick(plant.id)
        }


    }

    // sets the plants to show in the recycler
    fun setPlants(plants: Collection<MyPlant>) {
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
        fun onPlantClick(plantId : Int)
    }

}