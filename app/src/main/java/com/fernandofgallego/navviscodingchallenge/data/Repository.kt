package com.fernandofgallego.navviscodingchallenge.data

import org.json.JSONArray
import org.json.JSONObject

class Repository(private val local: JsonProvider) {
    fun getNumbers(): List<Byte> {
        val jsonContent = readFile("numbers.json")
        val jsonObject = JSONObject(jsonContent)
        val array = jsonObject.getJSONArray("numbers")
        val list = mutableListOf<Byte>()
        for (i in 0.until(array.length())) {
            list.add(array.getInt(i).toByte())
        }
        return list
        // return listOf(4.toByte(), 150.toByte(), 12.toByte(), 21.toByte(), 136.toByte(), 3.toByte())
    }

    fun readFile(filename: String): String {
        return local.getJson(filename)
    }
}