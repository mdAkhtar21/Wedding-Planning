package com.example.weddingplanningapp.presentation.Home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weddingplanningapp.domain.model.Venue
import com.example.weddingplanningapp.di.VenueUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCases: VenueUseCases
) : ViewModel() {

    private val _venues = MutableStateFlow<List<Venue>>(emptyList())
    val venues: StateFlow<List<Venue>> = _venues

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _priceFilter = MutableStateFlow("")
    val priceFilter: StateFlow<String> = _priceFilter

    // Filtered list based on search query & price filter
    val filteredVenues: StateFlow<List<Venue>> =
        combine(_venues, _searchQuery, _priceFilter) { venues, query, price ->
            venues.filter { venue ->
                val matchesQuery = query.isBlank() ||
                        venue.name.contains(query, ignoreCase = true) ||
                        venue.location.contains(query, ignoreCase = true)
                val matchesPrice = price.isBlank() ||
                        venue.priceRange.contains(price, ignoreCase = true)
                matchesQuery && matchesPrice
            }
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    fun loadVenues() {
        viewModelScope.launch {
            try {
                useCases.getAllVenues().collect { list ->
                    _venues.value = list
                }
            } catch(e: Exception) {
                Log.e("tag1", "Error loading venues", e)
            }
        }
    }



    // Update search query
    fun updateSearch(query: String) {
        _searchQuery.value = query
    }

    // Update price filter
    fun updatePriceFilter(price: String) {
        _priceFilter.value = price
    }

    // Get venue by id
    fun getVenueById(id: Int): StateFlow<Venue?> {
        return _venues
            .map { venues -> venues.find { it.id == id } }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)
    }

    // Sort venues by price descending
    fun sortByPriceDescending() {
        _venues.value = _venues.value.sortedByDescending { it.priceRange.toIntOrNull() ?: 0 }
    }

    fun sortByCapacity() {
        _venues.value = _venues.value.sortedByDescending { it.capacity.toIntOrNull() ?: 0 }
    }

}
