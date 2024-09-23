package com.timrashard.foodorderapp_bootcamp.domain.model

data class SepetYemekModel(
    var sepet_yemek_id : Int? = 0,
    var yemek_adi: String? = "",
    var yemek_resim_adi: String? = "",
    var yemek_fiyat: Int? = 0,
    var yemek_siparis_adet:Int? = 0,
    var kullanici_adi: String? = ""
)
