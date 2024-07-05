package com.myapp.data.remote

import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    suspend fun searchImage(query: String, page: Int): Flow<ImagesModel>
    suspend fun searchVideo(query: String, page: Int): Flow<VideosModel>
}