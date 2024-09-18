package com.timrashard.foodorderapp_bootcamp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.timrashard.foodorderapp_bootcamp.presentation.navigation.Screen
import com.timrashard.foodorderapp_bootcamp.presentation.screen.CartScreen
import com.timrashard.foodorderapp_bootcamp.presentation.screen.DashboardScreen
import com.timrashard.foodorderapp_bootcamp.presentation.screen.DetailsScreen
import com.timrashard.foodorderapp_bootcamp.presentation.screen.FavoritesScreen
import com.timrashard.foodorderapp_bootcamp.presentation.screen.HomeScreen
import com.timrashard.foodorderapp_bootcamp.presentation.screen.OrdersScreen
import com.timrashard.foodorderapp_bootcamp.ui.theme.FoodOrderApp_BootcampTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            FoodOrderApp_BootcampTheme {
                Surface(
                    color = Color.White,
                    modifier = Modifier.fillMaxSize()
                ) {
                    NavHost(navController = navController, startDestination = "dashboard" ){
                        navigation(
                            route = "dashboard",
                            startDestination = "main",
                        ){
                            composable(route = "main") {
                                DashboardScreen(navController = navController)
                            }
                        }

                        composable(route = Screen.Details.route) {
                            DetailsScreen(navController = navController)
                        }

                        composable(route = Screen.Cart.route) {
                            CartScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }
}