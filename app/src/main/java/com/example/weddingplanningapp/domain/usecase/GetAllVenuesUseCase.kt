package com.example.weddingplanningapp.domain.usecase

import com.example.weddingplanningapp.domain.model.Venue
import com.example.weddingplanningapp.domain.repository.VenueRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllVenuesUseCase @Inject constructor(
    private val repository: VenueRepository
) {
    operator fun invoke(): Flow<List<Venue>> = repository.getAllVenues()
}