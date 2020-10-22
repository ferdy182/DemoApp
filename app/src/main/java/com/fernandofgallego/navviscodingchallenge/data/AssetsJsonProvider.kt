package com.fernandofgallego.navviscodingchallenge.data

import android.content.res.AssetManager
import android.util.Log
import com.fernandofgallego.navviscodingchallenge.domain.JsonProvider
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class AssetsJsonProvider(private val assetManager: AssetManager, private val filename: String):
    JsonProvider {
    override suspend fun getJson(): JsonProvider.JsonProviderResult {
        var reader: BufferedReader? = null
        return try {
            reader = BufferedReader(InputStreamReader(assetManager.open(filename)))
            val content = reader.readText()
            JsonProvider.JsonProviderResult.JsonResult(content)
        } catch (e: Exception) {
            JsonProvider.JsonProviderResult.JsonError("Error reading file: ${e.message}")
        } finally {
            try {
                reader?.close()
            } catch (e: IOException) {
                Log.e(javaClass.name, "Error closing file: ${e.message}")
            }
        }
    }
}