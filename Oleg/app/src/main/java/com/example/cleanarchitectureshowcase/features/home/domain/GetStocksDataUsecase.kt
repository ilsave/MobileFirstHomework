package com.example.cleanarchitectureshowcase.features.home.domain

import com.example.cleanarchitectureshowcase.core.domain.CoroutinesUseCase
import com.example.cleanarchitectureshowcase.features.home.presentation.StocksDataUI
import javax.inject.Inject

class GetStocksDataUsecase @Inject constructor(
    private val repository: DataRepository,
    private val businessLogicHelper: BusinessLogicHelper
): CoroutinesUseCase<String, StocksDataUI> {

    override suspend fun invoke(params: String): StocksDataUI {
        val stocksData = repository.getStocks()
        val stocksPictures = repository.getStocksPictures()

        val result = businessLogicHelper.combineForUI(stocksData, stocksPictures)
        return result.toUI()
    }
}
