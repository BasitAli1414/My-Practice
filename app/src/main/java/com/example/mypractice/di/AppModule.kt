package com.example.mypractice.di

//import android.content.Context
//import androidx.room.Room
//import com.example.mypractice.data.repository.UnsplashRepository
//import com.example.mypractice.rest.ApiInterface
//import com.example.mypractice.room.AppDatabase
//import com.example.mypractice.room.UnsplashPhotoDao
//import dagger.Module
//import dagger.Provides
//import dagger.hilt.InstallIn
//import dagger.hilt.android.qualifiers.ApplicationContext
//import dagger.hilt.components.SingletonComponent
//import jakarta.inject.Singleton

/*@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, AppDatabase::class.java, "app_db").build()

    @Provides
    fun provideDao(db: AppDatabase) = db.unsplashPhotoDao()

    @Provides
    @Singleton
    fun provideRepository(api: ApiInterface, dao: UnsplashPhotoDao, @ApplicationContext context: Context) =
        UnsplashRepository(api, dao, context)
}*/
