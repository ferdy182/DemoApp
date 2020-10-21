package com.fernandofgallego.navviscodingchallenge.data

import android.util.Log
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.lang.Exception

class Repository(private val local: JsonProvider, private val remote: JsonProvider) {
    fun getNumbers(): List<Byte> {
        val list = mutableListOf<Byte>()
        try {
            val jsonContent = readFile()
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

    fun readFile(): String {
        return local.getJson()
    }
}