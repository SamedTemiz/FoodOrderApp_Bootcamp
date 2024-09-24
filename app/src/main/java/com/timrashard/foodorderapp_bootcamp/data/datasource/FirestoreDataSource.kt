package com.timrashard.foodorderapp_bootcamp.data.datasource

import com.google.firebase.firestore.CollectionReference
import com.timrashard.foodorderapp_bootcamp.data.model.Yemekler
import com.timrashard.foodorderapp_bootcamp.domain.model.Order
import com.timrashard.foodorderapp_bootcamp.utils.Resource
import com.timrashard.foodorderapp_bootcamp.utils.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await

class FirestoreDataSource(var collectionReference: CollectionReference) {

    companion object {
        const val FAVORITES_COLLECTION = "favorites"
        const val ORDERS_COLLECTION = "orders"
    }

    suspend fun addFavorite(userId: String, yemek: Yemekler): Resource<Unit> {
        return try {
            collectionReference
                .document(userId)
                .collection(FAVORITES_COLLECTION)
                .document(yemek.yemek_id.toString())
                .set(yemek)
                .await()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.message.toString())
        }
    }

    fun getFavorites(userId: String): Flow<Resource<List<Yemekler>>> = flow {
        try {
            val documents = collectionReference
                .document(userId)
                .collection(FAVORITES_COLLECTION)
                .get()
                .await()

            val yemeklerList = documents.map { it.toObject(Yemekler::class.java) }
            emit(Resource.Success(yemeklerList))
        } catch (e: Exception) {
            emit(Resource.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.IO)

    suspend fun deleteFromFavorites(userId: String, yemekId: Int): Resource<Unit> {
        return try {
            collectionReference
                .document(userId)
                .collection(FAVORITES_COLLECTION)
                .document(yemekId.toString())
                .delete()
                .await()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.message.toString())
        }
    }

    // ORDERS

    suspend fun addOrder(userId: String, order: Order): Resource<Unit> {
        return try {
            val orderId = Utils.getCurrentEpoch()
            val updatedOrder = order.copy(orderId = orderId)

            collectionReference
                .document(userId)
                .collection(ORDERS_COLLECTION)
                .document(orderId)
                .set(updatedOrder)
                .await()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.message.toString())
        }
    }

    fun getOrders(userId: String): Flow<Resource<List<Order>>> = flow {
        try {
            val documents = collectionReference
                .document(userId)
                .collection(ORDERS_COLLECTION)
                .get()
                .await()

            val ordersList = documents.map { it.toObject(Order::class.java) }.reversed()
            emit(Resource.Success(ordersList))
        } catch (e: Exception) {
            emit(Resource.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.IO)

    suspend fun deleteOrder(userId: String, orderId: Int): Resource<Unit> {
        return try {
            collectionReference
                .document(userId)
                .collection(ORDERS_COLLECTION)
                .document(orderId.toString())
                .delete()
                .await()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.message.toString())
        }
    }
}
