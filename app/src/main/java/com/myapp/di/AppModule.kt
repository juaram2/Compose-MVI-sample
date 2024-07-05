package com.myapp.di

import com.myapp.data.remote.ApiService
import com.myapp.data.remote.SearchRepository
import com.myapp.domain.SearchRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(ViewModelComponent::class)
@Module
abstract class AppModule {
    @Binds
    abstract fun provideSearchRepository(impl: SearchRepositoryImpl): SearchRepository

//    @Binds
//    abstract fun provideBookmarkRepository(impl: BookmarkRepositoryImpl): BookmarkRepository
}

//@InstallIn(SingletonComponent::class)
//@Module
//object DBModule {
//
//    @Singleton
//    @Provides
//    fun provideAppDatabase(
//        @ApplicationContext context: Context
//    ): AppDatabase = AppDatabase.getInstance(context)
//
//    @Singleton
//    @Provides
//    fun provideThumbnailEntityDao(appDatabase: AppDatabase): BookmarkDao =
//        appDatabase.bookmarkEntityDao()
//}

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {
    @Singleton
    @Provides
    fun provideSearchService(): ApiService {
        return ApiService.create()
    }

//    @Singleton
//    @Provides
//    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
//        return context.getSharedPreferences("SharedPreference", Context.MODE_PRIVATE)
//    }
}