package com.timrashard.foodorderapp_bootcamp.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import com.timrashard.foodorderapp_bootcamp.R
import com.timrashard.foodorderapp_bootcamp.common.Constants
import com.timrashard.foodorderapp_bootcamp.data.model.Yemekler
import com.timrashard.foodorderapp_bootcamp.data.model.toSepetYemekler
import com.timrashard.foodorderapp_bootcamp.presentation.component.MainButtonComponent
import com.timrashard.foodorderapp_bootcamp.presentation.component.fadingEdge
import com.timrashard.foodorderapp_bootcamp.presentation.viewmodel.SharedViewModel
import com.timrashard.foodorderapp_bootcamp.ui.theme.SmokeWhite
import com.timrashard.foodorderapp_bootcamp.ui.theme.StarYellow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    navController: NavController,
    viewModel: SharedViewModel,
    food: Yemekler
) {
    val imageUrl = Constants.IMAGE_URL + food.yemek_resim_adi
    val itemCount = remember { mutableIntStateOf(1) }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Details",
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.fillMaxWidth()
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            navController.popBackStack()
                        }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                                contentDescription = ""
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_favorite),
                                contentDescription = ""
                            )
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
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 20.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 80.dp)
                        .fadingEdge(
                            Brush.verticalGradient(0.9f to Color.Black, 1f to Color.Transparent)
                        )
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                    ) {
                        SubcomposeAsyncImage(
                            model = imageUrl,
                            contentDescription = "Food Image",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier.fillMaxSize(),
                            loading = {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(24.dp)
                                )
                            },
                            error = {
                                Text(text = "Error loading image")
                            }
                        )
                    }

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        ItemInfoCardComponent(
                            icon = R.drawable.ic_star,
                            text = "4.8"
                        )

                        ItemInfoCardComponent(
                            icon = R.drawable.ic_discount,
                            text = "%10"
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = food.yemek_adi,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            maxLines = 2,
                            lineHeight = 40.sp,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier
                                .fillMaxWidth(0.6f)
                        )

                        ItemCountComponent(
                            itemCount = itemCount.intValue,
                            onItemCountChange = { newCount ->
                                itemCount.intValue = newCount
                            },
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )
                    }

                    ItemDescriptionComponent(
                        modifier = Modifier
                            .weight(1f)
                            .padding(bottom = 8.dp)
                            .verticalScroll(rememberScrollState()),
                        text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc laoreet erat mauris, ac venenatis tortor fringilla eget. Morbi a nulla laoreet, cursus orci et, vehicula."
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth(0.4f)
                    ) {
                        Text(text = "₺", fontSize = 24.sp)

                        Spacer(modifier = Modifier.width(2.dp))

                        Text(
                            text = food.yemek_fiyat.toString(),
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                        )
                    }

                    MainButtonComponent(
                        text = "Add to cart",
                        modifier = Modifier.weight(1f)
                    ){
                        viewModel.addFoodToCart(food.toSepetYemekler(itemCount.intValue), isDetails = true)
                        navController.popBackStack()
                    }
                }
            }
        }
    }
}

@Composable
fun ItemInfoCardComponent(
    icon: Int? = null,
    text: String
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .background(color = SmokeWhite, shape = RoundedCornerShape(15.dp))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
        ) {
            icon?.let {
                Icon(
                    painter = painterResource(id = it),
                    contentDescription = "Rate",
                    tint = StarYellow,
                    modifier = Modifier.size(18.dp)
                )

                Spacer(modifier = Modifier.width(6.dp))
            }

            Text(
                text = text,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
            )
        }
    }
}

@Composable
fun ItemCountComponent(
    itemCount: Int,
    onItemCountChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .background(color = SmokeWhite, shape = RoundedCornerShape(15.dp))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 8.dp)
        ) {
            IconButton(onClick = {
                if (itemCount > 1) {
                    onItemCountChange(itemCount - 1)
                }
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_minus),
                    contentDescription = ""
                )
            }

            Text(
                text = itemCount.toString(),
                fontSize = 20.sp,
                modifier = Modifier.padding(horizontal = 8.dp)
            )

            IconButton(onClick = {
                onItemCountChange(itemCount + 1)
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_plus),
                    contentDescription = "",
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

@Composable
fun ItemDescriptionComponent(
    modifier: Modifier,
    text: String
) {
    Column(modifier = modifier) {
        Text(
            text = "Description",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = text,
            fontSize = 16.sp,
            color = Color.DarkGray
        )
    }
}