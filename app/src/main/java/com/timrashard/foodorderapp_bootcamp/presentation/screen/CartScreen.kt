package com.timrashard.foodorderapp_bootcamp.presentation.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.timrashard.foodorderapp_bootcamp.R
import com.timrashard.foodorderapp_bootcamp.presentation.component.DashedDividerComponent
import com.timrashard.foodorderapp_bootcamp.presentation.component.MainButtonComponent
import com.timrashard.foodorderapp_bootcamp.ui.theme.SmokeWhite
import com.timrashard.foodorderapp_bootcamp.ui.theme.SoftGray
import com.timrashard.foodorderapp_bootcamp.ui.theme.SoftOrange
import com.timrashard.foodorderapp_bootcamp.ui.theme.SoftPink

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    navController: NavController
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Cart",
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
                            painter = painterResource(id = R.drawable.ic_more),
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
        ) {
            LazyColumn(
                contentPadding = PaddingValues(
                    start = 20.dp,
                    end = 20.dp,
                    top = 4.dp,
                    bottom = 200.dp
                ),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(6) {
                    CartItem()
                }
            }

            CartDetails(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .background(Color.White)
            )
        }
    }
}

@Composable
fun CartDetails(
    modifier: Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    val rotationState by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f
    )

    Column(
        verticalArrangement = Arrangement.Bottom,
        modifier = modifier .background(
            brush = Brush.verticalGradient(
                colors = listOf(Color.White, SoftPink)
            )
        )
    ) {
        Column(
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            AnimatedVisibility(
                visible = expanded,
                enter = expandVertically(animationSpec = tween(durationMillis = 300)),
                exit = shrinkVertically(animationSpec = tween(durationMillis = 300)),
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                ) {
                    HorizontalDivider(
                        thickness = 2.dp,
                        color = Color.Black,
                        modifier = Modifier.fillMaxWidth(0.1f)
                    )
                }

                ElevatedCard(
                    shape = RectangleShape,
                    onClick = {
                        expanded = !expanded
                    },
                    colors = CardDefaults.cardColors(
                        containerColor = Color.Transparent
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .animateContentSize(
                            animationSpec = tween(
                                durationMillis = 300,
                                easing = LinearOutSlowInEasing
                            )
                        )
                ) {
                    Spacer(modifier = Modifier.height(32.dp))

                    Column(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.padding(horizontal = 20.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = "Sub total", color = Color.DarkGray, fontSize = 18.sp)

                            Text(text = "₺24,90", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = "Shipping", color = Color.DarkGray, fontSize = 18.sp)

                            Text(text = "₺0,00", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = "Discount", color = Color.DarkGray, fontSize = 18.sp)

                            Text(text = "₺0,00", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    DashedDividerComponent(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                            .height(1.dp),
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(horizontal = 20.dp)
                    .clickable {
                        expanded = !expanded
                    }
            ) {
                Text(text = "Total", color = Color.DarkGray, fontSize = 18.sp)

                Icon(
                    imageVector = Icons.Default.KeyboardArrowUp,
                    contentDescription = "Swipe",
                    modifier = Modifier.rotate(rotationState)
                )

                Text(
                    text = "₺27,90",
                    color = SoftOrange,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(bottom = 16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(0.4f)
            ) {
                Text(text = "₺", fontSize = 24.sp)

                Spacer(modifier = Modifier.width(2.dp))

                Text(
                    text = "30",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                )
            }

            MainButtonComponent(
                text = "Pay now",
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun CartItem() {
    ElevatedCard(
        onClick = {},
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(125.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 8.dp, horizontal = 12.dp)
        ) {
            Checkbox(
                checked = true,
                onCheckedChange = {},
                colors = CheckboxDefaults.colors(
                    checkedColor = SoftOrange,
                )
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.weight(1f)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(100.dp)
                        .background(color = SoftPink, shape = RoundedCornerShape(15.dp))
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ayran),
                        contentDescription = "Item",
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Column(
                    verticalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)
                        .padding(start = 8.dp)
                ) {
                    Text(text = "İçecek", fontSize = 16.sp, color = SoftOrange)

                    Text(text = "Ayran", fontSize = 18.sp, fontWeight = FontWeight.Bold)

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row() {
                            Text(
                                text = "₺30",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.alignByBaseline()
                            )
                            Text(
                                text = "₺30",
                                fontSize = 14.sp,
                                color = SoftGray,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .alignByBaseline()
                                    .padding(start = 8.dp)
                            )
                        }

                        Spacer(modifier = Modifier.weight(1f))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            IconButton(
                                onClick = {
                                    // TODO
                                },
                                modifier = Modifier
                                    .background(color = SmokeWhite, shape = CircleShape)
                                    .size(24.dp)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_minus),
                                    contentDescription = "",
                                    modifier = Modifier.size(16.dp)
                                )
                            }

                            Text(
                                text = "1",
                                fontSize = 18.sp,
                                modifier = Modifier.padding(horizontal = 4.dp)
                            )

                            IconButton(
                                onClick = {
                                    // TODO
                                },
                                modifier = Modifier
                                    .background(color = Color.Black, shape = CircleShape)
                                    .size(24.dp)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_plus),
                                    contentDescription = "",
                                    tint = Color.White,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
