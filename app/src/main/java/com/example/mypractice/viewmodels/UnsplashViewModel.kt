package com.example.mypractice.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mypractice.data.repository.UnsplashRepository
import com.example.mypractice.models.UnsplashPhoto
import com.example.mypractice.rest.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

//class UnsplashViewModel : ViewModel() {
//
//    private val _photos = MutableStateFlow<List<UnsplashPhoto>>(emptyList())
//    val photos: StateFlow<List<UnsplashPhoto>> = _photos
//
//    fun loadPhotos() {
//        viewModelScope.launch {
//            try {
//                val result = RetrofitInstance.api.getPhotos()
//                _photos.value = result
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//        }
//    }
//}

class UnsplashViewModel(private val repository: UnsplashRepository) : ViewModel() {

    private val _photos = MutableStateFlow<List<UnsplashPhoto>>(emptyList())
    val photos: StateFlow<List<UnsplashPhoto>> = _photos

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

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
