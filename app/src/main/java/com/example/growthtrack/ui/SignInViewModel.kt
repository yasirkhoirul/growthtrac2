package com.example.growthtrack.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.growthtrack.pindahan.UserModel
import com.example.growthtrack.pindahan.UserRepository
import kotlinx.coroutines.launch

class SignInViewModel(private val repository: UserRepository) : ViewModel() {
    fun userSignIn(email: String, password: String) =
        repository.userSignIn(email, password)

    fun saveUserData(userData: UserModel) {
        viewModelScope.launch {
            repository.saveUserData(userData)
        }
    }
}