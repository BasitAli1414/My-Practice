package com.example.mypractice.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.mypractice.data.remote.ApiInterface
import com.example.mypractice.domain.model.UnsplashPhoto

class UnsplashPagingSource(
    private val api: ApiInterface
) : PagingSource<Int, UnsplashPhoto>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UnsplashPhoto> {
        val page = params.key ?: 1

        return try {
            val photos = api.getPhotos(page, 30)

            LoadResult.Page(
                data = photos,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (photos.isEmpty()) null else page + 1
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, UnsplashPhoto>): Int? {
        return state.anchorPosition
    }
}
