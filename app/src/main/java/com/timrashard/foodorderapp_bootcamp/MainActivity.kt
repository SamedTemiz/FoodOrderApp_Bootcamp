package com.timrashard.foodorderapp_bootcamp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.gson.Gson
import com.timrashard.foodorderapp_bootcamp.data.model.Yemekler
import com.timrashard.foodorderapp_bootcamp.presentation.navigation.Screen
import com.timrashard.foodorderapp_bootcamp.presentation.screen.CartScreen
import com.timrashard.foodorderapp_bootcamp.presentation.screen.DashboardScreen
import com.timrashard.foodorderapp_bootcamp.presentation.screen.DetailsScreen
import com.timrashard.foodorderapp_bootcamp.presentation.screen.SuccessScreen
import com.timrashard.foodorderapp_bootcamp.presentation.viewmodel.SharedViewModel
import com.timrashard.foodorderapp_bootcamp.ui.theme.FoodOrderApp_BootcampTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FoodOrderApp()
        }
    }
}

@Composable
fun FoodOrderApp() {
    val navController = rememberNavController()

    val sharedViewModel: SharedViewModel = hiltViewModel()

    FoodOrderApp_BootcampTheme {
        Surface(
            color = Color.White,
            modifier = Modifier.fillMaxSize()
        ) {
            NavHost(navController = navController, startDestination = "main") {
                navigation(
                    route = "main",
                    startDestination = Screen.Dashboard.route,
                ) {
                    composable(route = Screen.Dashboard.route) {
                        DashboardScreen(navController = navController)
                    }
                }

                composable(route = Screen.Details.route + "/{yemekler}",
                    arguments = listOf(
                        navArgument("yemekler") { type = NavType.StringType }
                    )
                ) {
                    val json = it.arguments?.getString("yemekler")
                    val foodObject = Gson().fromJson(json, Yemekler::class.java)

                    DetailsScreen(
                        navController = navController,
                        viewModel = sharedViewModel,
                        food = foodObject
                    )
                }

                composable(route = Screen.Cart.route) {
                    CartScreen(
                        navController = navController,
                        viewModel = sharedViewModel
                    )
                }

                composable(route = Screen.Success.route) {
                    SuccessScreen()
                }
            }
        }
    }
}