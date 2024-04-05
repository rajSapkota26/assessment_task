package com.example.assessment.feature.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.assessment.R
import com.example.assessment.ui.theme.white
import com.example.assessment.uiComponent.CommonCard
import com.example.assessment.uiComponent.FilledButton
import com.example.assessment.utils.navigation.NavigationScreen

@Composable
fun SplashScreen(navController: NavHostController) {
    CommonCard(
        paddingTop = 0.dp,
        paddingBottom = 0.dp,
        paddingStart = 0.dp,
        paddingEnd = 0.dp,
    ) {
        Box(
            modifier = Modifier
                .background(color = Color(0xFF1C2818))
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.splash_logo),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier

                )
                Text(
                    "Welcome To I&O Track",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = white,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
//                textAlign = TextAlign.Center
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                ) {
                    FilledButton(
                        label = "Proceed",
                    ) {
                        navController.popBackStack()
                        navController.navigate(NavigationScreen.loginScreen)
                        navController.clearBackStack(NavigationScreen.splashScreen)
                    }
                }

            }
        }


    }
}