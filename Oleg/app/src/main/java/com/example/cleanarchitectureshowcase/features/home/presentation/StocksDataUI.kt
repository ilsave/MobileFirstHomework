package com.example.cleanarchitectureshowcase.features.home.presentation

import com.example.cleanarchitectureshowcase.features.home.data.StockInfoDTO
import com.example.cleanarchitectureshowcase.features.home.data.StockPictureDTO

data class StocksDataUI(
    val stocks: List<StockInfoDTO>,
    val pics: List<StockPictureDTO>
)
