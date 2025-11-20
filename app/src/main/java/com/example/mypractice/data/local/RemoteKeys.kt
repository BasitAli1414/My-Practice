package com.example.mypractice.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKeys(
    @PrimaryKey val photoId: String,
    val prevKey: Int?,
    val nextKey: Int?
)
