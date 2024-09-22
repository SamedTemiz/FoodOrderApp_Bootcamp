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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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

    private var _itemSubLists: List<ItemSubList> = emptyList()
    val itemSubLists: List<ItemSubList> get() = _itemSubLists

    var allItems = listOf<Yemekler>()

    init {
        getAllFoods()
    }

    private fun getAllFoods() {
        viewModelScope.launch {
            foodRepository.getAllFoods().collect { result ->
                if (result is Resource.Success) {
                    processFoodItems(result.data?.yemekler ?: emptyList())
                }

                _itemListState.value = result
            }
        }
    }

    private suspend fun processFoodItems(items: List<Yemekler>) = withContext(Dispatchers.IO) {
        allItems = items

        val foods = mutableListOf<Yemekler>()
        val drinks = mutableListOf<Yemekler>()
        val desserts = mutableListOf<Yemekler>()

        items.forEach { item ->
            if(foodWords.any { it.lowercase() == item.yemek_adi.lowercase() }){
                foods.add(item)
            }else if(drinkWords.any { it.lowercase() == item.yemek_adi.lowercase() }){
                drinks.add(item)
            }else if(dessertWords.any { it.lowercase() == item.yemek_adi.lowercase() }){
                desserts.add(item)
            }
        }

        _itemSubLists = listOf(
            ItemSubList("All", items),
            ItemSubList("Foods", foods),
            ItemSubList("Drinks", drinks),
            ItemSubList("Desserts", desserts)
        )
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