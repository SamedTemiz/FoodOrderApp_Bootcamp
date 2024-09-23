package com.timrashard.foodorderapp_bootcamp.data.model

import com.timrashard.foodorderapp_bootcamp.domain.model.SepetYemekModel

data class SepetYemekler(
    var sepet_yemek_id : Int,
    var yemek_adi: String,
    var yemek_resim_adi: String,
    var yemek_fiyat: Int,
    var yemek_siparis_adet:Int,
    var kullanici_adi: String
)

fun SepetYemekler.toSepetYemekModel() : SepetYemekModel{
    return SepetYemekModel(
        sepet_yemek_id = this.sepet_yemek_id,
        yemek_adi = this.yemek_adi,
        yemek_resim_adi = this.yemek_resim_adi,
        yemek_fiyat = this.yemek_fiyat,
        yemek_siparis_adet = this.yemek_siparis_adet,
        kullanici_adi = this.kullanici_adi
    )
}
