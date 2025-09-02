package com.example.weddingplanningapp.presentation.detailScreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.weddingplanningapp.data.local.BookmarkEntity
import com.example.weddingplanningapp.presentation.Bookmark.BookmarkViewModel
import com.example.weddingplanningapp.presentation.Home.HomeViewModel
import com.example.weddingplanningapp.presentation.common.ScreenCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    navController: NavHostController,
    venueId: Int,
    homeViewModel: HomeViewModel = hiltViewModel(),
    bookmarkViewModel: BookmarkViewModel = hiltViewModel()
) {
    // Load venues
    LaunchedEffect(Unit) {
        homeViewModel.loadVenues()
    }

    val scrollState = rememberScrollState()
    val venueState by homeViewModel.getVenueById(venueId).collectAsState(initial = null)
    val venue = venueState ?: run {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text("Loading venue...")
        }
        return
    }

    // Fetch existing bookmark for this venue
    val existingBookmark by bookmarkViewModel.getBookmarkById(venueId)
        .collectAsState(initial = null)

    // Checkbox states (pre-filled if bookmark exists)
    var venueBooking by remember { mutableStateOf(existingBookmark?.isVenueBooked ?: false) }
    var photography by remember { mutableStateOf(existingBookmark?.isPhotographyBooked ?: false) }
    var catering by remember { mutableStateOf(existingBookmark?.isCateringBooked ?: false) }
    var mehendi by remember { mutableStateOf(existingBookmark?.isMehendiBooked ?: false) }
    var sangeet by remember { mutableStateOf(existingBookmark?.isSangeetBooked ?: false) }
    var honeymoon by remember { mutableStateOf(existingBookmark?.isHoneymoonBooked ?: false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detail Screen") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ScreenCard(
                imageUri = venue.imageUri,
                name = venue.name,
                location = venue.location,
                price = venue.priceRange,
                capacity = venue.capacity,
                onClick = {}
            )

            ChecklistItem("Venue Booking", venueBooking) { venueBooking = it }
            ChecklistItem("Photography", photography) { photography = it }
            ChecklistItem("Catering", catering) { catering = it }
            ChecklistItem("Mehendi", mehendi) { mehendi = it }
            ChecklistItem("Sangeet", sangeet) { sangeet = it }
            ChecklistItem("Honeymoon", honeymoon) { honeymoon = it }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val bookmark = BookmarkEntity(
                        id = existingBookmark?.id ?: 0, // preserve ID if exists
                        venueId = venue.id,
                        venueName = venue.name,
                        imageUri = venue.imageUri,
                        location = venue.location,
                        priceRange = venue.priceRange,
                        capacity = venue.capacity,
                        isVenueBooked = venueBooking,
                        isPhotographyBooked = photography,
                        isCateringBooked = catering,
                        isMehendiBooked = mehendi,
                        isSangeetBooked = sangeet,
                        isHoneymoonBooked = honeymoon
                    )
                    bookmarkViewModel.saveBookmark(bookmark) // updates if ID exists
                    navController.navigate("bookmark") {
                        popUpTo("bookmark") { inclusive = true }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.Check, contentDescription = "Save")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Save / Update")
            }
        }
    }
}

@Composable
fun ChecklistItem(label: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label)
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
    }
}
