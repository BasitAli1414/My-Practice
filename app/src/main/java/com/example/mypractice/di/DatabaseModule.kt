package com.example.mypractice.di

import android.content.Context
import androidx.room.Room
import com.example.mypractice.data.local.AppDatabase
import com.example.mypractice.data.remote.UnsplashPhotoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "unsplash_db"
        ).build()
    }

    @Provides
    fun provideUnsplashPhotoDao(appDatabase: AppDatabase): UnsplashPhotoDao {
        return appDatabase.unsplashPhotoDao()
    }
}