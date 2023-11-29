package com.example.payapp.data.repository

import com.example.payapp.data.mapper.Mapper
import com.example.payapp.data.network.ApiFactory
import com.example.payapp.domain.entities.AuthState
import com.example.payapp.domain.entities.PaymentState
import com.example.payapp.domain.entities.User
import com.example.payapp.domain.repository.PayRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.retry

class PayRepositoryImpl : PayRepository {

    private val apiService = ApiFactory.apiService

    override suspend fun getToken(user: User): Flow<AuthState> = flow {
        apiService.getToken(user).tokenDto?.let {
            emit(AuthState.Auth(it.token))
        } ?: emit(AuthState.NotAuth)
    }.onStart {
        emit(AuthState.Loading)
    }.retry(3) {
        delay(TIME_OUT_MILLIS)
        true
    }.catch {
        emit(AuthState.Error)
    }

    override suspend fun getPaymentList(token: String): Flow<PaymentState> = flow {
        apiService.getPayments(token).paymentDtoList?.let {
            val paymentList = Mapper.mapPaymentListDtoToEntity(it)
            emit(PaymentState.PaymentListState(paymentList))
        } ?: emit(PaymentState.Loading)
    }.onStart {
        emit(PaymentState.Loading)
    }.retry(3) {
        delay(TIME_OUT_MILLIS)
        true
    }.catch {
        emit(PaymentState.Error)
    }

    companion object{
        private const val TIME_OUT_MILLIS = 3_000L
    }
}