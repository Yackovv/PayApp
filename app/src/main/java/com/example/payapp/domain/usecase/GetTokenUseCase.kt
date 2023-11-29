package com.example.payapp.domain.usecase

import com.example.payapp.domain.entities.AuthState
import com.example.payapp.domain.entities.User
import com.example.payapp.domain.repository.PayRepository
import kotlinx.coroutines.flow.Flow

class GetTokenUseCase(private val repository: PayRepository) {

    suspend operator fun invoke(user: User): Flow<AuthState> {
        return repository.getToken(user)
    }
}