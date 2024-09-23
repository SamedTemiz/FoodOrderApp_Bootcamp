package com.timrashard.foodorderapp_bootcamp.presentation.screen.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.dotlottie.dlplayer.Mode
import com.lottiefiles.dotlottie.core.compose.runtime.DotLottieController
import com.lottiefiles.dotlottie.core.compose.ui.DotLottieAnimation
import com.lottiefiles.dotlottie.core.util.DotLottieSource
import com.timrashard.foodorderapp_bootcamp.R
import com.timrashard.foodorderapp_bootcamp.presentation.component.DrawerContent
import com.timrashard.foodorderapp_bootcamp.presentation.component.ProfileMenuComponent
import com.timrashard.foodorderapp_bootcamp.presentation.navigation.Screen
import com.timrashard.foodorderapp_bootcamp.presentation.screen.DropdownMenuComponent
import com.timrashard.foodorderapp_bootcamp.presentation.viewmodel.AuthState
import com.timrashard.foodorderapp_bootcamp.presentation.viewmodel.AuthViewModel
import com.timrashard.foodorderapp_bootcamp.presentation.viewmodel.SharedViewModel
import com.timrashard.foodorderapp_bootcamp.ui.theme.Jacques
import com.timrashard.foodorderapp_bootcamp.ui.theme.SoftGray
import com.timrashard.foodorderapp_bootcamp.ui.theme.SoftPink
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun DashboardScreen(
    navController: NavController,
    authViewModel: AuthViewModel,
    sharedViewModel: SharedViewModel,
) {
    val authState = authViewModel.authState.collectAsState()
    val user by authViewModel.user.collectAsState()

    val cartItemCount by sharedViewModel.itemsCount.collectAsState()

    val dashBoardNavController = rememberNavController()
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(DrawerValue.Closed)

    LaunchedEffect(authState.value) {
        when (authState.value) {
            is AuthState.LoggedOut -> {
                navController.navigate(Screen.Welcome.route) {
                    popUpTo(Screen.Main.route) { inclusive = true }
                }
            }

            else -> Unit
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent()
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBarComponent(
                    scope = scope,
                    drawerState = drawerState,
                    authViewModel = authViewModel
                )
            },
            bottomBar = {
                BottomAppBarComponent(navController = dashBoardNavController)
            },
            floatingActionButton = {
                BadgedBox(
                    badge = {
                        if (cartItemCount > 0) {
                            Badge {
                                val badgeNumber = cartItemCount.toString()
                                Text(
                                    text = badgeNumber,
                                    fontSize = 14.sp,
                                    modifier = Modifier.semantics {
                                        contentDescription =
                                            "$badgeNumber items in the cart"
                                    })
                            }
                        }
                    }
                ) {
                    val bgDotLottieController = remember { DotLottieController() }
                    val cartDotLottieController = remember { DotLottieController() }

                    if (cartItemCount > 0) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(72.dp),
                        ) {
                            DotLottieAnimation(
                                source = DotLottieSource.Asset("circle_pulse.lottie"),
                                autoplay = true,
                                loop = false,
                                controller = bgDotLottieController,
                                useFrameInterpolation = false,
                                playMode = Mode.FORWARD,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .scale(2f)
                                    .zIndex(0f)
                            )

                            FloatingActionButton(
                                onClick = {
                                    navController.navigate(Screen.Cart.route)
                                },
                                shape = CircleShape,
                                containerColor = SoftPink,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .zIndex(1f)
                            ) {
                                DotLottieAnimation(
                                    source = DotLottieSource.Asset("cart.lottie"),
                                    autoplay = true,
                                    loop = true,
                                    controller = cartDotLottieController,
                                    useFrameInterpolation = false,
                                    playMode = Mode.FORWARD,
                                    modifier = Modifier.size(48.dp)
                                )
                            }
                        }
                    }
                }
            }
        ) { paddingValues ->
            Surface(
                color = Color.White,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                NavHost(
                    navController = dashBoardNavController,
                    startDestination = Screen.Home.route
                ) {
                    composable(route = Screen.Home.route) {
                        HomeScreen(mainNavController = navController, userId = user?.uid)
                    }

                    composable(route = Screen.Favorites.route) {
                        FavoritesScreen(userId = user?.uid, sharedViewModel = sharedViewModel)
                    }

                    composable(route = Screen.Orders.route) {
                        OrdersScreen()
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarComponent(
    scope: CoroutineScope,
    drawerState: DrawerState,
    authViewModel: AuthViewModel,
) {
    val user = authViewModel.user.collectAsState()

    TopAppBar(
        title = {
            Text(
                text = buildAnnotatedString {
                    append("Zest")
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.SemiBold,
                        )
                    ) {
                        append("Up")
                    }
                },
                fontSize = 24.sp,
                fontFamily = Jacques,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        },
        navigationIcon = {
            ElevatedCard(
                shape = CircleShape,
                colors = CardDefaults.cardColors(
                    containerColor = Color.White,
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 4.dp
                ),
                modifier = Modifier.size(48.dp)
            ) {
                IconButton(onClick = {
                    scope.launch {
                        drawerState.open()
                    }
                }) {
                    Icon(Icons.Default.Menu, contentDescription = "Menu")
                }
            }
        },
        actions = {
            ProfileMenuComponent(
                menuIcon = {
                    ElevatedCard(
                        shape = CircleShape,
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White,
                        ),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 4.dp
                        ),
                        onClick = { it.invoke() },
                        modifier = Modifier.size(48.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.bear),
                            contentDescription = "Profile",
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                },
                user = user.value?.email ?: "User",
                onDropItemClick = {
                    authViewModel.logoutUser()
                }
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White
        ),
        windowInsets = WindowInsets(left = 16.dp, top = 16.dp, right = 16.dp, bottom = 16.dp)
    )
}

@Composable
fun BottomAppBarComponent(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val items = listOf(
        Screen.Home,
        Screen.Favorites,
        Screen.Orders,
    )

    NavigationBar(
        Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        containerColor = Color.White
    ) {
        items.forEach { screen ->
            NavigationBarItem(
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(id = screen.icon),
                        contentDescription = null,
                        tint = if (currentRoute == screen.route) Color.Black else SoftGray
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}