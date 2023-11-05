package com.example.cleanarchitectureshowcase.features.home.domain

import com.example.cleanarchitectureshowcase.features.home.data.StockInfoDTO
import com.example.cleanarchitectureshowcase.features.home.data.StockPictureDTO
import com.example.cleanarchitectureshowcase.features.home.presentation.StocksDataUI

data class CombinedStocksDataDomain(
    val stocksList: List<StockInfoDTO>,
    val stockPictures: List<StockPictureDTO>
) {
    fun toUI() = StocksDataUI(stocksList, stockPictures)
}
