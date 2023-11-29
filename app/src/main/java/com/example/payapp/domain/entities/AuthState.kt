package com.example.payapp.domain.entities

sealed class AuthState{

    object Error : AuthState()
    class Auth(val token: String): AuthState()
    object NotAuth: AuthState()
    object Loading: AuthState()

}
