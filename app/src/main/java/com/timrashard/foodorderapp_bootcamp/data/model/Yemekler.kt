package com.timrashard.foodorderapp_bootcamp.data.model

import com.timrashard.foodorderapp_bootcamp.common.Constants

data class Yemekler(
    var yemek_id: Int,
    var yemek_adi: String,
    var yemek_resim_adi: String,
    var yemek_fiyat: Int
)

fun Yemekler.toSepetYemekler(siparisAdet: Int) : SepetYemekler{
    return SepetYemekler(
        sepet_yemek_id = 0,
        yemek_adi = this.yemek_adi,
        yemek_resim_adi = this.yemek_resim_adi,
        yemek_fiyat = this.yemek_fiyat,
        yemek_siparis_adet = siparisAdet,
        kullanici_adi = Constants.USER_NAME
    )
}