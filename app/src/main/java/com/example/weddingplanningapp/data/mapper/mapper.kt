package com.example.weddingplanningapp.data.mapper

import com.example.weddingplanningapp.data.local.VenueEntity
import com.example.weddingplanningapp.domain.model.Venue

fun VenueEntity.toDomain(): Venue = Venue(
    id = id,
    name = name,
    location = location,
    priceRange = priceRange,
    capacity = capacity,
    imageUri = imageUri
)

fun Venue.toEntity(): VenueEntity = VenueEntity(
    id = id,
    name = name,
    location = location,
    priceRange = priceRange,
    capacity = capacity,
    imageUri = imageUri
)