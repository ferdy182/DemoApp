package com.fernandofgallego.navviscodingchallenge.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fernandofgallego.navviscodingchallenge.data.Repository

class MainViewModel(private val repository: Repository) : ViewModel() {
    val data = MutableLiveData<List<DataItem>>()

    fun parseNumbers(list: List<Int>) {
        data.postValue(listOf(DataItem.Section("section 1"),
            DataItem.Item("item 1", true),
            DataItem.Item("item 2", false),
            DataItem.Section("section 2"),
            DataItem.Item("item 1", true)
        ))
    }

    fun getNumbers(): List<Int> {
        return repository.getNumbers()
    }

}