package com.timrashard.foodorderapp_bootcamp.presentation.screen.auth

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.timrashard.foodorderapp_bootcamp.R
import com.timrashard.foodorderapp_bootcamp.presentation.component.AuthTextFieldComponent
import com.timrashard.foodorderapp_bootcamp.presentation.component.MainButtonComponent
import com.timrashard.foodorderapp_bootcamp.presentation.navigation.Screen
import com.timrashard.foodorderapp_bootcamp.ui.theme.SoftGray
import com.timrashard.foodorderapp_bootcamp.ui.theme.SoftLightGray
import com.timrashard.foodorderapp_bootcamp.ui.theme.SoftOrange
import com.timrashard.foodorderapp_bootcamp.ui.theme.SoftPink
import com.timrashard.foodorderapp_bootcamp.utils.Auth
import kotlinx.coroutines.flow.collectLatest

@Composable
fun LoginScreen(navController: NavController) {
    var selectedTabIndex by remember { mutableStateOf(0) }
//    var authState by remember { mutableStateOf(Auth.LOGIN) }

    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var rememberMe by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ElevatedCard(
                shape = CircleShape,
                colors = CardDefaults.cardColors(
                    containerColor = SoftPink
                ),
                modifier = Modifier
                    .size(72.dp)
                    .background(color = SoftPink, shape = CircleShape)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.auth),
                        contentDescription = "Auth",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.size(48.dp)
                    )
                }
            }

            Text(text = "Login or Register", fontSize = 28.sp, fontWeight = FontWeight.SemiBold)

            Text(
                text = "Your favorite dishes delivered to your door, just a click away!",
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                color = Color.Gray,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        CustomTabRow(
            tabs = listOf("Login", "Register"),
            selectedTabIndex = selectedTabIndex,
            onTabSelected = { selectedTabIndex = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            AuthTextFieldComponent(
                value = email,
                onValueChange = { email = it },
                isPassword = false,
                placeHolder = "Enter your email",
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            )

            AuthTextFieldComponent(
                value = password,
                onValueChange = { password = it },
                isPassword = true,
                placeHolder = "Enter your password",
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
            )
        }


        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = rememberMe,
                    onCheckedChange = { rememberMe = it },
                    colors = CheckboxDefaults.colors(
                        checkedColor = SoftOrange,
                    )
                )
                Text(
                    text = "Remember me",
                    fontSize = 14.sp,
                    color = if (rememberMe) Color.Black else Color.Gray
                )
            }

            if (selectedTabIndex == 0) {
                TextButton(onClick = {}) {
                    Text(text = "Forgot Password?", fontSize = 14.sp, color = Color.Gray)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        MainButtonComponent(
            text = if (selectedTabIndex == 0) "Login" else "Register",
            onClick = {},
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun CustomTabRow(
    tabs: List<String>,
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = SoftLightGray, shape = RoundedCornerShape(16.dp))
    ) {
        TabRow(
            selectedTabIndex = selectedTabIndex,
            indicator = {},
            divider = {},
            containerColor = Color.Transparent,
            modifier = Modifier
                .padding(4.dp)
                .clip(RoundedCornerShape(12.dp))
        ) {
            tabs.forEachIndexed { index, tab ->
                if (index == selectedTabIndex) {
                    ElevatedCard {
                        Tab(
                            selected = true,
                            onClick = { onTabSelected(index) },
                            modifier = Modifier
                                .background(
                                    color = Color.White,
                                    shape = RoundedCornerShape(12.dp)
                                )
                        ) {
                            Text(
                                text = tab,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(vertical = 10.dp)
                            )
                        }
                    }
                } else {
                    Tab(
                        selected = false,
                        onClick = { onTabSelected(index) },
                        modifier = Modifier
                            .background(
                                color = Color.Transparent,
                                shape = RoundedCornerShape(12.dp)
                            )
                    ) {
                        Text(
                            text = tab,
                            fontSize = 18.sp,
                            color = Color.DarkGray,
                            modifier = Modifier.padding(vertical = 10.dp)
                        )
                    }
                }
            }
        }
    }
}
