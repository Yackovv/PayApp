package com.example.payapp.presentation.loginscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.payapp.data.repository.PayRepositoryImpl
import com.example.payapp.domain.entities.AuthState
import com.example.payapp.domain.entities.User
import com.example.payapp.domain.usecase.GetTokenUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val repository = PayRepositoryImpl()
    private val getTokenUseCase = GetTokenUseCase(repository)

    val authState = MutableSharedFlow<AuthState>(replay = 1)

    fun getToken(user: User) {
        viewModelScope.launch {
            getTokenUseCase.invoke(user).collect {
                authState.emit(it)
            }
        }
    }
}