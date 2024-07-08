package com.myapp.domain.repository

import com.myapp.domain.model.ImagesModel
import com.myapp.domain.model.VideosModel
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    suspend fun searchImage(query: String, page: Int): Flow<ImagesModel>
    suspend fun searchVideo(query: String, page: Int): Flow<VideosModel>
}