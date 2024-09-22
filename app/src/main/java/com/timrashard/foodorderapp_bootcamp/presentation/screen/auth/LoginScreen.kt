package com.timrashard.foodorderapp_bootcamp.presentation.screen.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.timrashard.foodorderapp_bootcamp.presentation.navigation.Screen

@Composable
fun LoginScreen(navController: NavController){
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()) {

        Button(onClick = { /*TODO*/ }) {
            navController.navigate(Screen.Main.route){
                popUpTo(navController.graph.startDestinationId) { inclusive = true }
            }
        }
    }
}