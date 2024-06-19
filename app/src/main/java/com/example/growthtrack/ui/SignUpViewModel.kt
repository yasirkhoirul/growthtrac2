package com.example.growthtrack.ui

import androidx.lifecycle.ViewModel
import com.example.growthtrack.pindahan.UserRepository

class SignUpViewModel(private val repository: UserRepository): ViewModel() {

    fun userSignUp(name: String, email: String, password: String) =
        repository.userSignUp(name, email, password)
}