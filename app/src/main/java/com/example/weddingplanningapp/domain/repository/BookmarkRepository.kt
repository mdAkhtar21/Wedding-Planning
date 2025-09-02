package com.example.weddingplanningapp.domain.repository

import kotlinx.coroutines.flow.Flow
import com.example.weddingplanningapp.data.local.BookmarkEntity


interface BookmarkRepository {
    suspend fun insertBookmark(bookmark: BookmarkEntity)
    fun getAllBookmarks(): Flow<List<BookmarkEntity>>
    fun getBookmarkByVenueId(venueId: Int): Flow<BookmarkEntity?>
    suspend fun deleteBookmark(bookmark: BookmarkEntity)
    suspend fun deleteBookmarkByVenueId(venueId: Int)
    fun getBookmarkById(id: Int): Flow<BookmarkEntity?>

}
