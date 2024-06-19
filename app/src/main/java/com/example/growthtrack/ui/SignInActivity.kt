package com.example.growthtrack.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.growthtrack.MainActivity
import com.example.growthtrack.ViewModelFactory
import com.example.growthtrack.databinding.ActivitySigninBinding
import com.example.growthtrack.pindahan.ApiResult
import com.example.growthtrack.pindahan.UserModel

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySigninBinding
    private lateinit var signInViewModel: SignInViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        setupViewModel()
//        playAnimation()

        binding.tvofSignin.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
        binding.btnSignin.setOnClickListener {
            val email = binding.emailSignin.text.toString()
            val password = binding.cvPassSignin.text.toString()
            if (email.isEmpty()) {
                binding.emailSignin.error = "Input Email"
            } else if (password.isEmpty()) {
                binding.cvPassSignin.error = "Input Password"
            } else if (password.length < 8) {
                binding.cvPassSignin.requestFocus()
            } else {
                signInViewModel.userSignIn(email, password).observe(this) {
                    when (it) {
                        is ApiResult.Success -> {
                            showLoad(false)
                            val response = it.data
                            Log.d("login", response.data.email)
                            saveUserData(
                                UserModel(
                                    response.data.email,
                                    response.data.uid,
                                    response.token // Save the token here
                                )
                            )
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            finishAffinity()
                        }
                        is ApiResult.Loading -> showLoad(true)
                        is ApiResult.Error -> {
                            showDialog(FAIL)
                            showLoad(false)
                        }
                    }
                }
            }
        }
    }

    private fun setupViewModel() {
        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        signInViewModel = ViewModelProvider(this, factory)[SignInViewModel::class.java]
    }

    companion object {
        const val FAIL = "fail"
        const val SUCCESS = "success"
    }

    private fun saveUserData(userData: UserModel) {
        signInViewModel.saveUserData(userData)
    }

    private fun showLoad(isLoading: Boolean) {
        binding.progressBarLogin.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showDialog(mode: String) {
        val builder = AlertDialog.Builder(this)
        if (mode == FAIL) {
            builder.setTitle("Login Failed")
            builder.setMessage("Login failed, please try again")
            builder.setPositiveButton(android.R.string.ok) { _, _ -> }
            builder.show()
        } else if (mode == SUCCESS) {
            builder.setTitle("Login Success")
            builder.setMessage("Login successful, continue to the main screen!")
            builder.setPositiveButton(android.R.string.ok) { _, _ ->
                goToMain()
            }
            builder.show()
        }
    }

    private fun goToMain() {
        Toast.makeText(this, "Login success", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
