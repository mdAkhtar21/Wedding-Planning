package com.example.weddingplanningapp.data.repository

import com.example.weddingplanningapp.data.local.VenueDao
import com.example.weddingplanningapp.data.local.VenueEntity
import com.example.weddingplanningapp.domain.model.Venue
import com.example.weddingplanningapp.domain.repository.VenueRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class VenueRepositoryImpl(
    private val dao: VenueDao
) : VenueRepository {

    override suspend fun insertVenue(venue: Venue) {
        dao.insertVenue(
            VenueEntity(
                id = venue.id,
                name = venue.name,
                location = venue.location,
                priceRange = venue.priceRange,
                capacity = venue.capacity,
                imageUri = venue.imageUri
            )
        )
    }

    override fun getAllVenues(): Flow<List<Venue>> {
        return dao.getAllVenues().map { list ->
            list.map { entity ->
                Venue(
                    id = entity.id,
                    name = entity.name,
                    location = entity.location,
                    priceRange = entity.priceRange,
                    capacity = entity.capacity,
                    imageUri = entity.imageUri
                )
            }
        }
    }
}