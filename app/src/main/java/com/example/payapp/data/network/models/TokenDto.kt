package com.example.payapp.data.network.models

import com.google.gson.annotations.SerializedName

data class TokenDto(
    @SerializedName("token") val token: String
)
