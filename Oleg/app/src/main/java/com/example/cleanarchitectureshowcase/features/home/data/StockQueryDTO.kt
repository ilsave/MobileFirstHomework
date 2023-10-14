package com.example.cleanarchitectureshowcase.features.home.data

data class StockQueryDTO(
    val symbol: String,
    val name: String,
    val currency: String,
    val stockExchange: String,
    val exchangeShortName: String
)