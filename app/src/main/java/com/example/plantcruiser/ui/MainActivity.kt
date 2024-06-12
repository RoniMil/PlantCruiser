package com.example.plantcruiser.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.plantcruiser.R
import com.example.plantcruiser.data.models.PlantList
import com.example.plantcruiser.databinding.ActivityMainBinding
import com.example.plantcruiser.network.ApiService
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import retrofit2.Callback
import retrofit2.Call
import retrofit2.Response


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var apiService: ApiService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Use the apiService to make a network call
        val call = apiService.getYourData()
        call.enqueue(object : Callback<PlantList> {
            override fun onResponse(p0: Call<PlantList>, response: Response<PlantList>) {
                if (response.isSuccessful) {
                    // Handle the successful response
                    println("Response: ${response.body()}")
                } else {
                    // Handle the error response
                    println("Error: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<PlantList>, t: Throwable) {
                // Handle the failure
                println("Network call failed: ${t.message}")
            }
        })

//        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
//        val navController = navHostFragment.navController
//        val appBarConfiguration = AppBarConfiguration(navController.graph)
//        binding.toolbar.setupWithNavController(navController,appBarConfiguration)

    }
}
