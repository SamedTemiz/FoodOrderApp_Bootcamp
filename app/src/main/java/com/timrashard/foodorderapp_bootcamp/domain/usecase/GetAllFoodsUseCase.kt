package com.timrashard.foodorderapp_bootcamp.domain.usecase

import com.timrashard.foodorderapp_bootcamp.data.model.YemeklerResponse
import com.timrashard.foodorderapp_bootcamp.data.repository.FoodRepository
import com.timrashard.foodorderapp_bootcamp.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAllFoodsUseCase @Inject constructor(
    private val foodRepository: FoodRepository
) {
    suspend operator fun invoke(): Flow<Resource<YemeklerResponse>> = flow {
        emit(Resource.Loading())
        try {
            val response = foodRepository.getAllFoods()
            emit(Resource.Success(response))
        } catch (e: Exception) {
            emit(Resource.Error(message = e.message ?: "Unknown Error"))
        }
    }
}