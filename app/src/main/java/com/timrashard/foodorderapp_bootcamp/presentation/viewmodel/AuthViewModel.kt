package com.timrashard.foodorderapp_bootcamp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.timrashard.foodorderapp_bootcamp.data.repository.AuthRepository
import com.timrashard.foodorderapp_bootcamp.data.repository.DataStoreRepository
import com.timrashard.foodorderapp_bootcamp.utils.Auth
import com.timrashard.foodorderapp_bootcamp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    private var auth: FirebaseUser? = null

    private val _authState = MutableStateFlow<AuthState>(AuthState.Loading)
    val authState: StateFlow<AuthState> = _authState

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> get() = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> get() = _password

    private val _rememberMe = MutableStateFlow(false)
    val rememberMe: StateFlow<Boolean> = _rememberMe

    init {
        auth = repository.getCurrentUser()

        checkAuthStatus()
        checkRememberMe()
    }

    private fun checkAuthStatus() {
        _authState.value = AuthState.Loading

        if (auth == null) {
            _authState.value = AuthState.LoggedOut
        } else {
            _authState.value = AuthState.LoggedIn
        }
    }

    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            repository.loginUser(email, password).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _authState.value = AuthState.Loading
                    }

                    is Resource.Success -> {
                        _authState.value = AuthState.LoggedIn
                        if (_rememberMe.value) {
                            saveAuthData()
                        }
                    }

                    is Resource.Error -> {
                        _authState.value = AuthState.Error(result.message ?: "An error occurred")
                    }
                }
            }
        }
    }

    fun registerUser(email: String, password: String) {
        viewModelScope.launch {
            repository.registerUser(email, password).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _authState.value = AuthState.Loading
                    }

                    is Resource.Success -> {
                        _authState.value = AuthState.LoggedIn
                        if (_rememberMe.value) {
                            saveAuthData()
                        }
                    }

                    is Resource.Error -> {
                        _authState.value = AuthState.Error(result.message ?: "An error occurred")
                    }
                }
            }
        }
    }

    fun logoutUser() {
        repository.logoutUser()
        _authState.value = AuthState.LoggedOut
    }

    fun checkRememberMe() {
        viewModelScope.launch {
            _rememberMe.value =
                dataStoreRepository.readBoolean(key = DataStoreRepository.PreferencesKey.rememberMeKey)
                    .first() ?: false

            if (_rememberMe.value) {
                getAuthData()
            }else{
                _email.value = ""
                _password.value = ""
            }
        }
    }

    private fun saveAuthData() {
        viewModelScope.launch {
            dataStoreRepository.saveString(
                key = DataStoreRepository.PreferencesKey.emailKey,
                value = _email.value
            )
            dataStoreRepository.saveString(
                key = DataStoreRepository.PreferencesKey.passwordKey,
                value = _password.value
            )
        }
    }

    fun getAuthData() {
        viewModelScope.launch {
            _email.value =
                dataStoreRepository.readString(key = DataStoreRepository.PreferencesKey.emailKey)
                    .first() ?: ""
            _password.value =
                dataStoreRepository.readString(key = DataStoreRepository.PreferencesKey.passwordKey)
                    .first() ?: ""
            _rememberMe.value
        }
    }

    fun updateEmail(newEmail: String) {
        _email.value = newEmail
    }

    fun updatePassword(newPassword: String) {
        _password.value = newPassword
    }

    fun updateRememberMe(value: Boolean) {
        _rememberMe.value = value

        if(!value){
            _email.value = ""
            _password.value = ""
        }

        viewModelScope.launch {
            dataStoreRepository.saveBoolean(
                key = DataStoreRepository.PreferencesKey.rememberMeKey,
                value = value
            )
        }
    }
}

sealed class AuthState() {
    object LoggedIn : AuthState()
    object LoggedOut : AuthState()
    object Loading : AuthState()
    data class Error(val message: String) : AuthState()
}