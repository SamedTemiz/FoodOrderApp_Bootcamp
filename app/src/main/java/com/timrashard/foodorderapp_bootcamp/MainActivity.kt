package com.timrashard.foodorderapp_bootcamp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
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
import com.timrashard.foodorderapp_bootcamp.presentation.screen.DetailsScreen
import com.timrashard.foodorderapp_bootcamp.presentation.screen.SuccessScreen
import com.timrashard.foodorderapp_bootcamp.presentation.screen.auth.AuthScreen
import com.timrashard.foodorderapp_bootcamp.presentation.screen.main.DashboardScreen
import com.timrashard.foodorderapp_bootcamp.presentation.screen.onboarding.OnboardingScreen
import com.timrashard.foodorderapp_bootcamp.presentation.screen.onboarding.SplashScreen
import com.timrashard.foodorderapp_bootcamp.presentation.viewmodel.AuthViewModel
import com.timrashard.foodorderapp_bootcamp.presentation.viewmodel.OnBoardViewModel
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

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun FoodOrderApp() {
    val navController = rememberNavController()

    val onBoardViewModel: OnBoardViewModel = hiltViewModel()
    val authViewModel: AuthViewModel = hiltViewModel()
    val sharedViewModel: SharedViewModel = hiltViewModel()

    FoodOrderApp_BootcampTheme {
        Surface(
            color = Color.White,
            modifier = Modifier.fillMaxSize()
        ) {
            NavHost(navController = navController, startDestination = Screen.Welcome.route) {
                // Welcome
                navigation(
                    route = Screen.Welcome.route,
                    startDestination = Screen.Welcome.Splash.route,
                ) {
                    composable(route = Screen.Welcome.Splash.route) {
                        SplashScreen(
                            navController = navController,
                            viewModel = onBoardViewModel
                        )
                    }

                    composable(route = Screen.Welcome.OnBoard.route) {
                        OnboardingScreen(navController = navController)
                    }

                    composable(
                        route = Screen.Welcome.Auth.route,
                        exitTransition = {
                            slideOutOfContainer(
                                AnimatedContentTransitionScope.SlideDirection.Start,
                                tween(700)
                            )
                        }
                    ) {
                        AuthScreen(navController = navController, authViewModel = authViewModel)
                    }
                }

                // Main -> Dashboard Screen
                navigation(
                    route = Screen.Main.route,
                    startDestination = Screen.Dashboard.route,
                ) {
                    composable(
                        route = Screen.Dashboard.route,
                        enterTransition = {
                            slideIntoContainer(
                                AnimatedContentTransitionScope.SlideDirection.End,
                                tween(700)
                            )
                        }) {
                        DashboardScreen(
                            navController = navController,
                            authViewModel = authViewModel,
                            sharedViewModel = sharedViewModel,
                        )
                    }
                }

                // Details Screen
                composable(route = Screen.Details.route + "/{yemekler}",
                    arguments = listOf(
                        navArgument("yemekler") { type = NavType.StringType }
                    ),
                    enterTransition = {
                        fadeIn(tween(700))
                    },
                    exitTransition = {
                        slideOutOfContainer(
                            AnimatedContentTransitionScope.SlideDirection.End,
                            tween(700)
                        )
                    }
                ) {
                    val json = it.arguments?.getString("yemekler")
                    val foodObject = Gson().fromJson(json, Yemekler::class.java)

                    DetailsScreen(
                        navController = navController,
                        viewModel = sharedViewModel,
                        food = foodObject
                    )
                }

                // Cart Screen
                composable(
                    route = Screen.Cart.route,
                    enterTransition = {
                        fadeIn(tween(700))
                    }) {
                    CartScreen(
                        navController = navController,
                        viewModel = sharedViewModel
                    )
                }

                // Success Screen
                composable(route = Screen.Success.route,
                    enterTransition = {
                        fadeIn(tween(700))
                    }) {
                    SuccessScreen(navController = navController)
                }
            }
        }
    }
}