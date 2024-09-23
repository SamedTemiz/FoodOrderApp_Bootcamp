package com.timrashard.foodorderapp_bootcamp.presentation.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.dotlottie.dlplayer.Mode
import com.lottiefiles.dotlottie.core.compose.ui.DotLottieAnimation
import com.lottiefiles.dotlottie.core.util.DotLottieSource
import com.timrashard.foodorderapp_bootcamp.presentation.component.MainButtonComponent
import com.timrashard.foodorderapp_bootcamp.presentation.navigation.Screen
import com.timrashard.foodorderapp_bootcamp.ui.theme.SoftOrange

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SuccessScreen(
    navController: NavController
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Success",
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {}) {
                    }
                },
                actions = {
                    IconButton(onClick = {}) {
                    }
                },
                windowInsets = WindowInsets(
                    left = 16.dp,
                    top = 16.dp,
                    right = 16.dp,
                    bottom = 16.dp
                )
            )
        }
    ) { paddingValues ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
            ) {
                DotLottieAnimation(
                    source = DotLottieSource.Asset("delivery.lottie"),
                    autoplay = true,
                    loop = true,
                    useFrameInterpolation = false,
                    playMode = Mode.FORWARD,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = 16.dp)
            ) {
                Text(
                    text = "Your order has been placed successfully",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                )

                MainButtonComponent(
                    text = "Back to shopping",
                    modifier = Modifier.padding(horizontal = 20.dp)
                ){
                    navController.navigate(Screen.Main.route){
                        popUpTo(Screen.Success.route){
                            inclusive = true
                        }
                    }
                }

//                Text(
//                    text = "Back to shopping",
//                    textDecoration = TextDecoration.Underline,
//                    color = SoftOrange,
//                    modifier = Modifier
//                        .clickable {
//                            navController.navigate(Screen.Home.route){
//                                popUpTo(Screen.Success.route){
//                                    inclusive = true
//                                }
//                            }
//                        }
//                )
            }
        }
    }
}