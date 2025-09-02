package com.example.weddingplanningapp.di

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.weddingplanningapp.data.local.BookmarkDao
import com.example.weddingplanningapp.data.local.BookmarkEntity
import com.example.weddingplanningapp.data.local.VenueDao
import com.example.weddingplanningapp.data.local.VenueEntity

@Database(
    entities = [VenueEntity::class, BookmarkEntity::class],
    version = 3,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun venueDao(): VenueDao
    abstract fun bookmarkDao(): BookmarkDao
}
