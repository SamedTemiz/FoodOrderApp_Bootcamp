package com.timrashard.foodorderapp_bootcamp.presentation.component

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.timrashard.foodorderapp_bootcamp.ui.theme.BgBlack

@Composable
fun MainButtonComponent(
    text: String,
    modifier: Modifier
){
    Button(
        onClick = { /*TODO*/ },
        shape = RoundedCornerShape(15.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = BgBlack,
            contentColor = Color.White
        ),
        modifier = modifier
    ) {
        Text(
            text = text,
            fontSize = 24.sp,
            modifier = Modifier.padding(vertical = 10.dp)
        )
    }
}