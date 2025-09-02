package com.example.weddingplanningapp.domain.usecase

import com.example.weddingplanningapp.data.local.BookmarkEntity
import com.example.weddingplanningapp.domain.repository.BookmarkRepository
import javax.inject.Inject

class AddBookmarkUseCase @Inject constructor(
    private val repository: BookmarkRepository
) {
    suspend operator fun invoke(bookmark: BookmarkEntity) {
        repository.insertBookmark(bookmark)
    }
}