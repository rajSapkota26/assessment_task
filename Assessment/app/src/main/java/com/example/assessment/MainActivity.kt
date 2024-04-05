package com.example.assessment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.assessment.ui.theme.AssessmentTheme
import com.example.assessment.utils.navigation.NavGraph
import com.example.assessment.utils.navigation.NavigationScreen

class MainActivity : ComponentActivity() {
    private lateinit var navHostController: NavHostController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AssessmentTheme(darkTheme = false) {
                navHostController = rememberNavController()
                NavGraph(
                    navController = navHostController,
                    startDestination = NavigationScreen.splashScreen
                )
            }
        }
    }
}

