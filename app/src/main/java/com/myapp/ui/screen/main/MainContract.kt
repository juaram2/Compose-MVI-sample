package com.myapp.ui.screen.main

import com.myapp.ui.base.UiEffect
import com.myapp.ui.base.UiEvent

class MainContract {

    /** 이벤트 정의 */
    sealed class Event : UiEvent {
        object OnRandomNumberClicked : Event()
        object OnShowToastClicked : Event()
        object OnSecondClicked : Event()
    }

    /** 상태 정의 */
    data class State(
        val randomNumberState: RandomNumberState,
        val secondState: SecondState
    )

    /** 상세 기능에 대한 상태 정의 */
    sealed class RandomNumberState {
        object Idle : RandomNumberState()
        object Fail : RandomNumberState()
        object Loading : RandomNumberState()
        data class Success(val number : Int) : RandomNumberState()
    }

    /** 상세 기능에 대한 상태 정의 */
    sealed class SecondState {
        object Idle : SecondState()
        object Fail : SecondState()
        object Loading : SecondState()
        data class Success(val number : Int) : SecondState()
    }

    /** 사이드 이펙트 정의 */
    sealed class Effect : UiEffect {
        object ShowToast : Effect()
        object ShowDialog: Effect()
    }

}