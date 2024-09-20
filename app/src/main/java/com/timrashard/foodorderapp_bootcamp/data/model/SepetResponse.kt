package com.timrashard.foodorderapp_bootcamp.data.model

data class SepetResponse(
    var sepet_yemekler: List<SepetYemekler>?,
    var success: Int,
)