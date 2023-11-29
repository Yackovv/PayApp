package com.example.payapp.data.network

import com.example.payapp.data.network.models.PaymentsResponseDto
import com.example.payapp.data.network.models.TokenResponseDto
import com.example.payapp.domain.entities.User
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {

    @Headers("app-key: 12345", "v: 1")
    @POST("login")
    suspend fun getToken(@Body user: User): TokenResponseDto

    @Headers("app-key: 12345", "v: 1")
    @GET("payments")
    suspend fun getPayments(@Header("token") token: String): PaymentsResponseDto
}