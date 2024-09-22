package com.timrashard.foodorderapp_bootcamp.data.datasource

import android.util.Log
import com.timrashard.foodorderapp_bootcamp.data.model.ApiResponse
import com.timrashard.foodorderapp_bootcamp.data.model.SepetYemekler
import com.timrashard.foodorderapp_bootcamp.data.model.YemeklerResponse
import com.timrashard.foodorderapp_bootcamp.data.remote.FoodApi
import com.timrashard.foodorderapp_bootcamp.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FoodDataSource @Inject constructor(private var foodApi: FoodApi) {

    suspend fun getAllFoods(): Flow<Resource<YemeklerResponse>> = flow {
        emit(Resource.Loading())
        try {
            val response = withContext(Dispatchers.IO) {
                foodApi.getAllFoods()
            }
            if (response.success == 1) {
                emit(Resource.Success(response))
            } else {
                emit(Resource.Error(message = "Failed to fetch data"))
            }
        } catch (e: Exception) {
            emit(Resource.Error(message = e.message ?: "Unknown Error"))
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getAllCartFoods(): Flow<List<SepetYemekler>> = flow {
        try {
            val response = withContext(Dispatchers.IO) {
                foodApi.getAllCartFoods()
            }
            if (response.success == 1) {
                emit(response.sepet_yemekler?.sortedByDescending { it.yemek_fiyat } ?: emptyList())
            } else {
                emit(emptyList())
            }
        } catch (e: Exception) {
            Log.e("FoodDataSource", "getAllCartFoods: ${e.message}")
            emit(emptyList())
        }
    }.flowOn(Dispatchers.IO)

    suspend fun addFoodToCart(
        yemekAdi: String,
        yemekResimAdi: String,
        yemekFiyat: Int,
        yemekSiparisAdet: Int,
    ): ApiResponse {
        return withContext(Dispatchers.IO) {
            foodApi.addFoodToCart(
                yemek_adi = yemekAdi,
                yemek_resim_adi = yemekResimAdi,
                yemek_fiyat = yemekFiyat,
                yemek_siparis_adet = yemekSiparisAdet
            )
        }
    }

    suspend fun deleteFoodFromCart(sepetYemekId: Int): ApiResponse {
        return withContext(Dispatchers.IO) {
            foodApi.deleteFoodFromCart(sepet_yemek_id = sepetYemekId)
        }
    }
}