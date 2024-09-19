package com.timrashard.foodorderapp_bootcamp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.timrashard.foodorderapp_bootcamp.R
import com.timrashard.foodorderapp_bootcamp.domain.model.ChipItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel(){

    fun filterWithChip(chipCategory: Int) {
        // 0 -> Tümü (All)
        // 1 -> Yemek (Food)
        // 2 -> İçecek (Drink)
        // 3 -> Tatlı (Dessert)

        when(chipCategory){
            0 -> {}
            1 -> {}
            2 -> {}
            3 -> {}
            else -> {}
        }
    }

    fun getChipList() : List<ChipItem>{
        val chipList = listOf(
            ChipItem(icon = R.drawable.ic_all, label = "All"),
            ChipItem(icon = R.drawable.ic_food, label = "Food"),
            ChipItem(icon = R.drawable.ic_drink, label = "Drink"),
            ChipItem(icon = R.drawable.ic_dessert, label = "Dessert"),
        )

        return chipList
    }
}