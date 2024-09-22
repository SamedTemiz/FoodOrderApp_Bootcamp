package com.timrashard.foodorderapp_bootcamp.di

import android.content.Context
import com.timrashard.foodorderapp_bootcamp.data.datasource.FoodDataSource
import com.timrashard.foodorderapp_bootcamp.data.remote.FoodApi
import com.timrashard.foodorderapp_bootcamp.data.repository.DataStoreRepository
import com.timrashard.foodorderapp_bootcamp.data.repository.FoodRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFoodDataSource(foodApi: FoodApi): FoodDataSource{
        return FoodDataSource(foodApi)
    }

    @Provides
    @Singleton
    fun provideFoodRepository(foodDataSource: FoodDataSource): FoodRepository{
        return FoodRepository(foodDataSource)
    }

    @Provides
    @Singleton
    fun provideDataStoreRepository(@ApplicationContext context : Context): DataStoreRepository {
        return DataStoreRepository(context = context)
    }
}