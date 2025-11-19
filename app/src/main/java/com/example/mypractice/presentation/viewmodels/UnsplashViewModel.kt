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

    private val _photos = MutableStateFlow<List<UnsplashPhoto>>(emptyList())
    val photos: StateFlow<List<UnsplashPhoto>> = _photos

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        loadPhotos()
    }

    fun loadPhotos(page: Int = 1, perPage: Int = 30) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            repository.getPhotos(page, perPage).collect { result ->
                _photos.value = result
            }

            _loading.value = false
        }
    }
}