package com.timrashard.foodorderapp_bootcamp.domain.model

data class YemekModel(
    val yemek_id: Int,
    val yemek_adi: String,
    val yemek_resim_adi: String,
    val yemek_fiyat: Int,
    val yemek_kategori: String? = "all"
)