package com.example.cleanarchitectureshowcase.features.home.presentation

import com.example.cleanarchitectureshowcase.features.home.data.StocksDTO
import com.example.cleanarchitectureshowcase.features.home.data.StocksPictureDTO

data class StocksDataUI(
    val stocks: List<StocksDTO>,
    val pics: List<StocksPictureDTO>
)
