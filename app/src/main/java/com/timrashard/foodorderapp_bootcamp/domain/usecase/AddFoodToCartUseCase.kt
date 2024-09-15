package com.timrashard.foodorderapp_bootcamp.domain.usecase

import com.timrashard.foodorderapp_bootcamp.data.model.ApiResponse
import com.timrashard.foodorderapp_bootcamp.data.repository.FoodRepository
import javax.inject.Inject

class AddFoodToCartUseCase @Inject constructor(
    private val foodRepository: FoodRepository
) {
    suspend operator fun invoke(
        yemek_adi: String,
        yemek_resim_adi: String,
        yemek_fiyat: Int,
        yemek_siparis_adet: Int,
        kullanici_adi: String
    ): ApiResponse {
        return foodRepository.addFoodToCart(
            yemek_adi = yemek_adi,
            yemek_resim_adi = yemek_resim_adi,
            yemek_fiyat = yemek_fiyat,
            yemek_siparis_adet = yemek_siparis_adet,
            kullanici_adi = kullanici_adi
        )
    }
}
