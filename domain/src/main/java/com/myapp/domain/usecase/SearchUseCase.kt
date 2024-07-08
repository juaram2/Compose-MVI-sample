package com.myapp.domain.usecase

import com.myapp.domain.model.Document
import com.myapp.domain.repository.SearchRepository
import com.myapp.domain.model.SearchModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.zip
import javax.inject.Inject

class SearchUseCase @Inject constructor(
    private val searchRepository: SearchRepository,
//    private val bookmarkRepository: BookmarkRepository
) {

    suspend operator fun invoke(query: String, page: Int): Flow<List<SearchModel>> {
        val imageFlow = searchRepository.searchImage(query, page)
        val videoFlow = searchRepository.searchVideo(query, page)

//        val entities = bookmarkRepository.getBookmarks()

        val sourceList = imageFlow.zip(videoFlow) { image, video ->
            image.documents.map { mapToThumbnail(it) } +
                    video.documents.map { mapToThumbnail(it) }
        }

        return sourceList
    }

    private fun mapToThumbnail(document: Document): SearchModel {
        return when (document) {
            is Document.Image -> {
                SearchModel(
                    thumbnail = document.thumbnailUrl,
                    dateTime = document.datetime,
//                    isBookmarked = entities.find { it.thumbnail == document.thumbnailUrl } != null
                )
            }
            is Document.Video -> {
                SearchModel(
                    thumbnail = document.thumbnail,
                    dateTime = document.datetime,
//                    isBookmarked = entities.find { it.thumbnail == document.thumbnail } != null
                )
            }
        }
    }
}