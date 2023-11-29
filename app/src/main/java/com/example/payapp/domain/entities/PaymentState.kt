package com.example.payapp.domain.entities

sealed class PaymentState{

    object Error: PaymentState()
    class PaymentListState(val list: List<Payment>): PaymentState()
    object Loading: PaymentState()
}
