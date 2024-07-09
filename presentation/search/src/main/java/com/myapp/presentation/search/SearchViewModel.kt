package com.myapp.presentation.search

import androidx.lifecycle.viewModelScope
import com.myapp.domain.model.SearchModel
import com.myapp.domain.usecase.SearchUseCase
import com.myapp.presentation.base.Reducer
import com.myapp.presentation.base.UiEffect
import com.myapp.presentation.base.UiEvent
import com.myapp.presentation.base.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchUseCase: SearchUseCase,
//    private val saveImageUseCase: SaveImageUseCase
) : Reducer<SearchState, SearchEvent, SearchEffect>() {

    override fun createInitialState(): SearchState {
        return SearchState.None
    }

    fun handleEvent(event: SearchEvent){
        when (event) {
            is SearchEvent.Search -> sendEvent(event)
        }
    }

    override fun reduce(oldState: SearchState, event: SearchEvent) {
        when (event) {
            is SearchEvent.Search -> {
                search(event.query, event.page)
            }
        }
    }

    private fun search(query: String, page: Int) {
        if (page > 15) {
            return
        }

        viewModelScope.launch {
            setEffect { SearchEffect.Loading(true) }
            delay(1000)

            searchUseCase.invoke(query, page)
                .catch { error ->
                    setEffect { SearchEffect.ShowToast(error.localizedMessage) }
                }
                .collect { searchList ->
                    if (searchList.isEmpty()) {
                        setState(SearchState.Empty)
                        return@collect
                    }

                    val convertList = searchList.map {
                        SearchModel(
                            it.thumbnail, it.dateTime
                        )
                    }.sortedByDescending { it.dateTime }
                    setState(SearchState.Success(convertList))
                }

            setEffect { SearchEffect.Loading(false) } /** 로딩 false 처리 */
        }
    }
}

sealed class SearchState : UiState {
    object None : SearchState()
    object Empty : SearchState()
    data class Success(val searchList: List<SearchModel>) : SearchState()
}

sealed class SearchEvent : UiEvent {
    data class Search(val query: String, val page: Int) : SearchEvent()
}

sealed class SearchEffect : UiEffect {
    data class Loading(val isLoading: Boolean) : SearchEffect()
    data class ShowToast(val message: String) : SearchEffect()
}