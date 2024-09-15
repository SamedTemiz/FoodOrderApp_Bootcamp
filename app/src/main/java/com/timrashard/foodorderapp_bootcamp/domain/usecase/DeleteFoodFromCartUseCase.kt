package com.timrashard.foodorderapp_bootcamp.domain.usecase

import com.timrashard.foodorderapp_bootcamp.data.model.ApiResponse
import com.timrashard.foodorderapp_bootcamp.data.repository.FoodRepository
import javax.inject.Inject

class DeleteFoodFromCartUseCase @Inject constructor(
    private val foodRepository: FoodRepository
) {
    suspend operator fun invoke(sepet_yemek_id: Int, kullanici_adi: String): ApiResponse {
        return foodRepository.deleteFoodFromCart(sepet_yemek_id, kullanici_adi)
    }
}
