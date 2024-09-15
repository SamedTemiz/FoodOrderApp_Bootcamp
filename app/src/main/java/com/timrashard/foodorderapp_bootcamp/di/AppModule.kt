package com.timrashard.foodorderapp_bootcamp.di

import com.timrashard.foodorderapp_bootcamp.data.remote.FoodApi
import com.timrashard.foodorderapp_bootcamp.data.repository.FoodRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFoodRepository(foodApi: FoodApi): FoodRepository{
        return FoodRepository(foodApi)
    }
}