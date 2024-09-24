package com.timrashard.foodorderapp_bootcamp.data.repository

import com.timrashard.foodorderapp_bootcamp.data.datasource.FoodDataSource
import com.timrashard.foodorderapp_bootcamp.data.model.ApiResponse
import com.timrashard.foodorderapp_bootcamp.data.model.SepetYemekler
import com.timrashard.foodorderapp_bootcamp.data.model.YemeklerResponse
import com.timrashard.foodorderapp_bootcamp.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FoodRepository @Inject constructor(
    private val foodDataSource: FoodDataSource
) {

    suspend fun getAllFoods(): Flow<Resource<YemeklerResponse>> {
        return foodDataSource.getAllFoods()
    }

    suspend fun getAllCartFoods(): Flow<List<SepetYemekler>> {
        return foodDataSource.getAllCartFoods()
    }

    suspend fun addFoodToCart(
        yemekAdi: String,
        yemekResimAdi: String,
        yemekFiyat: Int,
        yemekSiparisAdet: Int,
    ): ApiResponse {
        return foodDataSource.addFoodToCart(
            yemekAdi = yemekAdi,
            yemekResimAdi = yemekResimAdi,
            yemekFiyat = yemekFiyat,
            yemekSiparisAdet = yemekSiparisAdet
        )
    }

    suspend fun deleteFoodFromCart(sepetYemekId: Int) {
        foodDataSource.deleteFoodFromCart(sepetYemekId)
    }
}