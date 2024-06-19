package com.example.growthtrack.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.growthtrack.pindahan.DataItem
import com.example.growthtrack.pindahan.UserRepository

class ArticleViewModel(private val repo: UserRepository) : ViewModel() {
    fun getListArticle(): LiveData<PagingData<DataItem>> {
        return repo.getArticle().cachedIn(viewModelScope)
    }
}