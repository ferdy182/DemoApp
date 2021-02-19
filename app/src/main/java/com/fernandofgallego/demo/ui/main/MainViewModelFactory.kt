package com.fernandofgallego.demo.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fernandofgallego.demo.data.Repository
import com.fernandofgallego.demo.domain.NumberProcessor

class MainViewModelFactory(private val repository: Repository, private val numberProcessor: NumberProcessor): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(repository, numberProcessor) as T
    }
}