package com.timrashard.foodorderapp_bootcamp.domain.model

data class Order(
    var orderId: String? = null,
    var orderDate: String? = null,
    var orderTotalPrice: String? = null,
    var orderItems: List<SepetYemekModel>? = null,
    var orderState: String? = null
)
