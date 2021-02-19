package com.fernandofgallego.demo.data

import android.util.Log
import com.fernandofgallego.demo.domain.JsonParser
import com.fernandofgallego.demo.domain.JsonProvider

class Repository(private val local: JsonProvider, private val remote: JsonProvider, private val jsonParser: JsonParser) {
    suspend fun getNumbers(): List<Byte> {
        val jsonContent = getContents()
        return jsonParser.parseNumbers(jsonContent)
    }

    private suspend fun getContents(): String {
        // TODO here should go some logic to choose between the remote and the local sources
        // also some cache that stores the remote and fetches again after some time
        when(val jsonRemote = remote.getJson()) {
            is JsonProvider.JsonProviderResult.JsonResult -> return jsonRemote.contents
            is JsonProvider.JsonProviderResult.JsonError -> {
                Log.e(javaClass.name, jsonRemote.message, jsonRemote.cause)
                when(val jsonLocal = local.getJson()) {
                    is JsonProvider.JsonProviderResult.JsonResult -> return jsonLocal.contents
                    is JsonProvider.JsonProviderResult.JsonError -> showError(jsonLocal.message, jsonLocal.cause)
                }
            }
        }
        throw RuntimeException("Unknown error")
    }

    private fun showError(message: String, e: Exception?) {
        Log.e(javaClass.name, message, e)
        //TODO show some UI error
    }
}