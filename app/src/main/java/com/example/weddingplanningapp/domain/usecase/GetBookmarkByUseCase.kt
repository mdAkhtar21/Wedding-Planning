package com.example.weddingplanningapp.domain.usecase

import com.example.weddingplanningapp.domain.repository.BookmarkRepository
import javax.inject.Inject

class GetBookmarkByIdUseCase @Inject constructor(
    private val repository: BookmarkRepository
) {
    operator fun invoke(id: Int) = repository.getBookmarkById(id)
}
