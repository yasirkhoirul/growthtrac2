package com.example.growthtrack

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.growthtrack.pindahan.UserModel
import com.example.growthtrack.pindahan.UserRepository
import kotlinx.coroutines.launch

class MainViewModel(private val repository: UserRepository): ViewModel() {
    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }

    fun getUserModel(): LiveData<UserModel> {
        return repository.getUserData()
    }
}