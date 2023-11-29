package com.example.payapp.presentation.paymentsscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.payapp.data.repository.PayRepositoryImpl
import com.example.payapp.domain.entities.PaymentState
import com.example.payapp.domain.usecase.GetPaymentListUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class PaymentsViewModel : ViewModel() {

    private val repository = PayRepositoryImpl()
    private val getPaymentListUseCase = GetPaymentListUseCase(repository)

    val state = MutableSharedFlow<PaymentState>(replay = 1)

    fun getPaymentList(token: String) {
        viewModelScope.launch {
            getPaymentListUseCase.invoke(token).collect {
                state.emit(it)
            }
        }
    }
}