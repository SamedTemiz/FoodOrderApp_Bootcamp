package com.timrashard.foodorderapp_bootcamp.data.repository

import com.timrashard.foodorderapp_bootcamp.data.model.ApiResponse
import com.timrashard.foodorderapp_bootcamp.data.model.SepetResponse
import com.timrashard.foodorderapp_bootcamp.data.model.Yemekler
import com.timrashard.foodorderapp_bootcamp.data.model.YemeklerResponse
import com.timrashard.foodorderapp_bootcamp.data.remote.FoodApi
import retrofit2.http.Field
import javax.inject.Inject

class FoodRepository @Inject constructor(
    private val foodApi: FoodApi
) {

    suspend fun getAllFoods(): YemeklerResponse {
        return foodApi.getAllFoods()
    }

    suspend fun getAllCartFoods(kullaniciAdi: String): SepetResponse {
        return foodApi.getAllCartFoods(kullaniciAdi)
    }

    suspend fun addFoodToCart(
        yemekAdi: String,
        yemekResimAdi: String,
        yemekFiyat: Int,
        yemekSiparisAdet: Int,
        kullaniciAdi: String
    ): ApiResponse {
        return foodApi.addFoodToCart(
            yemek_adi = yemekAdi,
            yemek_resim_adi = yemekResimAdi,
            yemek_fiyat = yemekFiyat,
            yemek_siparis_adet = yemekSiparisAdet,
            kullanici_adi = kullaniciAdi
        )
    }

    suspend fun deleteFoodFromCart(sepetYemekId: Int, kullaniciAdi: String): ApiResponse {
        return foodApi.deleteFoodFromCart(
            sepet_yemek_id = sepetYemekId,
            kullanici_adi = kullaniciAdi
        )
    }
}