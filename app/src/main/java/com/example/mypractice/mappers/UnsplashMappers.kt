package com.example.mypractice.mappers


import com.example.mypractice.models.UnsplashPhoto
import com.example.mypractice.room.UnsplashPhotoEntity

// Convert API model to Room entity
fun UnsplashPhoto.toEntity(): UnsplashPhotoEntity = UnsplashPhotoEntity(
    id = this.id,
    description = this.description,
    urls = this.urls
)

fun UnsplashPhotoEntity.toModel(): UnsplashPhoto = UnsplashPhoto(
    id = this.id,
    description = this.description,
    urls = this.urls
)

