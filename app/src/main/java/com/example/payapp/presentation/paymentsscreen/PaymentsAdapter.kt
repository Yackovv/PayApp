package com.example.payapp.presentation.paymentsscreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.payapp.databinding.ItemPaymentBinding
import com.example.payapp.domain.entities.Payment
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PaymentsAdapter(
    private val paymentList: List<Payment>
) : RecyclerView.Adapter<PaymentsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentsViewHolder {
        val binding = ItemPaymentBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PaymentsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PaymentsViewHolder, position: Int) {
        val binding = holder.binding
        val payment = paymentList[position]
        binding.tvDate.text = getCorrectDate(payment.created)
        binding.tvTitle.text = payment.title
        binding.tvAmount.text = payment.amount
    }

    override fun getItemCount(): Int {
        return paymentList.size
    }

    private fun getCorrectDate(time: Long): String {
        val date = Date(time * 1000)
        return SimpleDateFormat("dd MMMM yyyy, hh:mm", Locale.getDefault()).format(date)
    }
}