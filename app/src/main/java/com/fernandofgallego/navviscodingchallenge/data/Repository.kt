package com.fernandofgallego.navviscodingchallenge.data

import android.util.Log
import com.fernandofgallego.navviscodingchallenge.domain.JsonProvider
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.lang.Exception
import java.lang.RuntimeException

class Repository(private val local: JsonProvider, private val remote: JsonProvider) {
    suspend fun getNumbers(): List<Byte> {
        val list = mutableListOf<Byte>()
        try {
            val jsonContent = getContents()
            val jsonObject = JSONObject(jsonContent)
            val array = jsonObject.getJSONArray("numbers")

            for (i in 0.until(array.length())) {
                list.add(array.getInt(i).toByte())
            }
        } catch (e: JSONException) {
            Log.e(javaClass.name, "Error parsing JSON file: ${e.message}")
        } catch (e: IOException) {
            Log.e(javaClass.name, "Error reading file: ${e.message}")
        } catch (e: Exception) {
            Log.e(javaClass.name, "An error has occured: ${e.message}")
        }
        return list
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