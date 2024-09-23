package com.timrashard.foodorderapp_bootcamp.presentation.screen.main

import android.content.Context
import android.view.Gravity
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.SubcomposeAsyncImage
import com.google.firebase.auth.FirebaseUser
import com.timrashard.foodorderapp_bootcamp.R
import com.timrashard.foodorderapp_bootcamp.common.Constants
import com.timrashard.foodorderapp_bootcamp.data.model.Yemekler
import com.timrashard.foodorderapp_bootcamp.data.model.toSepetYemekler
import com.timrashard.foodorderapp_bootcamp.presentation.component.MainButtonComponent
import com.timrashard.foodorderapp_bootcamp.presentation.component.error.SearchErrorComponent
import com.timrashard.foodorderapp_bootcamp.presentation.viewmodel.FavoritesViewModel
import com.timrashard.foodorderapp_bootcamp.presentation.viewmodel.SharedViewModel
import com.timrashard.foodorderapp_bootcamp.ui.theme.BgBlack
import com.timrashard.foodorderapp_bootcamp.ui.theme.SoftGray
import com.timrashard.foodorderapp_bootcamp.ui.theme.SoftLightGray
import com.timrashard.foodorderapp_bootcamp.ui.theme.SoftPink
import com.timrashard.foodorderapp_bootcamp.ui.theme.SoftRed
import com.timrashard.foodorderapp_bootcamp.utils.DialogUtils
import com.timrashard.foodorderapp_bootcamp.utils.Resource
import kotlinx.coroutines.time.delay

@Composable
fun FavoritesScreen(
    userId: String?,
    sharedViewModel: SharedViewModel,
    favoritesViewModel: FavoritesViewModel = hiltViewModel()
) {
    val favorites by favoritesViewModel.favorites.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        userId?.let { id ->
            favoritesViewModel.getFavorites(userId = id)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(top = 8.dp, bottom = 100.dp)
        ) {
            when (favorites) {
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
                    val favoriteList = (favorites as Resource.Success).data

                    if (favoriteList.isNullOrEmpty()) {
                        item {
                            SearchErrorComponent(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp)
                            )
                        }
                    } else {
                        if (favoriteList.isNotEmpty()) {
                            items(favoriteList) { item ->
                                var isVisible by remember { mutableStateOf(true) }

                                AnimatedVisibility(visible = isVisible) {
                                    FavoriteItem(
                                        item = item,
                                        onRemoveFavoriteClick = {
                                            userId?.let {
                                                isVisible = false
                                                favoritesViewModel.deleteFavorite(
                                                    userId,
                                                    item.yemek_id
                                                )
                                            }
                                        },
                                        onAddToCartClick = {
                                            sharedViewModel.addFoodToCart(item.toSepetYemekler(siparisAdet = 1), isDetails = true)
                                            DialogUtils.showToast(context, "Item is added!")
                                        }
                                    )
                                }
                            }
                        } else {
                            item {
                                SearchErrorComponent(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(16.dp)
                                )
                            }
                        }

                    }
                }

                is Resource.Error -> {
                    item { Text("Hata: ${favorites.message}") }
                }
            }
        }
    }
}

@Composable
fun FavoriteItem(
    item: Yemekler,
    onRemoveFavoriteClick: () -> Unit,
    onAddToCartClick: () -> Unit
) {
    val imageUrl = Constants.IMAGE_URL + item.yemek_resim_adi

    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.White, shape = RoundedCornerShape(16.dp))
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(36.dp)
                        .background(color = SoftPink, shape = CircleShape)
                ) {
                    IconButton(onClick = {
                        onRemoveFavoriteClick()
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_favorite),
                            contentDescription = "Favorite",
                            tint = SoftRed,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(72.dp)
                        .background(
                            color = SoftPink,
                            shape = RoundedCornerShape(20.dp)
                        )
                ) {
                    SubcomposeAsyncImage(
                        model = imageUrl,
                        contentDescription = "Food Image",
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(4.dp),
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

                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxHeight()
                ) {
                    Text(
                        text = item.yemek_adi,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(text = "₺${item.yemek_fiyat}", fontSize = 14.sp, color = Color.Gray)
                }
            }

            Button(
                onClick = {
                    onAddToCartClick()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = BgBlack,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = "Add to cart",
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}