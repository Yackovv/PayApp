package com.example.payapp.data.mapper

import com.example.payapp.data.network.models.PaymentDto
import com.example.payapp.domain.entities.Payment

object Mapper {

    private const val NO_DATA = "no_data"

    private fun mapPaymentDtoToEntity(dto: PaymentDto) = Payment(
        id = dto.id ?: -1,
        title = if (dto.title.isNullOrEmpty()) NO_DATA else dto.title,
        amount = if (dto.amount.isNullOrEmpty()) NO_DATA else dto.amount,
        created = dto.created ?: -1
    )

    fun mapPaymentListDtoToEntity(dto: List<PaymentDto>): List<Payment> {
        val result = mutableListOf<Payment>()
        for (paymentDto in dto) {
            val payment = mapPaymentDtoToEntity(paymentDto)
            if (payment.id >= 0 && payment.created > 0) {
                result.add(payment)
            }
        }
        return result.toList()
    }
}