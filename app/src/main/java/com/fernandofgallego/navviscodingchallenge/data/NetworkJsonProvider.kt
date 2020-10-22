package com.fernandofgallego.navviscodingchallenge.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fernandofgallego.navviscodingchallenge.domain.JsonProvider
import kotlinx.coroutines.*
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class NetworkJsonProvider(private val url: String): JsonProvider {
    override suspend fun getJson(): JsonProvider.JsonProviderResult {
        return withContext(Dispatchers.IO) {
            var connection: HttpURLConnection? = null
            var reader: BufferedReader? = null
            try {
                connection = URL(url).openConnection() as HttpsURLConnection
                connection.doInput = true
                connection.connectTimeout = 1000
                connection.readTimeout = 1000
                connection.connect()
                val inputStream = connection.inputStream
                reader = BufferedReader(InputStreamReader(inputStream))
                val contents = reader.readText()
                JsonProvider.JsonProviderResult.JsonResult(contents)
            } catch (e: Exception) {
                JsonProvider.JsonProviderResult.JsonError("Error connecting to server", e)
            } finally {
                try {
                    reader?.close()
                } catch (e: IOException) {
                    Log.e(javaClass.name, "Error closing the reader", e)
                }
                try {
                    connection?.disconnect()
                } catch (e: Exception) {
                    Log.e(javaClass.name, "Error closing the connection", e)
                }
            }
        }
    }
}