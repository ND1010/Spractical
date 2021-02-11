package com.dhruv.apps.historyapp.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.lifecycle.ViewModelProvider
import com.dhruv.apps.historyapp.R
import com.dhruv.apps.historyapp.databinding.ActivitySignupBinding
import com.dhruv.apps.historyapp.utils.observe
import com.dhruv.apps.historyapp.viewmodels.SignupViewModel
import com.google.android.material.textfield.TextInputLayout
import com.jakewharton.rxbinding3.widget.textChangeEvents
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class SignUpActivity : AppCompatActivity() {

    private var isUsernameValid = false
    private var isPasswordValid = false
    private var isCPasswordValid = false
    private lateinit var viewModel: SignupViewModel
    private lateinit var binding: ActivitySignupBinding
    private val compositeDisposable by lazy {
        CompositeDisposable()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(SignupViewModel::class.java)
        binding.viewModel = this.viewModel
        binding.lifecycleOwner = this

        onInit()
    }

    private fun onInit() {
        binding.btnSignup.isEnabled = false
        binding.btnSignup.alpha = 0.5F
        initValidation()
        observe(viewModel.signupToApp) {
            if (it) {
                Toast.makeText(this@SignUpActivity, "SignUp Success", Toast.LENGTH_LONG).show()
                finish()
            } else {
                Toast.makeText(
                    this@SignUpActivity,
                    "Invalid User name and password",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun initValidation() {
        validation(binding.inputEdtMobile, binding.textInputLayoutLoginMobile, 0)
        validation(binding.edtPassword, binding.textInputLayoutLoginPassword, 1)
        validation(binding.edtPasswordC, binding.textInputLayoutLoginPasswordC, 2)
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
                        2 -> {
                            isCPasswordValid = false
                        }
                    }
                } else {
                    if (android.util.Patterns.EMAIL_ADDRESS.matcher(binding.inputEdtMobile.text?.trim()).matches()) {
                        isUsernameValid = true
                        til.isHelperTextEnabled = false
                    } else {
                        isUsernameValid = false
                        til.isHelperTextEnabled = true
                        til.helperText = "Invalid Email Address"
                    }
                    if (binding.edtPassword.text.toString().trim().isNotEmpty()){
                        isPasswordValid = true
                        til.isHelperTextEnabled = false
                    }else{
                        isPasswordValid = false
                        til.isHelperTextEnabled = true
                    }

                    if (binding.edtPassword.text.toString().trim().isEmpty()
                        || binding.edtPasswordC.text.toString().trim()
                        != binding.edtPassword.text.toString().trim()
                    ) {
                        isUsernameValid = false
                        til.isHelperTextEnabled = true
                        til.helperText = "Password does not match"
                    } else {
                        isCPasswordValid = true
                        til.isHelperTextEnabled = false
                    }
                }
            }
            .subscribe {
                binding.btnSignup.isEnabled = isUsernameValid && isPasswordValid
                binding.btnSignup.alpha = if (isUsernameValid && isPasswordValid) 1F else 0.5F
            }
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}