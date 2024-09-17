package com.timrashard.foodorderapp_bootcamp.presentation.navigation

import android.graphics.drawable.Icon
import com.timrashard.foodorderapp_bootcamp.R

sealed class Screen(val route: String, val label: String, val icon: Int = R.drawable.ic_default_page) {

    // Bottom Bar Screens
    object Home : Screen(route = "home", label = "Home", icon = R.drawable.ic_home)
    object Cart : Screen(route = "cart", label = "Cart", icon = R.drawable.ic_cart)
    object Favorites : Screen(route = "favorite", label = "Favorites", icon = R.drawable.ic_favorite)
    object Orders : Screen(route = "orders", label = "Orders", icon = R.drawable.ic_orders)

    // Others

    object Details : Screen(route = "details", label = "Detail")
}