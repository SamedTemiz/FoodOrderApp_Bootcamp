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
            yemekAdi = yemek_adi,
            yemekResimAdi = yemek_resim_adi,
            yemekFiyat = yemek_fiyat,
            yemekSiparisAdet = yemek_siparis_adet,
            kullaniciAdi = kullanici_adi
        )
    }
}
