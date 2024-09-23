package com.timrashard.foodorderapp_bootcamp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timrashard.foodorderapp_bootcamp.data.repository.FirestoreRepository
import com.timrashard.foodorderapp_bootcamp.domain.model.Order
import com.timrashard.foodorderapp_bootcamp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(
    private val firestoreRepository: FirestoreRepository
) : ViewModel() {

    private val _orders = MutableStateFlow<Resource<List<Order>>>(Resource.Loading())
    val orders: StateFlow<Resource<List<Order>>> = _orders

    fun getOrders(userId: String) {
        viewModelScope.launch {
            firestoreRepository.getOrders(userId).collect { resource ->
                _orders.value = resource
            }
        }
    }

    suspend fun deleteOrder(userId: String, orderId: Int): Resource<Unit> {
        return firestoreRepository.deleteOrder(userId, orderId)
    }
}