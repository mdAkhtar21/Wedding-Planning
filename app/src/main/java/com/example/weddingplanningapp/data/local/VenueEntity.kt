package com.example.weddingplanningapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "venues")
data class VenueEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val location: String,
    val priceRange: String,
    val capacity: String,
    val imageUri: String?
)
