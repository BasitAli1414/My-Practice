package com.example.mypractice.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mypractice.data.remote.UnsplashPhotoDao

@Database(
    entities = [
        UnsplashPhotoEntity::class,
        RemoteKeys::class
               ],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun unsplashPhotoDao(): UnsplashPhotoDao

    abstract fun remoteKeysDao(): RemoteKeysDao


    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}