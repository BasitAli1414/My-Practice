package com.example.mypractice.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UnsplashPhotoDao {

    @Query("SELECT * FROM unsplash_photos")
    suspend fun getAllPhotos(): List<UnsplashPhotoEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhotos(photos: List<UnsplashPhotoEntity>)
}
