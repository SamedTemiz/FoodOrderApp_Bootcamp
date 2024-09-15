package com.timrashard.foodorderapp_bootcamp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.timrashard.foodorderapp_bootcamp.presentation.viewmodel.MainViewModel
import com.timrashard.foodorderapp_bootcamp.ui.theme.FoodOrderApp_BootcampTheme
import com.timrashard.foodorderapp_bootcamp.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val yemekler by mainViewModel.foods.collectAsState()

            FoodOrderApp_BootcampTheme {
                when (yemekler) {
                    is Resource.Loading -> {
                        Log.e("Yemekler", "Loading")
                    }

                    is Resource.Success -> {
                        yemekler.data?.yemekler?.forEach{ yemek ->
                            Log.e("Yemekler", yemek.yemek_adi)
                        }
                    }

                    is Resource.Error -> {
                        Log.e("Yemekler", yemekler.message.toString())
                    }
                }
            }
        }
    }
}