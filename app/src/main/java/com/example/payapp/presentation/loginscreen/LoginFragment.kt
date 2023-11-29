package com.example.payapp.presentation.loginscreen

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
import com.example.payapp.databinding.FragmentLoginBinding
import com.example.payapp.domain.entities.AuthState
import com.example.payapp.domain.entities.User
import com.example.payapp.presentation.FILE_NAME
import com.example.payapp.presentation.paymentsscreen.PaymentsFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding: FragmentLoginBinding
        get() = _binding ?: throw RuntimeException("FragmentLoginBinding is null")
    private val viewModel by lazy {
        ViewModelProvider(this)[LoginViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogin.setOnClickListener {

            val login = binding.etLogin.text.toString()
            val password = binding.etPassword.text.toString()
            val user = User(login, password)
            viewModel.getToken(user)
            lifecycleScope.launch {
                viewModel.authState.collect {
                    when (it) {
                        is AuthState.Auth -> {
                            saveToken(it.token)
                            requireActivity().supportFragmentManager
                                .beginTransaction()
                                .replace(
                                    R.id.fragment_container,
                                    PaymentsFragment.newInstance(it.token)
                                )
                                .commit()
                        }

                        AuthState.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }

                        AuthState.Error -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(context, "No internet connection", Toast.LENGTH_SHORT)
                                .show()
                        }

                        AuthState.NotAuth -> {
                            binding.progressBar.visibility = View.GONE
                            binding.tilLogin.error = getString(R.string.wrong_login_or_password)
                            binding.tilPassword.error = getString(R.string.wrong_login_or_password)
                            delay(5_000)
                            binding.tilLogin.error = null
                            binding.tilPassword.error = null
                        }
                    }
                }
            }
        }
    }

    private fun saveToken(token: String) {
        val editor = requireActivity().getSharedPreferences(
            FILE_NAME,
            AppCompatActivity.MODE_PRIVATE
        ).edit()
        editor.putString(FILE_NAME, token).apply()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun newInstance(): LoginFragment {
            return LoginFragment()
        }
    }
}