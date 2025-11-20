package com.example.mypractice.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.mypractice.data.local.AppDatabase
import com.example.mypractice.data.local.UnsplashPhotoEntity
import com.example.mypractice.data.paging.UnsplashRemoteMediator
import com.example.mypractice.data.remote.ApiInterface
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/*
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
}*/

@OptIn(ExperimentalPagingApi::class)
@Singleton
class UnsplashRepository @Inject constructor(
    private val db: AppDatabase,
    private val api: ApiInterface
) {
    fun getPagedPhotos(): Flow<PagingData<UnsplashPhotoEntity>> {
        return Pager(
            config = PagingConfig(pageSize = 30),
            remoteMediator = UnsplashRemoteMediator(api, db),
            pagingSourceFactory = { db.unsplashPhotoDao().pagingSource() }
        ).flow
    }
}
