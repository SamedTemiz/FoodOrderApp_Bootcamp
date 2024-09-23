package com.timrashard.foodorderapp_bootcamp.presentation.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timrashard.foodorderapp_bootcamp.data.repository.DataStoreRepository
import com.timrashard.foodorderapp_bootcamp.data.repository.DataStoreRepository.PreferencesKey
import com.timrashard.foodorderapp_bootcamp.presentation.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardViewModel @Inject constructor(private val repository: DataStoreRepository) : ViewModel() {

    private val _startDestination: MutableState<String> = mutableStateOf(Screen.Welcome.route)
    val startDestination: State<String> = _startDestination

    private val _isLoading = mutableStateOf(true)
    val isLoading: State<Boolean> = _isLoading

    init {
        viewModelScope.launch {
            repository.readBoolean(PreferencesKey.onBoardingKey).collect { value ->
                value?.let {
                    if (value) {
                        _startDestination.value = Screen.Welcome.Login.route
                    } else {
                        _startDestination.value = Screen.Welcome.OnBoard.route
                    }
                    _isLoading.value = false
                }
            }
        }
    }

    fun saveOnBoardingState(completed: Boolean){
        viewModelScope.launch (Dispatchers.IO){
            repository.saveBoolean(key = PreferencesKey.onBoardingKey, value = completed)
        }
    }
}