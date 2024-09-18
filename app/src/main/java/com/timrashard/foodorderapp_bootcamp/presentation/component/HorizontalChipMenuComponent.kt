package com.timrashard.foodorderapp_bootcamp.presentation.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.timrashard.foodorderapp_bootcamp.domain.model.ChipItem
import com.timrashard.foodorderapp_bootcamp.ui.theme.SoftGray
import com.timrashard.foodorderapp_bootcamp.ui.theme.SmokeWhite
import kotlinx.coroutines.launch

@Composable
fun HorizontalChipMenuComponent(
    chipList: List<ChipItem>,
    onSelected: (Int) -> Unit
) {
    var selectedIndex by remember { mutableStateOf(0) }

    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        state = listState,
        contentPadding = PaddingValues(start = 20.dp, end = 20.dp)
    ) {
        itemsIndexed(chipList) { index, chip ->

            AssistChip(
                onClick = {
                    selectedIndex = index
                    scope.launch {
                        listState.animateScrollToItem(index = index, scrollOffset = -200)
                    }
                    onSelected(index)
                },
                label = {
                    Text(
                        text = chip.label,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .padding(vertical = 10.dp)
                    )
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = chip.icon),
                        contentDescription = "Chip",
                        tint = Color.Unspecified,
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .size(24.dp)
                    )
                },
                colors = AssistChipDefaults.assistChipColors(
                    containerColor = if (selectedIndex == index) SmokeWhite else Color.Transparent,
                    labelColor = if (selectedIndex == index) Color.Black else SoftGray,
                ),
                border = BorderStroke(1.dp, if (selectedIndex == index) Color.Black else SoftGray)
            )
        }
    }
}