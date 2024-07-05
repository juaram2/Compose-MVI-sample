package com.myapp.data.remote

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class Meta(
    @field:SerializedName("total_count") val totalCount: Int,
    @field:SerializedName("pageable_count") val pageableCount: Int,
    @field:SerializedName("is_end") val isEnd: Boolean
)

sealed class Document {
    data class Video(
        @field:SerializedName("title") val title: String,
        @field:SerializedName("play_time") val playTime: Int,
        @field:SerializedName("thumbnail") val thumbnail: String,
        @field:SerializedName("url") val url: String,
        @field:SerializedName("datetime") val datetime: LocalDateTime,
        @field:SerializedName("author") val author: String
    ): Document()

    data class Image(
        @field:SerializedName("collection") val collection: String,
        @field:SerializedName("thumbnail_url") val thumbnailUrl: String,
        @field:SerializedName("image_url") val imageUrl: String,
        @field:SerializedName("width") val width: Int,
        @field:SerializedName("height") val height: Int,
        @field:SerializedName("display_sitename") val displaySitename: String,
        @field:SerializedName("doc_url") val docUrl: String,
        @field:SerializedName("datetime") val datetime: LocalDateTime
    ): Document()
}

data class VideosModel(
    @field:SerializedName("meta") val meta: Meta,
    @field:SerializedName("documents") val documents: List<Document.Video>
)

data class ImagesModel(
    @field:SerializedName("meta") val meta: Meta,
    @field:SerializedName("documents") val documents: List<Document.Image>
)