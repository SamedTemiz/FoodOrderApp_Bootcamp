package com.timrashard.foodorderapp_bootcamp.data.repository

import com.timrashard.foodorderapp_bootcamp.data.datasource.FirestoreDataSource
import com.timrashard.foodorderapp_bootcamp.data.model.Yemekler
import com.timrashard.foodorderapp_bootcamp.utils.Resource
import kotlinx.coroutines.flow.Flow

class FirestoreRepository(val firestoreDataSource: FirestoreDataSource) {

    // FAVORITES

    suspend fun addFavorite(userId: String, yemek: Yemekler): Resource<Unit> {
        return firestoreDataSource.addFavorite(userId, yemek)
    }

    fun getFavorites(userId: String): Flow<Resource<List<Yemekler>>> {
        return firestoreDataSource.getFavorites(userId)
    }

    suspend fun deleteFromFavorites(userId: String, yemekId: Int): Resource<Unit> {
        return firestoreDataSource.deleteFromFavorites(userId, yemekId)
    }

    // ORDERS
    /*
    fun addOrder(
        userId: String,
        yemek: Yemekler,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) = firestoreDataSource.addOrder(
        userId, yemek, onSuccess, onFailure
    )

    fun getOrders(
        userId: String,
        onSuccess: (List<Yemekler>) -> Unit,
        onFailure: (Exception) -> Unit
    ) = firestoreDataSource.getOrders(userId, onSuccess, onFailure)

    fun deleteFromOrders(
        userId: String,
        yemekId: Int,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) = firestoreDataSource.deleteFromOrders(userId, yemekId, onSuccess, onFailure)
     */
}