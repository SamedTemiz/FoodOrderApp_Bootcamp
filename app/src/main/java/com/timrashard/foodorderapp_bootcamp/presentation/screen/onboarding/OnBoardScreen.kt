package com.timrashard.foodorderapp_bootcamp.presentation.screen.onboarding

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.timrashard.foodorderapp_bootcamp.R
import com.timrashard.foodorderapp_bootcamp.presentation.navigation.Screen
import com.timrashard.foodorderapp_bootcamp.presentation.viewmodel.OnBoardViewModel
import com.timrashard.foodorderapp_bootcamp.ui.theme.SoftGray
import com.timrashard.foodorderapp_bootcamp.ui.theme.SoftOrange
import com.timrashard.foodorderapp_bootcamp.ui.theme.SoftPink
import kotlinx.coroutines.launch


@Composable
fun OnboardingScreen(
    navController: NavHostController,
    onBoardViewModel: OnBoardViewModel = hiltViewModel()
) {
    val images = listOf(
        R.drawable.order,
        R.drawable.delivering,
        R.drawable.delivered,
    )

    val pageTitle = listOf(
        "Make Your Food Selection!",
        "Your Order is Prepared and on the Way!",
        "Your Order is Delivered!"
    )

    val titles = listOf(
        "Choose Your Favorite Meal",
        "Fast and Safe Delivery",
        "Your Hot and Fresh Food is at Your Doorstep"
    )

    val descriptions = listOf(
        "Select from our delicious menus and easily create your order.",
        "Your order is carefully prepared and on its way. Our courier will deliver it to you as soon as possible.",
        "Your food has been delivered to your door. Now it's time to enjoy your meal!"
    )


    val pagerState = rememberPagerState(
        pageCount = { images.size }
    )

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.75f)
        ) { currentPage ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 20.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(15.dp))
                        .background(SoftPink)
                ) {
                    Image(
                        painter = painterResource(id = images[currentPage]),
                        contentDescription = "Image",
                        modifier = Modifier.fillMaxSize()

                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Column(
                    verticalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = pageTitle[currentPage],
                        textAlign = TextAlign.Center,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Text(
                        text = titles[currentPage],
                        fontSize = 18.sp,
                        textDecoration = TextDecoration.Underline,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Text(
                        text = descriptions[currentPage],
                        fontSize = 18.sp,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

        PageIndicator(
            pageCount = images.size,
            currentPage = pagerState.currentPage,
            modifier = Modifier
        )

        ButtonsSection(
            pagerState = pagerState,
            navController = navController,
            onBoardViewModel = onBoardViewModel,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 20.dp, vertical = 20.dp)
        )
    }
}

@Composable
fun ButtonsSection(
    pagerState: PagerState,
    navController: NavHostController,
    onBoardViewModel: OnBoardViewModel,
    modifier: Modifier
) {

    val scope = rememberCoroutineScope()

    Box(
        modifier = modifier
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {
            // Back button with animated visibility
            AnimatedVisibility(
                visible = pagerState.currentPage > 0,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Button(
                    onClick = {
                        scope.launch {
                            val prevPage = pagerState.currentPage - 1
                            if (prevPage >= 0) {
                                pagerState.scrollToPage(prevPage)
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black,
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = "Back",
                        fontSize = 18.sp,
                    )
                }
            }

            if (pagerState.currentPage == 0) {
                Spacer(modifier = Modifier.size(16.dp))
            }

            // Next button
            Button(
                onClick = {
                    scope.launch {
                        val nextPage = pagerState.currentPage + 1
                        pagerState.scrollToPage(nextPage)
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Next",
                    fontSize = 18.sp,
                )
            }
        }

        // Last page button
        if (pagerState.currentPage == 2) {
            Button(
                onClick = {
                    onBoardViewModel.saveOnBoardingState(completed = true)

                    navController.navigate(Screen.Welcome.Auth.route) {
                        popUpTo(Screen.Welcome.OnBoard.route) {
                            inclusive = true
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Let's Start",
                    fontSize = 18.sp,
                )
            }
        }
    }
}

@Composable
fun PageIndicator(pageCount: Int, currentPage: Int, modifier: Modifier) {

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
    ) {
        repeat(pageCount) {
            IndicatorSingleDot(isSelected = it == currentPage)
        }
    }
}

@Composable
fun IndicatorSingleDot(isSelected: Boolean) {

    val width = animateDpAsState(targetValue = if (isSelected) 35.dp else 15.dp, label = "")
    Box(
        modifier = Modifier
            .padding(2.dp)
            .height(15.dp)
            .width(width.value)
            .clip(CircleShape)
            .background(if (isSelected) SoftOrange else SoftGray)
    )
}