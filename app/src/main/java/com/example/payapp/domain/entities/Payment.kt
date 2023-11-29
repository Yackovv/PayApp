package com.example.payapp.domain.entities

data class Payment(
    val id: Int,
    val title: String,
    val amount: String,
    val created: Long
)
