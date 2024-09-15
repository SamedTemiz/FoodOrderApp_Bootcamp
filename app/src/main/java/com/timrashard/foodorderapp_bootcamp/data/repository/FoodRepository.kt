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

    suspend fun getAllCartFoods(kullanici_adi: String): SepetResponse {
        return foodApi.getAllCartFoods(kullanici_adi)
    }

    suspend fun addFoodToCart(
        yemek_adi: String,
        yemek_resim_adi: String,
        yemek_fiyat: Int,
        yemek_siparis_adet: Int,
        kullanici_adi: String
    ): ApiResponse {
        return foodApi.addFoodToCart(
            yemek_adi = yemek_adi,
            yemek_resim_adi = yemek_resim_adi,
            yemek_fiyat = yemek_fiyat,
            yemek_siparis_adet = yemek_siparis_adet,
            kullanici_adi = kullanici_adi
        )
    }

    suspend fun deleteFoodFromCart(sepet_yemek_id: Int, kullanici_adi: String): ApiResponse {
        return foodApi.deleteFoodFromCart(
            sepet_yemek_id = sepet_yemek_id,
            kullanici_adi = kullanici_adi
        )
    }
}