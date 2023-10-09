package com.example.cleanarchitectureshowcase.features.home.domain

import com.example.cleanarchitectureshowcase.features.home.data.StockInfoDTO
import com.example.cleanarchitectureshowcase.features.home.data.StockPictureDTO

interface DataPreparationHelper {

    suspend fun combineForUI(stocks: List<StockInfoDTO>, pics: List<StockPictureDTO>): CombinedStocksDataDomain
}
