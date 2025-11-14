package com.example.mypractice.room

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "unsplash_photos")
data class UnsplashPhotoEntity(
    @PrimaryKey val id: String,
    val description: String?,
    @Embedded val urls: Urls
)

data class Urls(
    val raw: String,
    val full: String,
    val regular: String,
    val small: String,
    val thumb: String
)