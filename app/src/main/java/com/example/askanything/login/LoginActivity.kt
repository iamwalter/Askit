package com.example.askanything.login

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.example.askanything.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)
        initViewModel()
        initViews()
    }

    private fun initViewModel() {
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        loginViewModel.email.observe(this, Observer { email ->
            loginViewModel.password.observe(this, Observer { password ->
                btnRegister.isEnabled = (email.isNotBlank() && password.isNotBlank())
                btnLogin.isEnabled = btnRegister.isEnabled
            })
        })

        loginViewModel.error.observe(this, Observer { error ->
            tvErrorMessage.visibility = View.VISIBLE
            tvErrorMessage.text = error
        })

        loginViewModel.success.observe(this, Observer { success ->
            if (success) {
                loginViewModel.reset()
                finish()
            }
        })
    }

    private fun initViews() {
        etEmail.addTextChangedListener {
            loginViewModel.email.value = it.toString()
        }

        etPassword.addTextChangedListener {
            loginViewModel.password.value = it.toString()
        }

        btnLogin.setOnClickListener {
            loginViewModel.login()
        }

        btnRegister.setOnClickListener {
            loginViewModel.createAccount()
        }
    }
}
