package com.example.cleanarchitectureshowcase.features.home.domain

import com.example.cleanarchitectureshowcase.features.home.data.StocksDTO
import com.example.cleanarchitectureshowcase.features.home.data.StocksPictureDTO

interface BusinessLogicHelper {
    suspend fun doWork(params: DataDomain): DataDomain

    suspend fun combineForUI(stocks: List<StocksDTO>, pics: List<StocksPictureDTO>): CombinedStocksDataDomain
}
