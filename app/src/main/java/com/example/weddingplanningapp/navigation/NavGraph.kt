package com.example.weddingplanningapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.weddingplanningapp.presentation.AddScreen.AddItemScreen
import com.example.weddingplanningapp.presentation.AddScreen.VenueViewModel
import com.example.weddingplanningapp.presentation.Bookmark.BookmarkScreen
import com.example.weddingplanningapp.presentation.Bookmark.BookmarkViewModel
import com.example.weddingplanningapp.presentation.Home.HomeScreen
import com.example.weddingplanningapp.presentation.Home.HomeViewModel
import com.example.weddingplanningapp.presentation.auth.AuthViewModel
import com.example.weddingplanningapp.presentation.auth.LoginScreen
import com.example.weddingplanningapp.presentation.auth.RegisterScreen
import com.example.weddingplanningapp.presentation.detailScreen.DetailScreen

sealed class Screen(val route: String) {
    object Register : Screen("register")
    object Login : Screen("login")
    object Home : Screen("home")
    object AddItem : Screen("add_item")
    object Bookmark : Screen("bookmark")
    object Detail : Screen("detail") {
        fun createRoute(id: Int) = "detail/$id"
    }
}

@Composable
fun NavGraph(navController: NavHostController) {
    val authViewModel: AuthViewModel = hiltViewModel()

    val isLoggedIn by authViewModel.isLoggedIn.collectAsState()

    NavHost(
        navController = navController,
        startDestination = if (isLoggedIn) "home" else "register"
    ) {

        composable("register") { RegisterScreen(authViewModel, navController) }
        composable("login") { LoginScreen(authViewModel, navController) }
        composable("home") {
            val homeViewModel: HomeViewModel = hiltViewModel()
            val authViewModel :AuthViewModel= hiltViewModel()
            HomeScreen(navController, homeViewModel,authViewModel)
        }

        // ===== Add Item Screen =====
        composable(Screen.AddItem.route) {
            val venueViewModel: VenueViewModel = hiltViewModel()
            AddItemScreen(navController, venueViewModel)
        }

        // ===== Bookmark Screen =====
        composable(Screen.Bookmark.route) {
            val bookmarkViewModel: BookmarkViewModel = hiltViewModel()
            BookmarkScreen(navController, bookmarkViewModel)
        }

        // ===== Detail Screen =====
        composable(
            route = Screen.Detail.route + "/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val venueId = backStackEntry.arguments?.getInt("id") ?: -1
            val homeViewModel: HomeViewModel = hiltViewModel()
            val bookmarkViewModel: BookmarkViewModel = hiltViewModel()
            DetailScreen(navController, venueId, homeViewModel = homeViewModel, bookmarkViewModel = bookmarkViewModel)
        }
    }
}
