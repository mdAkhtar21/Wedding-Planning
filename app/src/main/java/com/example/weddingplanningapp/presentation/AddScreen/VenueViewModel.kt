package com.example.weddingplanningapp.presentation.AddScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weddingplanningapp.di.VenueUseCases
import com.example.weddingplanningapp.domain.model.Venue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VenueViewModel @Inject constructor(
    private val useCases: VenueUseCases
) : ViewModel() {

    fun addVenue(venue: Venue) {
        viewModelScope.launch {
            useCases.addVenue(venue)
        }
    }
}

