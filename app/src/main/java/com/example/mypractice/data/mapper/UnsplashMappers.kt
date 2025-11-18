package com.example.mypractice.data.mapper


import com.example.mypractice.domain.model.UnsplashPhoto
import com.example.mypractice.data.local.UnsplashPhotoEntity

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

