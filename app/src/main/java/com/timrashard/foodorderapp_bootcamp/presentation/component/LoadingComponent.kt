package com.timrashard.foodorderapp_bootcamp.presentation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.dotlottie.dlplayer.Mode
import com.lottiefiles.dotlottie.core.compose.ui.DotLottieAnimation
import com.lottiefiles.dotlottie.core.util.DotLottieSource

@Composable
fun LoadingComponent(
    modifier: Modifier
){
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ){
        DotLottieAnimation(
            source = DotLottieSource.Asset("loading.lottie"),
            autoplay = true,
            loop = true,
            useFrameInterpolation = false,
            playMode = Mode.FORWARD,
            modifier = Modifier.fillMaxSize()
        )
    }
}