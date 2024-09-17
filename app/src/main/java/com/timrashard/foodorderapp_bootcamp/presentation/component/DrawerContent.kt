package com.timrashard.foodorderapp_bootcamp.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DrawerContent() {
    val menuList = listOf(
        "Deneme"
    )

    val selectedItem = remember { mutableStateOf(0) }

    ModalDrawerSheet(
        modifier = Modifier.fillMaxWidth(0.70f),
        drawerShape = RectangleShape,
        drawerContainerColor = Color.White,
        drawerContentColor = Color.Black
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 16.dp)
            ) {
                Column {
                    Text(
                        text = "Samed Temiz",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        text = "TimRashard",
                        fontSize = 16.sp,
                    )
                }
            }
        }
    }
}

data class DrawerMenu(var icon: Int, var title: String)