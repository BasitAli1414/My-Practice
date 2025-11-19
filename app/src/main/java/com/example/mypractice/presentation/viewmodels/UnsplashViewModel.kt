package com.example.mypractice.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mypractice.domain.model.UnsplashPhoto
import com.example.mypractice.data.repository.UnsplashRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UnsplashViewModel @Inject constructor(private val repository: UnsplashRepository) : ViewModel() {

    // StateFlow to hold the list of photos
    private val _photos = MutableStateFlow<List<UnsplashPhoto>>(emptyList())
    val photos: StateFlow<List<UnsplashPhoto>> = _photos

    // StateFlow for loading indicator
    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    // StateFlow for error message
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        // Load initial photos when the ViewModel is created
        loadPhotos()
    }

    fun loadPhotos(page: Int = 1, perPage: Int = 30) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            // Use .collect to get results from the repository's Flow
            repository.getPhotos(page, perPage).collect { result ->
                // Assuming the result flow emits the list directly upon success
                // In a real app, you'd handle loading/error/success states here
                _photos.value = result
            }

            _loading.value = false
        }
    }
}