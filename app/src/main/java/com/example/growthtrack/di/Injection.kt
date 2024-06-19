package com.example.growthtrack.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.growthtrack.api.ApiConfig
import com.example.growthtrack.pindahan.UserPreference
import com.example.growthtrack.pindahan.UserRepository

val Context.dataStore: DataStore<Preferences> by preferencesDataStore("growthtrack")

object Injection {
    fun provideDataStore(context: Context): UserPreference {
        return UserPreference.getInstance(context.dataStore)
    }

    fun provideRepository(context: Context): UserRepository {
        val preferences = UserPreference.getInstance(context.dataStore)
        val apiService = ApiConfig.getApiService()
        return UserRepository.getInstance(preferences, apiService)
    }
}
