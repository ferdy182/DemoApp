package com.fernandofgallego.demo.domain

interface JsonProvider {
    suspend fun getJson(): JsonProviderResult

    sealed class JsonProviderResult {
        data class JsonResult(val contents: String): JsonProviderResult()
        data class JsonError(val message: String, val cause: Exception? = null): JsonProviderResult()
    }
}