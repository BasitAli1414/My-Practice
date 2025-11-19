package com.example.mypractice.data.repository

import android.content.Context
import com.example.mypractice.data.mapper.toEntity
import com.example.mypractice.data.mapper.toModel
import com.example.mypractice.data.remote.ApiInterface
import com.example.mypractice.data.remote.UnsplashPhotoDao
import com.example.mypractice.util.NetworkUtils
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UnsplashRepository @Inject constructor(
    private val unsplashPhotoDao: UnsplashPhotoDao,

    private val unsplashApi: ApiInterface,

    @ApplicationContext private val context: Context
) {
    fun getPhotos(page: Int = 1, perPage: Int = 30) = flow {
        val isConnected = NetworkUtils.isInternetAvailable(context)

        if (isConnected) {
            val photosFromApi = unsplashApi.getPhotos(page, perPage)
            unsplashPhotoDao.insertPhotos(photosFromApi.map { it.toEntity() })
            emit(photosFromApi)
        } else {
            val cachedPhotos = unsplashPhotoDao.getAllPhotos().map { it.toModel() }
            emit(cachedPhotos)
        }
    }.flowOn(Dispatchers.IO)
}