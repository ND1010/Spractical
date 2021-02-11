package com.dhruv.apps.historyapp.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.lifecycle.ViewModelProvider
import com.dhruv.apps.historyapp.R
import com.dhruv.apps.historyapp.databinding.ActivityLoginBinding
import com.dhruv.apps.historyapp.ui.ui.user.UserActivity
import com.dhruv.apps.historyapp.utils.observe
import com.dhruv.apps.historyapp.viewmodels.LoginViewModel
import com.fizhu.bikeappconcept.data.pref.PrefDataSource
import com.google.android.material.textfield.TextInputLayout
import com.jakewharton.rxbinding3.widget.textChangeEvents
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*
import java.util.concurrent.TimeUnit

class LoginActivity : AppCompatActivity() {

    private var isUsernameValid = false
    private var isPasswordValid = false
    private lateinit var viewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding
    private val compositeDisposable by lazy {
        CompositeDisposable()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        binding.viewModel = this.viewModel
        binding.lifecycleOwner = this

        onInit()
    }

    private fun onInit() {
        binding.btnLogin.isEnabled = false
        binding.btnLogin.alpha = 0.5F
        initValidation()
        tvSignup.setOnClickListener {
            startActivity(Intent(this@LoginActivity,SignUpActivity::class.java))
        }
        val userPref = PrefDataSource()
        if (userPref.getIsLogin()){
            startActivity(Intent(this@LoginActivity, UserActivity::class.java))
            finish()
        }
        observe(viewModel.loginToApp){
            if (it) {
                Toast.makeText(this@LoginActivity,"Login Success",Toast.LENGTH_LONG).show()
                startActivity(Intent(this@LoginActivity,UserActivity::class.java))
                finish()
            } else {
                Toast.makeText(this@LoginActivity,"Invalid User name and password",Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun initValidation() {
        validation(binding.inputEdtMobile, binding.textInputLayoutLoginMobile, 0)
        validation(binding.edtPassword, binding.textInputLayoutLoginPassword, 1)
    }

    private fun validation(
        et: AppCompatEditText,
        til: TextInputLayout,
        type: Int
    ) {
        val observable = et.textChangeEvents()
        compositeDisposable.add(observable
            .skip(1)
            .debounce(400, TimeUnit.MILLISECONDS)
            .map { it.text }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                if (it.length < 6) {
                    til.isHelperTextEnabled = true
                    til.helperText = getString(R.string.invalid_validation)
                    when (type) {
                        0 -> {
                            isUsernameValid = false
                        }
                        1 -> {
                            isPasswordValid = false
                        }
                    }
                } else {
                    when (type) {
                        0 -> {
                            if (android.util.Patterns.EMAIL_ADDRESS.matcher(it.trim()).matches()){
                                isUsernameValid = true
                                til.isHelperTextEnabled = false
                            }else{
                                isUsernameValid = false
                                til.isHelperTextEnabled = true
                                til.helperText = "Invalid Email Address"
                            }
                        }
                        1 -> {
                            isPasswordValid = true
                            til.isHelperTextEnabled = false
                        }
                    }
                }
            }
            .subscribe {
                binding.btnLogin.isEnabled = isUsernameValid && isPasswordValid
                binding.btnLogin.alpha = if (isUsernameValid && isPasswordValid) 1F else 0.5F
            }
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}