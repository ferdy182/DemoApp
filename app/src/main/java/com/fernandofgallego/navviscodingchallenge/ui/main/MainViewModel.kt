package com.fernandofgallego.navviscodingchallenge.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fernandofgallego.navviscodingchallenge.data.DataItem
import com.fernandofgallego.navviscodingchallenge.data.Repository
import com.fernandofgallego.navviscodingchallenge.domain.NumberProcessor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainViewModel(private val repository: Repository, private val numberProcessor: NumberProcessor) : ViewModel() {
    val data = MutableLiveData<List<DataItem>>()

    suspend fun update() {
        // all process is done asyncronous and we post the result to the live data
        withContext(Dispatchers.IO) {
            val numbers = repository.getNumbers()
            val items = numberProcessor.parseNumbers(numbers)
            data.postValue(numberProcessor.parseItems(items))
        }
    }


}