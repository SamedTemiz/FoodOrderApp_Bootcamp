package com.timrashard.foodorderapp_bootcamp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timrashard.foodorderapp_bootcamp.R
import com.timrashard.foodorderapp_bootcamp.data.model.Yemekler
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

    val foodWords = listOf("Izgara Somon", "Izgara Tavuk", "Köfte", "Lazanya", "Makarna", "Pizza")
    val drinkWords = listOf("Ayran", "Fanta", "Kahve", "Su")
    val dessertWords = listOf("Baklava", "Kadayıf", "Sütlaç", "Tiramisu")

    private val _itemListState = MutableStateFlow<Resource<YemeklerResponse>>(Resource.Loading())
    val itemListState: StateFlow<Resource<YemeklerResponse>> = _itemListState

    private val _itemSubLists = MutableStateFlow<List<ItemSubList>>(emptyList())
    val itemSubLists: StateFlow<List<ItemSubList>> = _itemSubLists

    var allItems = listOf<Yemekler>()
    var foods = listOf<Yemekler>()
    var drinks = listOf<Yemekler>()
    var desserts = listOf<Yemekler>()

    init {
        getAllFoods()
    }

    private fun getAllFoods() {
        viewModelScope.launch {
            foodRepository.getAllFoods().collect { result ->
                _itemListState.value = result

                if (result is Resource.Success) {
                    allItems = result.data?.yemekler ?: emptyList()
                    foods = allItems.filter { item -> foodWords.any { it.lowercase() == item.yemek_adi.lowercase() } }
                    drinks = allItems.filter { item -> drinkWords.any { it.lowercase() == item.yemek_adi.lowercase() } }
                    desserts = allItems.filter { item -> dessertWords.any { it.lowercase() == item.yemek_adi.lowercase() } }

                    _itemSubLists.value = listOf(
                        ItemSubList("All", allItems),
                        ItemSubList("Foods", foods),
                        ItemSubList("Drinks", drinks),
                        ItemSubList("Desserts", desserts)
                    )
                }
            }
        }
    }

    fun searchWithQuery(query: String) : List<Yemekler> {
        val filteredItems = allItems.filter { item ->
            item.yemek_adi.contains(query, ignoreCase = true)
        }

        return filteredItems
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

data class ItemSubList(var title: String, var items: List<Yemekler>)