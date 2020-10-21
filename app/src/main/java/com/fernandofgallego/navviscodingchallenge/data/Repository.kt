package com.fernandofgallego.navviscodingchallenge.data

class Repository(private val local: JsonProvider) {
    fun getNumbers(): List<Int> {
        return listOf()
    }

    fun readFile(filename: String): String {
        return local.getJson(filename)
    }
}