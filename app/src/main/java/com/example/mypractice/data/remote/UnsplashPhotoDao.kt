package com.example.mypractice.data.remote

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mypractice.data.local.UnsplashPhotoEntity

@Dao
interface UnsplashPhotoDao {

    @Query("SELECT * FROM unsplash_photos")
    suspend fun getAllPhotos(): List<UnsplashPhotoEntity>

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertPhotos(photos: List<UnsplashPhotoEntity>)
}