package com.example.weddingplanningapp.data.local


import androidx.room.*
import kotlinx.coroutines.flow.Flow
@Dao
interface BookmarkDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmark(bookmark: BookmarkEntity)

    @Query("SELECT * FROM bookmarks")
    fun getAllBookmarks(): Flow<List<BookmarkEntity>>

    @Query("SELECT * FROM bookmarks WHERE venueId = :venueId LIMIT 1")
    fun getBookmarkByVenueId(venueId: Int): Flow<BookmarkEntity?>

    @Delete
    suspend fun deleteBookmark(bookmark: BookmarkEntity)

    // ✅ Delete directly by venueId
    @Query("DELETE FROM bookmarks WHERE venueId = :venueId")
    suspend fun deleteBookmarkByVenueId(venueId: Int)
}
