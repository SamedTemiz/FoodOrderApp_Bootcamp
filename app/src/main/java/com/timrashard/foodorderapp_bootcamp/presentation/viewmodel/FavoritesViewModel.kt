package com.timrashard.foodorderapp_bootcamp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timrashard.foodorderapp_bootcamp.data.model.Yemekler
import com.timrashard.foodorderapp_bootcamp.data.repository.FirestoreRepository
import com.timrashard.foodorderapp_bootcamp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val repository: FirestoreRepository
) : ViewModel() {

    private val _favorites = MutableStateFlow<Resource<List<Yemekler>>>(Resource.Loading())
    val favorites: StateFlow<Resource<List<Yemekler>>> = _favorites

    fun getFavorites(userId: String) {
        viewModelScope.launch {
            repository.getFavorites(userId).collect { resource ->
                _favorites.value = resource
            }
        }
    }

    fun deleteFavorite(userId: String, yemekId: Int) {
        viewModelScope.launch {
            val result = repository.deleteFromFavorites(userId, yemekId)
            if (result is Resource.Success) {
                // TODO bildirim falan filan
            } else if (result is Resource.Error) {
                // TODO bildirim falan filan
            }
        }
    }
}
