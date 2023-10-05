package com.example.cleanarchitectureshowcase.features.home.domain

import com.example.cleanarchitectureshowcase.features.home.data.StocksDTO
import com.example.cleanarchitectureshowcase.features.home.data.StocksPictureDTO
import com.example.cleanarchitectureshowcase.features.home.presentation.StocksDataUI

data class CombinedStocksDataDomain(
    val stocksList: List<StocksDTO>,
    val stockPictures: List<StocksPictureDTO>
) {
    fun toUI() = StocksDataUI(stocksList, stockPictures)
}
