package com.timrashard.foodorderapp_bootcamp.domain.model

import com.timrashard.foodorderapp_bootcamp.data.model.SepetYemekler

data class Order(
    var orderId: String? = null,
    var orderDate: String? = null,
    var orderTotalPrice: String? = null,
    var orderItems: List<SepetYemekModel>? = null,
    var orderState: String? = null
)
