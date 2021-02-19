package com.fernandofgallego.demo.data

sealed class DataItem {
    data class Section(val title: String): DataItem()
    data class Item(val title: String, val checked: Boolean): DataItem()
}