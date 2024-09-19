package com.timrashard.foodorderapp_bootcamp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timrashard.foodorderapp_bootcamp.common.Constants
import com.timrashard.foodorderapp_bootcamp.data.model.SepetResponse
import com.timrashard.foodorderapp_bootcamp.data.model.SepetYemekler
import com.timrashard.foodorderapp_bootcamp.data.model.YemeklerResponse
import com.timrashard.foodorderapp_bootcamp.domain.usecase.AddFoodToCartUseCase
import com.timrashard.foodorderapp_bootcamp.domain.usecase.DeleteFoodFromCartUseCase
import com.timrashard.foodorderapp_bootcamp.domain.usecase.GetAllCartFoodsUseCase
import com.timrashard.foodorderapp_bootcamp.domain.usecase.GetAllFoodsUseCase
import com.timrashard.foodorderapp_bootcamp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val getAllCartFoodsUseCase: GetAllCartFoodsUseCase,
    private val addFoodToCartUseCase: AddFoodToCartUseCase,
    private val deleteFoodFromCartUseCase: DeleteFoodFromCartUseCase
) : ViewModel() {

    private val _cartFoods = MutableStateFlow<Resource<SepetResponse>>(Resource.Loading())
    val cartFoods: StateFlow<Resource<SepetResponse>> = _cartFoods

    private val _totalPrice = MutableStateFlow(0)
    val totalPrice : StateFlow<Int> = _totalPrice

    fun calculateTotalPrice(foodList: List<SepetYemekler>) {
        val totalPrice = foodList.sumOf { it.yemek_fiyat * it.yemek_siparis_adet }
        _totalPrice.value = totalPrice
    }

    fun getCartFoods() {
        viewModelScope.launch {
            getAllCartFoodsUseCase.invoke(Constants.USER_NAME).collect { resource ->
                _cartFoods.value = resource
            }
        }
    }

    fun addFoodToCart(sepetYemekler: SepetYemekler, siparisAdet: Int) {
        viewModelScope.launch {
            val currentCart = _cartFoods.value
            if (currentCart is Resource.Success) {
                val existingFood = currentCart.data?.sepet_yemekler?.find {
                    it.yemek_adi == sepetYemekler.yemek_adi
                }

                if (existingFood != null) {
                    val updatedFood = existingFood.copy(
                        yemek_siparis_adet = existingFood.yemek_siparis_adet + siparisAdet
                    )

                    val updatedCartList = currentCart.data.sepet_yemekler.toMutableList().apply {
                        remove(existingFood)
                        add(updatedFood)
                    }
                    _cartFoods.value = Resource.Success(currentCart.data.copy(sepet_yemekler = updatedCartList))
                } else {
                    addFoodToCartUseCase.invoke(
                        yemek_adi = sepetYemekler.yemek_adi,
                        yemek_resim_adi = sepetYemekler.yemek_resim_adi,
                        yemek_fiyat = sepetYemekler.yemek_fiyat,
                        yemek_siparis_adet = siparisAdet,
                        kullanici_adi = Constants.USER_NAME
                    )
                }
            }
        }
    }


    fun deleteFoodFromCart(sepetYemekler: SepetYemekler){
        viewModelScope.launch {
            deleteFoodFromCartUseCase.invoke(
                sepet_yemek_id = sepetYemekler.sepet_yemek_id,
                kullanici_adi = Constants.USER_NAME
            )
        }
    }
}