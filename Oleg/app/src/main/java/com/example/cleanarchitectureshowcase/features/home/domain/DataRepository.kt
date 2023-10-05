package com.example.cleanarchitectureshowcase.features.home.domain

import com.example.cleanarchitectureshowcase.features.home.data.StocksDTO
import com.example.cleanarchitectureshowcase.features.home.data.StocksPictureDTO

interface DataRepository {
    suspend fun getStocks(): List<StocksDTO>

    suspend fun getStocksPictures(): List<StocksPictureDTO>
}
