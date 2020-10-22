package com.fernandofgallego.navviscodingchallenge.domain

import android.util.Log
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.lang.Exception

class JsonParser {
    fun parseNumbers(jsonContent: String): List<Byte> {
        val list = mutableListOf<Byte>()
        try {
            val jsonObject = JSONObject(jsonContent)
            val array = jsonObject.getJSONArray("numbers")

            for (i in 0.until(array.length())) {
                val input = array.get(i)
                if(validateInput(input))
                    list.add((input as Int).toByte())
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

    private fun validateInput(input: Any): Boolean = input !is Int
}