package com.example.cleanarchitectureshowcase.features.home.domain

import com.example.cleanarchitectureshowcase.features.home.data.StockDTO
import com.example.cleanarchitectureshowcase.features.home.data.StockInfoDTO
import com.example.cleanarchitectureshowcase.features.home.data.StockPictureDTO
import com.example.cleanarchitectureshowcase.features.home.data.StockQueryDTO

interface DataRepository {

    suspend fun getStocksList(): List<StockDTO>
    suspend fun getStockInfo(stock: String): List<StockInfoDTO>
    suspend fun getStockPicture(stock: String): List<StockPictureDTO>
    suspend fun getStocksByQuery(query: String, limit: Int, exchange: String): List<StockQueryDTO>
}
