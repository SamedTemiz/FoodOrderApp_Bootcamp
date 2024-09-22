package com.timrashard.foodorderapp_bootcamp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthResult
import com.timrashard.foodorderapp_bootcamp.data.repository.AuthRepository
import com.timrashard.foodorderapp_bootcamp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel(){

    private val _loginState = MutableStateFlow<Resource<AuthResult>>(Resource.Loading())
    val loginState: StateFlow<Resource<AuthResult>> = _loginState

    private val _registerState = MutableStateFlow<Resource<AuthResult>>(Resource.Loading())
    val registerState: StateFlow<Resource<AuthResult>> = _registerState

    fun loginUser(email: String, password: String){
        viewModelScope.launch {
            repository.loginUser(email, password).collect{ result ->
                _loginState.value = result
            }
        }
    }

    fun registerUser(email: String, password: String){
        viewModelScope.launch {
            repository.registerUser(email, password).collect{ result ->
                _loginState.value = result
            }
        }
    }
}