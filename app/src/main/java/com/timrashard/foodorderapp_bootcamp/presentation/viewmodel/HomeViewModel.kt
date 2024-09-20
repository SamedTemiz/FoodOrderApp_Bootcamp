package com.timrashard.foodorderapp_bootcamp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timrashard.foodorderapp_bootcamp.R
import com.timrashard.foodorderapp_bootcamp.data.model.YemeklerResponse
import com.timrashard.foodorderapp_bootcamp.data.repository.FoodRepository
import com.timrashard.foodorderapp_bootcamp.domain.model.ChipItem
import com.timrashard.foodorderapp_bootcamp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val foodRepository: FoodRepository
) : ViewModel() {

    private val _foodList = MutableStateFlow<Resource<YemeklerResponse>>(Resource.Loading())
    val foodList: StateFlow<Resource<YemeklerResponse>> = _foodList

    init {
        getAllFoods()
    }

    private fun getAllFoods() {
        viewModelScope.launch {
            foodRepository.getAllFoods().collect {
                _foodList.value = it
            }
        }
    }

    fun filterWithChip(chipCategory: Int) {
        // 0 -> Tümü (All)
        // 1 -> Yemek (Food)
        // 2 -> İçecek (Drink)
        // 3 -> Tatlı (Dessert)

        when (chipCategory) {
            0 -> {}
            1 -> {}
            2 -> {}
            3 -> {}
            else -> {}
        }
    }

    fun getChipList(): List<ChipItem> {
        val chipList = listOf(
            ChipItem(icon = R.drawable.ic_all, label = "All"),
            ChipItem(icon = R.drawable.ic_food, label = "Food"),
            ChipItem(icon = R.drawable.ic_drink, label = "Drink"),
            ChipItem(icon = R.drawable.ic_dessert, label = "Dessert"),
        )

        return chipList
    }
}