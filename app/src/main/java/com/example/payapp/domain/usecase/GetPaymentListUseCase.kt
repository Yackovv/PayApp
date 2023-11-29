package com.example.payapp.domain.usecase

import com.example.payapp.domain.entities.PaymentState
import com.example.payapp.domain.repository.PayRepository
import kotlinx.coroutines.flow.Flow

class GetPaymentListUseCase(private val repository: PayRepository) {

    suspend operator fun invoke(token: String): Flow<PaymentState> {
        return repository.getPaymentList(token)
    }
}