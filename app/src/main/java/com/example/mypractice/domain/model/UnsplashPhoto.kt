package com.example.mypractice.domain.model

import com.example.mypractice.data.local.Urls

data class UnsplashPhoto(
    val id: String,
    val description: String?,
    val urls: Urls
)