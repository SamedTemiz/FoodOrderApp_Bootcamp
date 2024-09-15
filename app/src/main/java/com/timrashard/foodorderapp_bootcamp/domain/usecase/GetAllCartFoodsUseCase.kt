package com.timrashard.foodorderapp_bootcamp.domain.usecase

import com.timrashard.foodorderapp_bootcamp.data.model.SepetResponse
import com.timrashard.foodorderapp_bootcamp.data.repository.FoodRepository
import javax.inject.Inject

class GetAllCartFoodsUseCase @Inject constructor(
    private val foodRepository: FoodRepository
) {
    suspend operator fun invoke(kullanici_adi: String): SepetResponse {
        return foodRepository.getAllCartFoods(kullanici_adi)
    }
}
