package com.fernandofgallego.navviscodingchallenge.data

import android.content.res.AssetManager
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class AssetsJsonProvider(private val assetManager: AssetManager, private val filename: String): JsonProvider {
    override fun getJson(): String {
        return try {
            val reader = BufferedReader(InputStreamReader(assetManager.open(filename)))
            val content = reader.readText()
            reader.close()
            content
        } catch (e: IOException) {
            "{}"
        }
    }
}