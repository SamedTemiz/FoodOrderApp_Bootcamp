package com.timrashard.foodorderapp_bootcamp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timrashard.foodorderapp_bootcamp.data.model.Yemekler
import com.timrashard.foodorderapp_bootcamp.data.model.YemeklerResponse
import com.timrashard.foodorderapp_bootcamp.domain.usecase.GetAllFoodsUseCase
import com.timrashard.foodorderapp_bootcamp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getAllFoodsUseCase: GetAllFoodsUseCase
) : ViewModel() {

    private val _foods = MutableStateFlow<Resource<YemeklerResponse>>(Resource.Loading())
    val foods: StateFlow<Resource<YemeklerResponse>> = _foods

    init {
        fetchAllFoods()
    }

    fun fetchAllFoods() {
        viewModelScope.launch {
            getAllFoodsUseCase().collect { resource ->
                _foods.value = resource
            }
        }
    }
}