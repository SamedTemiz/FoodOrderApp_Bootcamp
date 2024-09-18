package com.timrashard.foodorderapp_bootcamp.presentation.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.timrashard.foodorderapp_bootcamp.ui.theme.SoftGray
import com.timrashard.foodorderapp_bootcamp.ui.theme.SmokeWhite

@Composable
fun SearchBarComponent(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String = "Find your food",
) {
    val searchText = remember { mutableStateOf("") }

    TextField(
        value = value,
        onValueChange = {
            onValueChange(it)
            searchText.value = it
        },
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
        },
        trailingIcon = {
            if (searchText.value.isNotEmpty()) {
                IconButton(onClick = {
                    onValueChange("")
                    searchText.value = ""
                }) {
                    Icon(imageVector = Icons.Default.Clear, contentDescription = "Clear")
                }
            }
        },
        placeholder = {
            Text(placeholder)
        },
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = SmokeWhite,
            unfocusedLeadingIconColor = SoftGray,
            unfocusedTextColor = SoftGray,
            unfocusedPlaceholderColor = SoftGray,
            focusedContainerColor = SmokeWhite,
            focusedLeadingIconColor = Color.Black,
            focusedTextColor = Color.Black,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        modifier = modifier
    )
}