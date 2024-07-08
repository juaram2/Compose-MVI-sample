package com.myapp.data.repository

import android.os.SystemClock
import com.myapp.data.ApiService
import com.myapp.domain.model.ImagesModel
import com.myapp.domain.model.VideosModel
import com.myapp.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val service: ApiService
): SearchRepository {
    private var searchImage = mutableMapOf<String, Pair<Long, ImagesModel>>()
    private var searchVideo = mutableMapOf<String, Pair<Long, VideosModel>>()

    override suspend fun searchImage(query: String, page: Int): Flow<ImagesModel> = flow {
//        if (!isCachedImage(query)) {
            val result = service.searchImage(query, page).body()!!
            val curTime = SystemClock.elapsedRealtime()
            searchImage[query] = Pair(curTime, result)
            emit(result)
//        } else if (page > 1) {
//            val result = service.searchImage(query, page).body()!!
//            emit(result)
//        } else {
//            emit(searchImage[query]!!.second)
//        }
    }

    override suspend fun searchVideo(query: String, page: Int): Flow<VideosModel> = flow {
//        if (!isCachedVideo(query)) {
            val result = service.searchVideo(query, page).body()!!
            val curTime = SystemClock.elapsedRealtime()
            searchVideo[query] = Pair(curTime, result)
            emit(result)
//        } else if (page > 1) {
//            val result = service.searchVideo(query, page).body()!!
//            emit(result)
//        } else {
//            emit(searchVideo[query]!!.second)
//        }
    }
}