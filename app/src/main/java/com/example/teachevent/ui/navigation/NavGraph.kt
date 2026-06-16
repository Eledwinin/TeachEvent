package com.example.teachevent.ui.navigation

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.teachevent.data.local.SessionDataStore
import com.example.teachevent.ui.screens.LoginScreen
import com.example.teachevent.ui.screens.MainAdaptiveScreen
import com.example.teachevent.ui.screens.DetailScreen
import com.example.teachevent.ui.viewmodel.LoginViewModel
import com.example.teachevent.ui.viewmodel.EventViewModel

@Composable
fun NavGraph(
    sessionDataStore: SessionDataStore,
    loginViewModel: LoginViewModel,
    windowSizeClass: WindowWidthSizeClass
) {
    val navController = rememberNavController()
    val isLoggedIn by sessionDataStore.isLoggedIn.collectAsState(initial = null)

    if (isLoggedIn != null) {
        val startDestination = if (isLoggedIn == true) Routes.Catalog.route else Routes.Login.route

        NavHost(navController = navController, startDestination = startDestination) {

            composable(Routes.Login.route) {
                LoginScreen(
                    viewModel = loginViewModel,
                    onLoginSuccess = {
                        navController.navigate(Routes.Catalog.route)
                    }
                )
            }

            composable(Routes.Catalog.route) {
                val eventViewModel: EventViewModel = viewModel()
                val eventUiState by eventViewModel.uiState.collectAsState()

                MainAdaptiveScreen(
                    uiState = eventUiState,
                    isDarkMode = false,
                    onThemeChange = { },
                    onFavoriteToggle = { },
                    onRetry = { },
                    onEventClick = { eventId ->
                        navController.navigate(Routes.Detail.createRoute(eventId))
                    }
                )
            }

            composable(
                route = Routes.Detail.route,
                arguments = listOf(navArgument("eventId") { type = NavType.StringType })
            ) { backStackEntry ->
                val eventId = backStackEntry.arguments?.getString("eventId") ?: ""
                val eventViewModel: EventViewModel = viewModel()
                val eventUiState by eventViewModel.uiState.collectAsState()

                val currentEvent = (eventUiState as? com.example.teachevent.ui.viewmodel.UIState.Success)?.events?.find {
                    it.id == eventId
                }

                DetailScreen(
                    event = currentEvent,
                    onBackClick = { navController.popBackStack() }
                )
            }
        }
    }
}