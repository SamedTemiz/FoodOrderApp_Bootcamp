package com.timrashard.foodorderapp_bootcamp.data.repository

import com.timrashard.foodorderapp_bootcamp.data.datasource.FirestoreDataSource
import com.timrashard.foodorderapp_bootcamp.data.model.Yemekler
import com.timrashard.foodorderapp_bootcamp.domain.model.Order
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

    suspend fun addOrder(userId: String, order: Order): Resource<Unit> {
        return firestoreDataSource.addOrder(userId, order)
    }

    fun getOrders(userId: String): Flow<Resource<List<Order>>> {
        return firestoreDataSource.getOrders(userId)
    }

    suspend fun deleteOrder(userId: String, orderId: Int): Resource<Unit> {
        return firestoreDataSource.deleteOrder(userId, orderId)
    }
}