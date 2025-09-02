package com.example.weddingplanningapp.data.repository

import com.example.weddingplanningapp.data.local.BookmarkDao
import com.example.weddingplanningapp.data.local.BookmarkEntity
import com.example.weddingplanningapp.domain.repository.BookmarkRepository
import kotlinx.coroutines.flow.Flow

class BookmarkRepositoryImpl(
    private val dao: BookmarkDao
) : BookmarkRepository {   // ✅ implement kiya interface

    override suspend fun insertBookmark(bookmark: BookmarkEntity) {
        dao.insertBookmark(bookmark)
    }

    override fun getAllBookmarks(): Flow<List<BookmarkEntity>> =
        dao.getAllBookmarks()

    override fun getBookmarkByVenueId(venueId: Int): Flow<BookmarkEntity?> =
        dao.getBookmarkByVenueId(venueId)

    override suspend fun deleteBookmark(bookmark: BookmarkEntity) {
        dao.deleteBookmark(bookmark)
    }

    override suspend fun deleteBookmarkByVenueId(venueId: Int) {
        dao.deleteBookmarkByVenueId(venueId)
    }

    override fun getBookmarkById(id: Int): Flow<BookmarkEntity?> {
        return dao.getBookmarkByVenueId(id) // reuse your existing DAO method
    }
}