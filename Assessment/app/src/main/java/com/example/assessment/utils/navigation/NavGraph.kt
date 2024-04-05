package com.example.assessment.utils.navigation

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.assessment.feature.dashboard.screen.DashBoardScreen
import com.example.assessment.feature.dashboard.vm.DashBoardVM
import com.example.assessment.feature.dashboard.vm.DashBoardVMFactory
import com.example.assessment.feature.login.LoginScreen
import com.example.assessment.feature.login.vm.LoginVM
import com.example.assessment.feature.login.vm.LoginVMFactory
import com.example.assessment.feature.register.CreateAccountScreen
import com.example.assessment.feature.register.vm.RegisterVM
import com.example.assessment.feature.register.vm.RegisterVMFactory
import com.example.assessment.feature.splash.SplashScreen
import com.example.assessment.service_layer.repository.RoomRepository

@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String
) {
    val context = LocalContext.current.applicationContext as Application

    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {

        composable(NavigationScreen.splashScreen) {
            SplashScreen(navController = navController)
        }
        composable(NavigationScreen.loginScreen) {
            val viewModel: LoginVM = viewModel(
                factory = LoginVMFactory(
                    context, RoomRepository(context)
                )
            )
            LoginScreen(
                navController = navController,
                state = viewModel.state,
                onEvent = viewModel::onEvent
            )
        }
        composable(NavigationScreen.registerScreen) {
            val viewModel: RegisterVM = viewModel(
                factory = RegisterVMFactory(
                    context, RoomRepository(context)
                )
            )
            CreateAccountScreen(
                navController = navController,
                state = viewModel.state,
                onEvent = viewModel::onEvent
            )
        }
        composable(NavigationScreen.homeScreen) {
            val viewModel: DashBoardVM = viewModel(
                factory = DashBoardVMFactory(
                    context, RoomRepository(context)
                )
            )
            DashBoardScreen(
                navController = navController, state = viewModel.state,
                onEvent = viewModel::onEvent
            )
        }

    }
}