package com.timrashard.foodorderapp_bootcamp.presentation.navigation

import com.timrashard.foodorderapp_bootcamp.R

sealed class Screen(val route: String, val label: String, val icon: Int = R.drawable.ic_default_page) {

    // Auth & Welcome
    object Welcome : Screen(route = "welcome", label = "Welcome") {
        object Splash : Screen(route = "splash", label = "Splash")
        object OnBoard : Screen(route = "onboard", label = "Onboard")
        object Auth : Screen(route = "auth", label = "Auth")
    }

    // Bottom Bar Screens
    object Main : Screen(route = "main", label = "Main")

    object Dashboard : Screen(route = "dashboard", label = "Dashboard")
    object Home : Screen(route = "home", label = "Home", icon = R.drawable.ic_home)
    object Favorites : Screen(route = "favorite", label = "Favorites", icon = R.drawable.ic_favorite)
    object Orders : Screen(route = "orders", label = "Orders", icon = R.drawable.ic_orders)

    // Others
    object Details : Screen(route = "details", label = "Detail")
    object Cart : Screen(route = "cart", label = "Cart", icon = R.drawable.ic_cart)
    object Success : Screen(route = "success", label = "Success")
}