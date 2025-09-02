package com.example.weddingplanningapp.presentation.Home

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.weddingplanningapp.R
import com.example.weddingplanningapp.navigation.Screen
import com.example.weddingplanningapp.presentation.auth.AuthViewModel
import com.example.weddingplanningapp.presentation.common.ScreenCard
import kotlinx.coroutines.delay


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel(),
    authViewModel: AuthViewModel
) {
//    val venues = viewModel.filteredVenues.collectAsState()
    val searchQuery = viewModel.searchQuery.collectAsState()
    val venues by viewModel.filteredVenues.collectAsState(initial = emptyList())

    // ✅ State to toggle search bar visibility
    var isSearchVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.loadVenues()
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Wedding Planning App") },
                navigationIcon = {
                    IconButton(onClick = { /* maybe exit app */ }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { navController.navigate(Screen.Bookmark.route) }) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_bookmark_24),
                            contentDescription = "Bookmark"
                        )
                    }
                    IconButton(onClick = { isSearchVisible = !isSearchVisible }) {
                        Icon(Icons.Default.Search, contentDescription = "Search")
                    }
                    IconButton(onClick = { authViewModel.logout() }) {
                        Icon(Icons.Default.Close, contentDescription = "Search")
                    }

                },
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate(Screen.AddItem.route) }) {
                Icon(Icons.Default.Add, contentDescription = "Add Venue")
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {

            // ✅ Show/Hide Search TextField
            if (isSearchVisible) {
                OutlinedTextField(
                    value = searchQuery.value,
                    onValueChange = { viewModel.updateSearch(it) },
                    label = { Text("Search venues...") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    singleLine = true
                )
            }

            // ✅ Sorting buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { viewModel.sortByPriceDescending() },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Sort by Price ↓")
                }

                Button(
                    onClick = { viewModel.sortByCapacity() },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Sort by Capacity")
                }
            }
            LazyColumn {
                        items(venues) { venue ->
                            val visible = remember { mutableStateOf(false) }

                            // Trigger animation when item is composed
                            LaunchedEffect(Unit) {
                                delay(100) // small stagger effect
                                visible.value = true
                            }

                            AnimatedVisibility(
                                visible = visible.value,
                                enter = slideInHorizontally (
                                    initialOffsetX = { it }, // slide from bottom
                                    animationSpec = tween(durationMillis = 500)
                                )
                            ) {
                                ScreenCard(
                                    imageUri = venue.imageUri,
                                    name = venue.name ?: "Unknown",
                                    location = venue.location ?: "",
                                    price = venue.priceRange ?: "0",
                                    capacity = venue.capacity ?: "0",
                                    onClick = { navController.navigate(Screen.Detail.createRoute(venue.id)) }
                                )
                            }
                        }
                    }

        }
        Log.d("tag1","The error is show");
    }
}