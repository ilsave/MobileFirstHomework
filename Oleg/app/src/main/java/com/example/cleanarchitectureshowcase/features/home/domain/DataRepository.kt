package com.example.cleanarchitectureshowcase.features.home.domain

import com.example.cleanarchitectureshowcase.features.home.data.StockDTO
import com.example.cleanarchitectureshowcase.features.home.data.StockInfoDTO
import com.example.cleanarchitectureshowcase.features.home.data.StockPictureDTO

interface DataRepository {

    suspend fun getStocksList(): List<StockDTO>
    suspend fun getStockInfo(stock: String): List<StockInfoDTO>
    suspend fun getStockPicture(stock: String): List<StockPictureDTO>
}
