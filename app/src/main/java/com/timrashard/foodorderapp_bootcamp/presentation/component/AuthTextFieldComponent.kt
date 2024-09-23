package com.timrashard.foodorderapp_bootcamp.presentation.component

import android.widget.Space
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.timrashard.foodorderapp_bootcamp.R
import com.timrashard.foodorderapp_bootcamp.ui.theme.SoftLightGray

@Composable
fun AuthTextFieldComponent(
    value: String,
    onValueChange: (String) -> Unit,
    placeHolder: String? = "",
    isPassword: Boolean = false,
    modifier: Modifier = Modifier
) {
    var passwordVisibility by remember { mutableStateOf(false) }

    TextField(
        value = value,
        onValueChange = onValueChange,
        visualTransformation = if (isPassword && !passwordVisibility) PasswordVisualTransformation() else VisualTransformation.None,
        textStyle = TextStyle(
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
        ),
        maxLines = 1,
        singleLine = true,
        placeholder = {
            placeHolder?.let {
                Text(text = it)
            }
        },
        keyboardOptions = KeyboardOptions(
            imeAction = if(isPassword) ImeAction.Done else ImeAction.Next
        ),
        trailingIcon = {
            Row(
                modifier = Modifier.padding(end = 8.dp),
                horizontalArrangement = Arrangement.End
            ) {
                if (isPassword) {
                    IconButton(
                        onClick = { passwordVisibility = !passwordVisibility },
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = if (passwordVisibility) Color.White else Color.Transparent
                        ),
                        modifier = Modifier.size(28.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = if (passwordVisibility) R.drawable.ic_eye_open else R.drawable.ic_eye_closed),
                            contentDescription = if (passwordVisibility) "Hide Password" else "Show Password",
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                if (value.isNotEmpty()) {
                    Spacer(modifier = Modifier.width(8.dp))
                    
                    IconButton(
                        onClick = { onValueChange("") },
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = Color.White
                        ),
                        modifier = Modifier.size(28.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Clear",
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        },
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = SoftLightGray,
            unfocusedIndicatorColor = Color.Transparent,
            focusedContainerColor = SoftLightGray,
            focusedIndicatorColor = Color.Transparent
        ),
        modifier = modifier.fillMaxWidth()
    )
}
