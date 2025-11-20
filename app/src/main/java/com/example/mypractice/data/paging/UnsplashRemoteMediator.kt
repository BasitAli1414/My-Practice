package com.example.mypractice.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.mypractice.data.local.AppDatabase
import com.example.mypractice.data.local.RemoteKeys
import com.example.mypractice.data.local.UnsplashPhotoEntity
import com.example.mypractice.data.mapper.toEntity
import com.example.mypractice.data.remote.ApiInterface

@OptIn(ExperimentalPagingApi::class)
class UnsplashRemoteMediator(
    private val api: ApiInterface,
    private val db: AppDatabase
) : RemoteMediator<Int, UnsplashPhotoEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, UnsplashPhotoEntity>
    ): MediatorResult {

        val page = when (loadType) {

            LoadType.REFRESH -> 1

            LoadType.PREPEND -> {
                return MediatorResult.Success(endOfPaginationReached = true)
            }

            LoadType.APPEND -> {
                val lastItem = state.lastItemOrNull()
                    ?: return MediatorResult.Success(endOfPaginationReached = true)

                val remoteKeys = db.remoteKeysDao().remoteKeys(lastItem.id)

                remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = true)
            }
        }

        try {
            val response = api.getPhotos(page = page, perPage = 30)

            val endOfPagination = response.isEmpty()

            db.withTransaction {

                if (loadType == LoadType.REFRESH) {
                    db.remoteKeysDao().clearRemoteKeys()
                    //db.unsplashPhotoDao().clearAll()
                }

                val keys = response.map { photo ->
                    RemoteKeys(
                        photoId = photo.id,
                        prevKey = if (page == 1) null else page - 1,
                        nextKey = if (endOfPagination) null else page + 1
                    )
                }

                db.remoteKeysDao().insertAll(keys)

                db.unsplashPhotoDao().insertPhotos(
                    response.map { apiPhoto -> apiPhoto.toEntity() }
                )
            }

            return MediatorResult.Success(endOfPaginationReached = endOfPagination)

        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }
}

