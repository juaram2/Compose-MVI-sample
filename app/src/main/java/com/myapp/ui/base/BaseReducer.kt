package com.myapp.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class Reducer<S : UiState, E : UiEvent, F : UiEffect>(): ViewModel() {
    private val initialState : S by lazy { createInitialState() }
    abstract fun createInitialState() : S

    private val _uiState = MutableStateFlow(initialState)
    val uiState = _uiState.asStateFlow()

    private val _effect : Channel<F> = Channel()
    val effect = _effect.receiveAsFlow()

    fun sendEvent(event: E) {
        reduce(_uiState.value, event)
    }

    fun setState(newState: S) {
        _uiState.value = newState
    }

    fun setEffect(builder: () -> F) {
        val effectValue = builder()
        viewModelScope.launch { _effect.send(effectValue) }
    }

    abstract fun reduce(oldState: S, event: E)
}

//abstract class BaseReducer<Intent: UiIntent, State: UiState, Effect: UiEffect>: ViewModel() {
//    // Create Initial State of View
//    private val initialState : State by lazy { createInitialState() }
//    abstract fun createInitialState() : State
//
//    // Get Current State
//    val currentState: State
//        get() = uiState.value
//
//    private val _uiState : MutableStateFlow<State> = MutableStateFlow(initialState)
//    val uiState = _uiState.asStateFlow()
//
//    private val _intent : MutableSharedFlow<Intent> = MutableSharedFlow()
//    val intent = _intent.asSharedFlow()
//
//    private val _effect : Channel<Effect> = Channel()
//    val effect = _effect.receiveAsFlow()
//
//    init {
//        subscribeEvents()
//    }
//
//    /**
//     * Start listening to Intent
//     */
//    private fun subscribeEvents() {
//        viewModelScope.launch {
//            intent.collect {
//                reduce(it)
//            }
//        }
//    }
//
//    /**
//     * Handle each intent
//     */
//    abstract fun reduce(intent : Intent)
//
//
//    /**
//     * Set new Intent
//     */
//    fun setIntent(intent : Intent) {
//        val newIntent = intent
//        viewModelScope.launch { _intent.emit(newIntent) }
//    }
//
//
//    /**
//     * Set new Ui State
//     */
//    protected fun setState(reduce: State.() -> State) {
//        val newState = currentState.reduce()
//        _uiState.value = newState
//    }
//
//    /**
//     * Set new Effect
//     */
//    protected fun setEffect(builder: () -> Effect) {
//        val effectValue = builder()
//        viewModelScope.launch { _effect.send(effectValue) }
//    }
//}