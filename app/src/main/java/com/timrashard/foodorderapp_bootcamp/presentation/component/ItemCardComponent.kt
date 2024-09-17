package com.timrashard.foodorderapp_bootcamp.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.timrashard.foodorderapp_bootcamp.R
import com.timrashard.foodorderapp_bootcamp.ui.theme.Gray
import com.timrashard.foodorderapp_bootcamp.ui.theme.SoftPink
import com.timrashard.foodorderapp_bootcamp.ui.theme.SoftRed
import com.timrashard.foodorderapp_bootcamp.ui.theme.StarYellow

@Composable
fun ItemCardComponent(
    onFavoriteClick: () -> Unit,
    onItemClick: () -> Unit
) {
    val isFavorite = remember { mutableStateOf(false) }

    ElevatedCard(
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        modifier = Modifier
            .size(width = 175.dp, height = 225.dp)
            .clickable {
                onItemClick()
            }
    ) {
        Box(
            contentAlignment = Alignment.TopCenter,
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(36.dp)
                    .background(color = SoftPink, shape = CircleShape)
                    .align(Alignment.TopEnd)
            ) {
                IconButton(onClick = {
                    onFavoriteClick()
                    isFavorite.value = !isFavorite.value
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_favorite),
                        contentDescription = "Favorite",
                        tint = if (isFavorite.value) SoftRed else Color.Unspecified,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ayran),
                    contentDescription = "Ayran image",
                    modifier = Modifier.size(100.dp)
                )

                Text(
                    text = "Ayran",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_star),
                            contentDescription = "Rate",
                            tint = StarYellow,
                            modifier = Modifier.size(18.dp)
                        )

                        Spacer(modifier = Modifier.width(2.dp))

                        Text(
                            text = "4.8",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Gray,
                        )
                    }

                    VerticalDivider(
                        thickness = 1.dp,
                        modifier = Modifier
                            .height(20.dp)
                            .padding(horizontal = 8.dp),
                        color = Gray
                    )

                    Text(
                        text = "330 ml",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Gray,
                        modifier = Modifier.weight(1f)
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "₺", fontSize = 16.sp)

                    Spacer(modifier = Modifier.width(2.dp))

                    Text(
                        text = "30",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }


            }
        }
    }
}
