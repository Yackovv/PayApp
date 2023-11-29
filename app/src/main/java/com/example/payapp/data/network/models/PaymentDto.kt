package com.example.payapp.data.network.models

import com.google.gson.annotations.SerializedName

data class PaymentDto(
    @SerializedName("id") val id: Int?,
    @SerializedName("title") val title: String?,
    @SerializedName("amount") val amount: String?,
    @SerializedName("created") val created: Long?
)
