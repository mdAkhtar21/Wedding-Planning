package com.example.weddingplanningapp.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.weddingplanningapp.auth.data.UserPreferences
import com.example.weddingplanningapp.data.local.BookmarkDao
import com.example.weddingplanningapp.data.local.VenueDao
import com.example.weddingplanningapp.data.repository.BookmarkRepositoryImpl
import com.example.weddingplanningapp.data.repository.VenueRepositoryImpl
import com.example.weddingplanningapp.domain.repository.BookmarkRepository
import com.example.weddingplanningapp.domain.repository.VenueRepository
import com.example.weddingplanningapp.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            "wedding_db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideVenueDao(db: AppDatabase): VenueDao = db.venueDao()

    @Provides
    fun provideBookmarkDao(db: AppDatabase): BookmarkDao = db.bookmarkDao()

    @Provides
    @Singleton
    fun provideUserPreferences(@ApplicationContext context: Context): UserPreferences {
        return UserPreferences(context)
    }

    @Provides
    @Singleton
    fun provideVenueRepository(dao: VenueDao): VenueRepository = VenueRepositoryImpl(dao)

    @Provides
    @Singleton
    fun provideBookmarkRepository(dao: BookmarkDao): BookmarkRepository = BookmarkRepositoryImpl(dao)

    @Provides
    @Singleton
    fun provideVenueUseCases(repository: VenueRepository) = VenueUseCases(
        addVenue = AddVenueUseCase(repository),
        getAllVenues = GetAllVenuesUseCase(repository)
    )

    @Provides
    @Singleton
    fun provideBookmarkUseCases(repository: BookmarkRepository) = BookmarkUseCases(
        addBookmark = AddBookmarkUseCase(repository),
        deleteBookmark = DeleteBookmarkUseCase(repository),
        getBookmarks = GetAllBookmarksUseCase(repository),
        getBookmarkById = GetBookmarkByIdUseCase(repository)
    )
}

data class VenueUseCases(
    val addVenue: AddVenueUseCase,
    val getAllVenues: GetAllVenuesUseCase
)

data class BookmarkUseCases(
    val addBookmark: AddBookmarkUseCase,
    val deleteBookmark: DeleteBookmarkUseCase,
    val getBookmarks: GetAllBookmarksUseCase,
    val getBookmarkById: GetBookmarkByIdUseCase // ✅ added
)


