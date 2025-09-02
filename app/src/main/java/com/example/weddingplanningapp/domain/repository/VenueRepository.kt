package com.example.weddingplanningapp.domain.repository

import com.example.weddingplanningapp.domain.model.Venue
import kotlinx.coroutines.flow.Flow

interface VenueRepository {
    suspend fun insertVenue(venue: Venue)
    fun getAllVenues(): Flow<List<Venue>>
}