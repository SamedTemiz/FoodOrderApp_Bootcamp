package com.timrashard.foodorderapp_bootcamp.data.model

import com.timrashard.foodorderapp_bootcamp.common.Constants
import com.timrashard.foodorderapp_bootcamp.domain.model.YemekModel

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

fun Yemekler.toYemekModel(kategori: String) : YemekModel{
    return YemekModel(
        yemek_id = this.yemek_id,
        yemek_adi = this.yemek_adi,
        yemek_resim_adi = this.yemek_resim_adi,
        yemek_fiyat = this.yemek_fiyat,
        yemek_kategori = kategori
    )
}