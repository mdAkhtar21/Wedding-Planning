package com.example.weddingplanningapp.domain.usecase

import com.example.weddingplanningapp.data.local.BookmarkEntity
import com.example.weddingplanningapp.domain.repository.BookmarkRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllBookmarksUseCase @Inject constructor(
    private val repository: BookmarkRepository
) {
    operator fun invoke(): Flow<List<BookmarkEntity>> = repository.getAllBookmarks()
}