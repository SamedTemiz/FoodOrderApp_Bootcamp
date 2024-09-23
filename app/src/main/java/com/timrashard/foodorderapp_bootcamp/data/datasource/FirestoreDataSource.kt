package com.timrashard.foodorderapp_bootcamp.data.datasource

import com.google.firebase.firestore.CollectionReference
import com.timrashard.foodorderapp_bootcamp.data.model.Yemekler
import com.timrashard.foodorderapp_bootcamp.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await

class FirestoreDataSource(var collectionReference: CollectionReference) {

    companion object {
        const val FAVORITES_COLLECTION = "favorites"
//        const val ORDERS_COLLECTION = "orders"
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
}
