package com.example.payapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.payapp.R
import com.example.payapp.presentation.loginscreen.LoginFragment
import com.example.payapp.presentation.paymentsscreen.PaymentsFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val token = getSharedPreferences(FILE_NAME, MODE_PRIVATE).getString(FILE_NAME, null)
        val fragment = if (token == null) {
            LoginFragment.newInstance()
        } else {
            PaymentsFragment.newInstance(token)
        }
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}