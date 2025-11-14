package com.example.mypractice.data.repository

import android.content.Context
import com.example.mypractice.mappers.toEntity
import com.example.mypractice.mappers.toModel
import com.example.mypractice.rest.RetrofitInstance
import com.example.mypractice.room.AppDatabase
import com.example.mypractice.util.NetworkUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class UnsplashRepository(var context: Context) {

    private val dao = AppDatabase.getDatabase(context).unsplashPhotoDao()
    private val api = RetrofitInstance.api

    fun getPhotos(page: Int = 1, perPage: Int = 30) = flow {
        val isConnected = NetworkUtils.isInternetAvailable(context)
        if (isConnected) {
            val photosFromApi = api.getPhotos(page, perPage)
            dao.insertPhotos(photosFromApi.map { it.toEntity() })
            emit(photosFromApi)
        } else {
            val cachedPhotos = dao.getAllPhotos().map { it.toModel() }
            emit(cachedPhotos)
        }
    }.flowOn(Dispatchers.IO)
}
