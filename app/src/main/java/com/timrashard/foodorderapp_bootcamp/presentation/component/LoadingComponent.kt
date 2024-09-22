package com.timrashard.foodorderapp_bootcamp.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.dotlottie.dlplayer.Mode
import com.lottiefiles.dotlottie.core.compose.ui.DotLottieAnimation
import com.lottiefiles.dotlottie.core.util.DotLottieSource

@Composable
fun LoadingComponent(){
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ){
        DotLottieAnimation(
            source = DotLottieSource.Asset("loading.lottie"),
            autoplay = true,
            loop = true,
            useFrameInterpolation = false,
            playMode = Mode.FORWARD,
            modifier = Modifier
                .fillMaxSize()
        )
    }
}