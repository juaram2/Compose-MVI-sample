package com.myapp.ui.screen.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.time.delay
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import java.time.Duration

class MainViewModel: ContainerHost<MainContract.State, MainContract.Effect>, ViewModel() {
//    override fun createInitialState(): MainContract.State {
//        return MainContract.State(
//            MainContract.RandomNumberState.Idle,
//            MainContract.SecondState.Idle
//        )
//    }
//
//    override fun reduce(intent: MainContract.Intent) {
//        when (intent) {
//            is MainContract.Intent.OnRandomNumberClicked -> {
//                generateRandomNumber()
//            }
//            is MainContract.Intent.OnShowToastClicked -> {
//                setEffect { MainContract.Effect.ShowToast }
//            }
//            is MainContract.Intent.OnSecondClicked -> {
//
//            }
//        }
//    }

    /** 기능 구현 */
    fun generateRandomNumber() {
        viewModelScope.launch {
            intent {
                /** set state : Loading */
                reduce { state.copy(randomNumberState = MainContract.RandomNumberState.Loading) }
                delay(Duration.ofMillis(1000))
                try {
                    /** Implement func */
                    val random = (0..10).random()
                    if (random % 2 == 0) {
                        postSideEffect(MainContract.Effect.ShowToast)
                        throw RuntimeException("Number is even")
                    }
                    /** set state : Success */
                    reduce {
                        state.copy(randomNumberState = MainContract.RandomNumberState.Success(random))
                    }
                } catch (e: Exception) {
                    postSideEffect(MainContract.Effect.ShowToast)
                }
            }
//            setState { copy(randomNumberState = MainContract.RandomNumberState.Loading) }
//            delay(Duration.ofMillis(1000))
//            try {
//                /** Implement func */
//                val random = (0..10).random()
//                if (random % 2 == 0) {
//                    setState { copy(randomNumberState = MainContract.RandomNumberState.Fail) }
//                    throw RuntimeException("Number is even")
//                }
//                /** set state : Success */
//                setState { copy(randomNumberState = MainContract.RandomNumberState.Success(random)) }
//            } catch (e: Exception) {
//                setEffect { MainContract.Effect.ShowToast }
//            }
        }
    }

    override val container: Container<MainContract.State, MainContract.Effect>
        get() = container(MainContract.State(
            randomNumberState = MainContract.RandomNumberState.Idle,
            secondState = MainContract.SecondState.Idle
        ))
}