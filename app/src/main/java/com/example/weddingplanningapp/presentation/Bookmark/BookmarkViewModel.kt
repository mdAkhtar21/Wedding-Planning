package com.example.weddingplanningapp.presentation.Bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weddingplanningapp.data.local.BookmarkEntity
import com.example.weddingplanningapp.di.BookmarkUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val useCases: BookmarkUseCases
) : ViewModel() {

    val bookmarks = useCases.getBookmarks().stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        emptyList()
    )

    fun saveBookmark(bookmark: BookmarkEntity) = viewModelScope.launch {
        useCases.addBookmark(bookmark)
    }

    fun deleteBookmark(bookmark: BookmarkEntity) = viewModelScope.launch {
        useCases.deleteBookmark(bookmark)
    }
    fun getBookmarkById(id: Int) = useCases.getBookmarkById(id)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

}

