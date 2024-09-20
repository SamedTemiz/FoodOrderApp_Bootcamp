package com.timrashard.foodorderapp_bootcamp.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.dotlottie.dlplayer.Mode
import com.google.gson.Gson
import com.lottiefiles.dotlottie.core.compose.ui.DotLottieAnimation
import com.lottiefiles.dotlottie.core.util.DotLottieSource
import com.timrashard.foodorderapp_bootcamp.domain.model.ChipItem
import com.timrashard.foodorderapp_bootcamp.presentation.component.HorizontalChipMenuComponent
import com.timrashard.foodorderapp_bootcamp.presentation.component.ItemCardComponent
import com.timrashard.foodorderapp_bootcamp.presentation.component.SearchBarComponent
import com.timrashard.foodorderapp_bootcamp.presentation.component.fadingEdge
import com.timrashard.foodorderapp_bootcamp.presentation.navigation.Screen
import com.timrashard.foodorderapp_bootcamp.presentation.viewmodel.HomeViewModel
import com.timrashard.foodorderapp_bootcamp.ui.theme.SoftGray
import com.timrashard.foodorderapp_bootcamp.utils.Resource
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    mainNavController: NavController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val foodListState by homeViewModel.foodList.collectAsState()

    val searchText = remember { mutableStateOf("") }
    val chipList = remember { mutableStateOf(listOf<ChipItem>()) }
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        chipList.value = homeViewModel.getChipList()
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.padding(start = 20.dp)
        ) {
            Text("Find and order", fontSize = 24.sp)

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text("your favorite food", fontSize = 24.sp, fontWeight = FontWeight.Bold)

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.size(36.dp)
                ) {
                    DotLottieAnimation(
                        source = DotLottieSource.Asset("burger.lottie"),
                        autoplay = true,
                        loop = true,
                        speed = 0.5f,
                        useFrameInterpolation = false,
                        playMode = Mode.FORWARD,
                        modifier = Modifier
                            .fillMaxSize()
                            .scale(1.5f)
                    )
                }
            }
        }

        SearchBarComponent(
            value = searchText.value,
            onValueChange = {
                searchText.value = it
            },
            placeholder = "Find your food",
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        )

        HorizontalChipMenuComponent(
            chipList = chipList.value,
            onSelected = { index ->
                homeViewModel.filterWithChip(index)
                scope.launch {
                    listState.animateScrollToItem(index = index)
                }
            }
        )

        when (foodListState) {
            is Resource.Loading -> {
                CircularProgressIndicator()
            }

            is Resource.Success -> {
                val foodList = foodListState.data?.yemekler ?: emptyList()

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(bottom = 102.dp),
                    state = listState,
                    modifier = Modifier
                        .fillMaxSize()
                        .fadingEdge(
                            brush = Brush.verticalGradient(
                                0.9f to SoftGray,
                                1f to Color.Transparent
                            )
                        )
                ) {
                    if (searchText.value.isEmpty()) {
                        item {
                            Text(
                                text = "All",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(start = 20.dp)
                            )

                            Spacer(Modifier.height(8.dp))

                            LazyRow(
                                contentPadding = PaddingValues(start = 20.dp, end = 20.dp),
                                horizontalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                items(foodList) { food ->
                                    ItemCardComponent(
                                        food = food,
                                        onFavoriteClick = {},
                                        onItemClick = {
                                            val foodJson = Gson().toJson(food)
                                            mainNavController.navigate(Screen.Details.route + "/$foodJson")
                                        }
                                    )
                                }
                            }
                        }
                    } else {
//                        item {
//                            Text(
//                                text = "Search result",
//                                fontSize = 20.sp,
//                                fontWeight = FontWeight.SemiBold,
//                                modifier = Modifier.padding(start = 20.dp)
//                            )
//
//                            Spacer(Modifier.height(16.dp))
//
//                            LazyRow(
//                                contentPadding = PaddingValues(start = 20.dp, end = 20.dp),
//                                horizontalArrangement = Arrangement.spacedBy(16.dp)
//                            ) {
//                                items(6) {
//                                    ItemCardComponent(
//                                        onFavoriteClick = {},
//                                        onItemClick = {
////                                    val foodJson = Gson().toJson(contact)
//                                            mainNavController.navigate(Screen.Details.route)
//                                        }
//                                    )
//                                }
//                            }
//                        }
                    }
                }
            }

            is Resource.Error -> {

            }
        }
    }
}