package com.myapp.ui.screen.search

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.myapp.domain.SearchModel
import java.time.format.DateTimeFormatter


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun SearchItem(item: SearchModel,/* saveImage: () -> Unit*/) {
    val dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
    val isBookmarked = remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .padding(4.dp)
            .clickable {
                if (!item.isBookmarked) {
                    isBookmarked.value = !isBookmarked.value
                }
            },
        border = BorderStroke(1.dp, Color.LightGray),
        elevation = CardDefaults.cardElevation(),
    ) {
        Column {
            Box {
                GlideImage(
                    model = item.thumbnail,
                    modifier = Modifier
                        .fillMaxSize()
                        .heightIn(100.dp),
                    contentScale = ContentScale.Crop,
                    contentDescription = "image"
                )

                Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.TopEnd) {
                    Icon(
                        if (isBookmarked.value) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "bookmark",
                        tint = Color.Cyan
                    )
                }

            }
            Text(
                text = dateFormat.format(item.dateTime),
                modifier = Modifier.padding(4.dp, 2.dp)
            )
        }
    }
}