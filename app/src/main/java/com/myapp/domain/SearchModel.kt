package com.myapp.domain

import java.time.LocalDateTime

data class SearchModel(
    val thumbnail: String,
    val dateTime: LocalDateTime,
    val isBookmarked: Boolean = false
)