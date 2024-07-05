package com.myapp.ui.screen.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.myapp.ui.components.CircleProgressBar
import com.myapp.ui.components.EmptyView
import com.myapp.ui.components.ErrorView
import com.myapp.ui.components.SearchBar


@Composable
fun SearchScreen() {
    val context = LocalContext.current
    val viewModel: SearchViewModel = viewModel()
    val uiState = viewModel.uiState.collectAsState().value
    val uiEffect = viewModel.effect.collectAsState(initial = SearchEffect.Loading(false)).value

    val query = remember { mutableStateOf("") }
    val page = remember { mutableStateOf(1) }

    val gridState = rememberLazyStaggeredGridState()

    //  paging
    val endReached by remember {
        derivedStateOf { gridState.layoutInfo.visibleItemsInfo.lastOrNull()?.index == gridState.layoutInfo.totalItemsCount - 1 }
    }

    LaunchedEffect(endReached) {
        if (endReached && query.value.isNotEmpty() && query.value.isNotBlank() && gridState.isScrollInProgress) {
            viewModel.handleEvent(SearchEvent.Search(query.value, page.value++))
            gridState.scrollToItem(0, 0)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(4.dp)
        ) {
            // search bar
            SearchBar(query = query, doSearch = {
                if (query.value.isNotEmpty() && query.value.isNotBlank()) {
                    page.value = 1
                    viewModel.handleEvent(SearchEvent.Search(query.value, page.value))
                }
            }) {}

            // search list
            when (uiState) {
                is SearchState.Success -> {
                    LazyVerticalStaggeredGrid(
                        columns = StaggeredGridCells.Fixed(2),
                        state = gridState,
                        contentPadding = PaddingValues(4.dp)
                    ) {
                        items(uiState.searchList) {
                            SearchItem(it)
                        }
                    }
                }

                SearchState.Empty -> {
                    EmptyView()
                }

                else -> {}
            }
        }

        when (uiEffect) {
            is SearchEffect.Loading -> {
                if (uiEffect.isLoading) {
                    CircleProgressBar()
                }
            }
            is SearchEffect.ShowToast -> {
                ErrorView(error = uiEffect.message)
            }
        }
    }
}