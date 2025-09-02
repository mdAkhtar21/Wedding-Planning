package com.example.weddingplanningapp.presentation.Bookmark

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.weddingplanningapp.data.local.BookmarkEntity
import com.example.weddingplanningapp.presentation.Home.HomeViewModel
import com.example.weddingplanningapp.presentation.common.ScreenCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun BookmarkScreen(
    navController: NavHostController,
    viewModel: BookmarkViewModel = hiltViewModel(),
    homeViewModel: HomeViewModel= hiltViewModel()
)
{
    val bookmarks = viewModel.bookmarks.collectAsState()




    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Bookmarks") },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate("home") {
                            popUpTo("bookmark") { inclusive = true }
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ){ padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(bookmarks.value) { bookmark ->
                BookmarkItem(bookmark,navController)
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
fun BookmarkItem(
    bookmark: BookmarkEntity,
    navController: NavHostController,
    viewModel: BookmarkViewModel = hiltViewModel() // pass ViewModel here
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        ScreenCard(
            imageUri = bookmark.imageUri,
            name = bookmark.venueName,
            location = bookmark.location,
            price = bookmark.priceRange,
            capacity = bookmark.capacity,
            onClick = {
                navController.navigate("detail/${bookmark.venueId}")
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        Column(modifier = Modifier.padding(start = 8.dp)) {
            Text(text = "List", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            if (bookmark.isVenueBooked) Text("Venue Booked")
            if (bookmark.isPhotographyBooked) Text("Photography")
            if (bookmark.isCateringBooked) Text("Catering")
            if (bookmark.isMehendiBooked) Text("Mehendi")
            if (bookmark.isSangeetBooked) Text("Sangeet")
            if (bookmark.isHoneymoonBooked) Text("Honeymoon")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {

            Button(onClick = {
                // Pass bookmarkId so DetailScreen knows it's an update
                navController.navigate("detail/${bookmark.venueId}?bookmarkId=${bookmark.id}")
            }) {
                Text("Update")
            }


            Button(onClick = {
                viewModel.deleteBookmark(bookmark) // ✅ Use ViewModel passed as parameter
            }) {
                Text("Delete")
            }
        }
    }
}
