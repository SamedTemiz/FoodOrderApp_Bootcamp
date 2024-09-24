package com.timrashard.foodorderapp_bootcamp.presentation.screen.main

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.SubcomposeAsyncImage
import com.timrashard.foodorderapp_bootcamp.common.Constants
import com.timrashard.foodorderapp_bootcamp.domain.model.Order
import com.timrashard.foodorderapp_bootcamp.domain.model.SepetYemekModel
import com.timrashard.foodorderapp_bootcamp.presentation.component.DashedDividerComponent
import com.timrashard.foodorderapp_bootcamp.presentation.component.error.SearchErrorComponent
import com.timrashard.foodorderapp_bootcamp.presentation.viewmodel.OrdersViewModel
import com.timrashard.foodorderapp_bootcamp.utils.Resource

@Composable
fun OrdersScreen(
    userId: String?,
    ordersViewModel: OrdersViewModel = hiltViewModel()
) {
    val orders by ordersViewModel.orders.collectAsState()

    LaunchedEffect(Unit) {
        userId?.let { id ->
            ordersViewModel.getOrders(userId = id)
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            when(orders){
                is Resource.Loading -> {
                    item {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }

                is Resource.Success -> {
                    val ordersList = (orders as Resource.Success).data

                    if (ordersList.isNullOrEmpty()) {
                        item {
                            SearchErrorComponent(
                                text = "Your orders is empty",
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp)
                            )
                        }
                    }else{
                        items(ordersList) { item ->
                            OrderItem(order = item)
                        }
                    }
                }

                is Resource.Error -> {
                    item { Text("Hata: ${orders.message}") }
                }
            }
        }
    }
}

@Composable
fun OrderItem(
    order: Order
) {
    var expandedState by remember { mutableStateOf(false) }
    val rotationState by animateFloatAsState(
        targetValue = if (expandedState) 180f else 0f
    )

    ElevatedCard(
        onClick = {
            expandedState = !expandedState
        },
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .border(0.5.dp, Color.Gray, shape = CardDefaults.shape)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
            .heightIn(max = 300.dp),
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = order.orderId.toString(), fontWeight = FontWeight.Bold)
                Text(text = "Date: ${order.orderDate}", fontSize = 12.sp, color = Color.Gray)
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column{
                    Text(text = "Total Price:", fontSize = 12.sp, color = Color.Gray)

                    Text(text = "₺${order.orderTotalPrice}", fontWeight = FontWeight.Bold)
                }
                
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(text = order.orderState ?: "Order Received", textDecoration = TextDecoration.Underline)
                    
                    Spacer(modifier = Modifier.width(8.dp))
                    
                    IconButton(onClick = {
                        expandedState = !expandedState
                    }) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = "Swipe",
                            modifier = Modifier.rotate(rotationState)
                        )
                    }
                }
                

            }

            if (expandedState) {
                DashedDividerComponent(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp),
                    color = Color.LightGray
                )

                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Item Count: ${order.orderItems?.size}", fontSize = 12.sp, color = Color.Gray)
                        Text(text = "Shipping: ₺0,00", fontSize = 12.sp, color = Color.Gray)
                        Text(text = "Discount: ₺0,00", fontSize = 12.sp, color = Color.Gray)
                    }

                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        contentPadding = PaddingValues(top = 4.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(order.orderItems ?: emptyList()) { item ->
                            OrderFoodItem(foodItem = item)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun OrderFoodItem(
    foodItem: SepetYemekModel
) {
    val imageUrl = Constants.IMAGE_URL + foodItem.yemek_resim_adi
    val foodTotalPrice by remember { mutableStateOf(foodItem.yemek_siparis_adet!! * foodItem.yemek_fiyat!!) }

    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(5.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ){
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.size(48.dp)
                ) {
                    SubcomposeAsyncImage(
                        model = imageUrl,
                        contentDescription = "Food Image",
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

                Column {
                    Text(text = foodItem.yemek_adi ?: "Item", fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                    Text(text = "Count: ${foodItem.yemek_siparis_adet}", fontSize = 12.sp)
                }
            }
            
            Text(text = "₺${foodTotalPrice}" , fontWeight = FontWeight.Bold)
        }
    }
}