package com.timrashard.foodorderapp_bootcamp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timrashard.foodorderapp_bootcamp.data.repository.DataStoreRepository
import com.timrashard.foodorderapp_bootcamp.data.repository.DataStoreRepository.PreferencesKey
import com.timrashard.foodorderapp_bootcamp.presentation.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardViewModel @Inject constructor(
    private val repository: DataStoreRepository
) : ViewModel() {

    private val _startDestination: MutableStateFlow<String> = MutableStateFlow(Screen.Welcome.route)
    val startDestination: StateFlow<String> = _startDestination

    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        viewModelScope.launch {
            repository.readBoolean(PreferencesKey.onBoardingKey).collect { value ->
                val onBoardingState = value ?: false

                if (onBoardingState) {
                    _startDestination.value = Screen.Welcome.Auth.route
                } else {
                    _startDestination.value = Screen.Welcome.OnBoard.route
                }

                _isLoading.value = false
            }
        }
    }

    fun saveOnBoardingState(completed: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.saveBoolean(key = PreferencesKey.onBoardingKey, value = completed)
        }
    }
}