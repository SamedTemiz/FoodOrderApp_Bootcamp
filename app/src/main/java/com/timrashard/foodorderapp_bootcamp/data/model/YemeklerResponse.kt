package com.timrashard.foodorderapp_bootcamp.data.model

data class YemeklerResponse(
    var yemekler: List<Yemekler>,
    var success: Int,
    var message: String?,
)