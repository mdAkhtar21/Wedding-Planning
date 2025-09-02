package com.example.weddingplanningapp.data.local


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmarks")
data class BookmarkEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val venueId: Int,
    val venueName: String,
    val imageUri: String?,
    val location: String,
    val priceRange: String,
    val capacity: String,
    val isVenueBooked: Boolean = false,
    val isPhotographyBooked: Boolean = false,
    val isCateringBooked: Boolean = false,
    val isMehendiBooked: Boolean = false,
    val isSangeetBooked: Boolean = false,
    val isHoneymoonBooked: Boolean = false
)
