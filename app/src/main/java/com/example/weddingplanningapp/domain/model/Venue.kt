package com.example.weddingplanningapp.domain.model

data class Venue(
    val id: Int = 0,
    val name: String,
    val location: String,
    val priceRange: String,
    val capacity: String,
    val imageUri: String? = null
)
