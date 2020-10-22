package com.fernandofgallego.navviscodingchallenge.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fernandofgallego.navviscodingchallenge.data.Item
import com.fernandofgallego.navviscodingchallenge.data.Repository
import com.fernandofgallego.navviscodingchallenge.domain.JsonProvider
import com.fernandofgallego.navviscodingchallenge.domain.NumberProcessor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.experimental.and

class MainViewModel(private val repository: Repository) : ViewModel() {
    val data = MutableLiveData<List<DataItem>>()
    private val numberProcessor = NumberProcessor()

    suspend fun update() {
        withContext(Dispatchers.IO) {
            val numbers = repository.getNumbers()
            val items = numberProcessor.parseNumbers(numbers)
            data.postValue(numberProcessor.parseItems(items))
        }
    }


}