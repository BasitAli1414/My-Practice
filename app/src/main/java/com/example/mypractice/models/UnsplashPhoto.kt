package com.example.mypractice.models

import com.example.mypractice.room.Urls


data class UnsplashPhoto(
    val id: String,
    val description: String?,
    val urls: Urls
)
//
//data class Urls(
//    val raw: String,
//    val full: String,
//    val regular: String,
//    val small: String,
//    val thumb: String
//)
