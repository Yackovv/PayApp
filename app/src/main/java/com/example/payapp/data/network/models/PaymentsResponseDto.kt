package com.example.payapp.data.network.models

import com.google.gson.annotations.SerializedName

data class PaymentsResponseDto(
    @SerializedName("response") val paymentDtoList: List<PaymentDto>?
)
