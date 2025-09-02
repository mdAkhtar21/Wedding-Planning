package com.example.weddingplanningapp.domain.usecase


import com.example.weddingplanningapp.domain.model.Venue
import com.example.weddingplanningapp.domain.repository.VenueRepository
import javax.inject.Inject

class AddVenueUseCase @Inject constructor(
    private val repository: VenueRepository
) {
    suspend operator fun invoke(venue: Venue) {
        repository.insertVenue(venue)
    }
}