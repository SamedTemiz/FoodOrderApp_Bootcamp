package com.timrashard.foodorderapp_bootcamp.di

import android.content.Context
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.firestore
import com.timrashard.foodorderapp_bootcamp.data.datasource.AuthDataSource
import com.timrashard.foodorderapp_bootcamp.data.datasource.FirestoreDataSource
import com.timrashard.foodorderapp_bootcamp.data.datasource.FoodDataSource
import com.timrashard.foodorderapp_bootcamp.data.remote.FoodApi
import com.timrashard.foodorderapp_bootcamp.data.repository.AuthRepository
import com.timrashard.foodorderapp_bootcamp.data.repository.DataStoreRepository
import com.timrashard.foodorderapp_bootcamp.data.repository.FirestoreRepository
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
    fun provideFirebaseAuth() = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideCollectionReference() : CollectionReference {
        return Firebase.firestore.collection("foods")
    }

    @Provides
    @Singleton
    fun provideFoodDataSource(foodApi: FoodApi): FoodDataSource{
        return FoodDataSource(foodApi)
    }

    @Provides
    @Singleton
    fun provideAuthDataSource(firebaseAuth: FirebaseAuth): AuthDataSource{
        return AuthDataSource(firebaseAuth)
    }

    @Provides
    @Singleton
    fun provideFirestoreDataSource(collectionReference: CollectionReference): FirestoreDataSource{
        return FirestoreDataSource(collectionReference)
    }

    @Provides
    @Singleton
    fun provideFoodRepository(foodDataSource: FoodDataSource): FoodRepository{
        return FoodRepository(foodDataSource)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(authDataSource: AuthDataSource): AuthRepository{
        return AuthRepository(authDataSource)
    }

    @Provides
    @Singleton
    fun provideFirestoreRepository(firestoreDataSource: FirestoreDataSource): FirestoreRepository {
        return FirestoreRepository(firestoreDataSource)
    }

    @Provides
    @Singleton
    fun provideDataStoreRepository(@ApplicationContext context : Context): DataStoreRepository {
        return DataStoreRepository(context)
    }
}