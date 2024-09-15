package com.timrashard.foodorderapp_bootcamp.domain.usecase

import com.timrashard.foodorderapp_bootcamp.data.model.SepetResponse
import com.timrashard.foodorderapp_bootcamp.data.model.YemeklerResponse
import com.timrashard.foodorderapp_bootcamp.data.repository.FoodRepository
import com.timrashard.foodorderapp_bootcamp.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAllCartFoodsUseCase @Inject constructor(
    private val foodRepository: FoodRepository
) {
    suspend operator fun invoke(kullaniciAdi: String): Flow<Resource<SepetResponse>> = flow {
        emit(Resource.Loading())
        try {
            val response = foodRepository.getAllCartFoods(kullaniciAdi)
            emit(Resource.Success(response))
        } catch (e: Exception) {
            emit(Resource.Error(message = e.message ?: "Unknown Error"))
        }
    }
}