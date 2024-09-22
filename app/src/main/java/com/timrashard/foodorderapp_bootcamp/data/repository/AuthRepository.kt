package com.timrashard.foodorderapp_bootcamp.data.repository

import com.timrashard.foodorderapp_bootcamp.data.datasource.AuthDataSource

class AuthRepository(private val authDataSource: AuthDataSource) {

    fun loginUser(email: String, password: String) = authDataSource.loginUser(email, password)

    fun registerUser(email: String, password: String) = authDataSource.registerUser(email, password)
}