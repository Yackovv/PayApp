package com.example.payapp.presentation.paymentsscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.payapp.R
import com.example.payapp.databinding.FragmentPaymentsBinding
import com.example.payapp.domain.entities.PaymentState
import com.example.payapp.presentation.FILE_NAME
import com.example.payapp.presentation.loginscreen.LoginFragment
import kotlinx.coroutines.launch

class PaymentsFragment : Fragment() {

    private var _binding: FragmentPaymentsBinding? = null
    private val binding: FragmentPaymentsBinding
        get() = _binding ?: throw RuntimeException("FragmentPaymentsBinding is null")
    private val viewModel by lazy {
        ViewModelProvider(this)[PaymentsViewModel::class.java]
    }
    private var token: String = EMPTY_TOKEN

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseParams()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPaymentsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolBar.setOnMenuItemClickListener {
            deleteToken()
            requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, LoginFragment.newInstance())
                .commit()
            true
        }


        viewModel.getPaymentList(token)
        lifecycleScope.launch {
            viewModel.state.collect { paymentState ->
                when (paymentState) {
                    PaymentState.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(context, "No internet connection", Toast.LENGTH_SHORT).show()
                    }
                    PaymentState.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }

                    is PaymentState.PaymentListState -> {
                        binding.progressBar.visibility = View.GONE
                        val adapter = PaymentsAdapter(paymentState.list)
                        binding.rvPayments.adapter = adapter
                    }
                }
            }
        }
    }

    private fun deleteToken() {
        val editor = requireActivity().getSharedPreferences(
            FILE_NAME,
            AppCompatActivity.MODE_PRIVATE
        ).edit()
        editor.putString(FILE_NAME, null).apply()
    }

    private fun parseParams() {
        val args = requireArguments()
        if (!args.containsKey(TOKEN_KEY)) {
            throw RuntimeException("Token not found")
        }
        val temp = args.getString(TOKEN_KEY)
        if (temp.isNullOrEmpty()) {
            throw RuntimeException("Token is null or empty")
        }
        token = temp
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {

        private const val TOKEN_KEY = "token"
        private const val EMPTY_TOKEN = ""

        fun newInstance(token: String): PaymentsFragment {
            return PaymentsFragment().apply {
                arguments = Bundle().apply {
                    putString(TOKEN_KEY, token)
                }
            }
        }
    }
}