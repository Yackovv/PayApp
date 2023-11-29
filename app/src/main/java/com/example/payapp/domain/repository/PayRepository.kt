package com.example.payapp.domain.repository

import com.example.payapp.domain.entities.AuthState
import com.example.payapp.domain.entities.PaymentState
import com.example.payapp.domain.entities.User
import kotlinx.coroutines.flow.Flow

interface PayRepository {

    suspend fun getToken(user: User): Flow<AuthState>

    suspend fun getPaymentList(token: String): Flow<PaymentState>
}