package com.example.payapp.data.network.models

import com.google.gson.annotations.SerializedName

data class TokenResponseDto(
    @SerializedName("response") val tokenDto: TokenDto?
)
