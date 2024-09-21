package com.timrashard.foodorderapp_bootcamp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timrashard.foodorderapp_bootcamp.data.model.SepetYemekler
import com.timrashard.foodorderapp_bootcamp.data.repository.FoodRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val foodRepository: FoodRepository
) : ViewModel() {

    private val _cartFoods = MutableStateFlow<List<SepetYemekler>>(emptyList())
    val cartFoods: StateFlow<List<SepetYemekler>> = _cartFoods

    private val _totalPrice = MutableStateFlow(0)
    val totalPrice: StateFlow<Int> = _totalPrice

    private val _itemsCount = MutableStateFlow(0)
    val itemsCount: StateFlow<Int> = _itemsCount

    init {
        fetchCartFoods()
    }

    private fun fetchCartFoods() {
        viewModelScope.launch {
            foodRepository.getAllCartFoods().collectLatest { list ->
                Log.d("SharedViewModel", "Fetched cart foods: $list")
                _cartFoods.value = list
                _itemsCount.value = list.size
            }
        }
    }

    fun calculateTotalPrice() {
        val foodList = cartFoods.value
        _totalPrice.value = foodList.sumOf { it.yemek_siparis_adet * it.yemek_fiyat }
    }

    fun addFoodToCart(yemek: SepetYemekler, isDetails: Boolean) {
        viewModelScope.launch {
            var siparisAdet: Int = yemek.yemek_siparis_adet

            val existingFood = cartFoods.value.find {
                it.sepet_yemek_id == yemek.sepet_yemek_id || it.yemek_adi == yemek.yemek_adi
            }

            if (existingFood != null) {
                siparisAdet = if (isDetails) existingFood.yemek_siparis_adet + yemek.yemek_siparis_adet else yemek.yemek_siparis_adet
                foodRepository.deleteFoodFromCart(existingFood.sepet_yemek_id)
            }

            foodRepository.addFoodToCart(
                yemekAdi = yemek.yemek_adi,
                yemekResimAdi = yemek.yemek_resim_adi,
                yemekFiyat = yemek.yemek_fiyat,
                yemekSiparisAdet = siparisAdet
            )

            fetchCartFoods()
        }
    }

    fun deleteFoodFromCart(sepetYemekler: SepetYemekler) {
        viewModelScope.launch {
            val result = foodRepository.deleteFoodFromCart(sepetYemekler.sepet_yemek_id)
            Log.d("SharedViewModel", "Deleted ${sepetYemekler.yemek_adi} from cart")
            fetchCartFoods()
        }
    }

    fun clearCart() {
        viewModelScope.launch {
            cartFoods.value.forEach {
                foodRepository.deleteFoodFromCart(it.sepet_yemek_id)
                Log.d("SharedViewModel", "Cart is cleared")
                _cartFoods.value = emptyList()
                _itemsCount.value = 0
            }
        }
    }
}