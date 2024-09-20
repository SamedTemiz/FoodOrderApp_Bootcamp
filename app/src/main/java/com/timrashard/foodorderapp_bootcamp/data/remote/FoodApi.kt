package com.timrashard.foodorderapp_bootcamp.data.remote

import com.timrashard.foodorderapp_bootcamp.common.Constants
import com.timrashard.foodorderapp_bootcamp.data.model.ApiResponse
import com.timrashard.foodorderapp_bootcamp.data.model.SepetResponse
import com.timrashard.foodorderapp_bootcamp.data.model.YemeklerResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST


interface FoodApi {

    // BASE_URL: http://kasimadalan.pe.hu/

    // Tüm yemekleri getir          ->  yemekler/tumYemekleriGetir.php
    // Sepetteki yemekleri getir:   ->  yemekler/sepettekiYemekleriGetir.php
    // Sepete yemek ekleme:         ->  yemekler/sepeteYemekEkle.php
    // Sepetten yemek silme:        ->  yemekler/sepettenYemekSil.php
    // Yemek resimlerini alma:      ->  yemekler/resimler/yemek_adi.png

    @GET("yemekler/tumYemekleriGetir.php")
    suspend fun getAllFoods(): YemeklerResponse

    @POST("yemekler/sepettekiYemekleriGetir.php")
    @FormUrlEncoded
    suspend fun getAllCartFoods(
        @Field("kullanici_adi") kullanici_adi: String = Constants.USER_NAME,
    ): SepetResponse

    @POST("yemekler/sepeteYemekEkle.php")
    @FormUrlEncoded
    suspend fun addFoodToCart(
        @Field("yemek_adi") yemek_adi: String,
        @Field("yemek_resim_adi") yemek_resim_adi: String,
        @Field("yemek_fiyat") yemek_fiyat: Int,
        @Field("yemek_siparis_adet") yemek_siparis_adet: Int,
        @Field("kullanici_adi") kullanici_adi: String = Constants.USER_NAME,
    ) : ApiResponse

    @POST("yemekler/sepettenYemekSil.php")
    @FormUrlEncoded
    suspend fun deleteFoodFromCart(
        @Field("sepet_yemek_id") sepet_yemek_id: Int,
        @Field("kullanici_adi") kullanici_adi: String = Constants.USER_NAME,
    ) : ApiResponse
}