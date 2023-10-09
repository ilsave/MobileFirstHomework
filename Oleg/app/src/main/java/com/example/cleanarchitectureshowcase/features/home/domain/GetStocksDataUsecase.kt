package com.example.cleanarchitectureshowcase.features.home.domain

import com.example.cleanarchitectureshowcase.core.domain.CoroutinesUseCase
import com.example.cleanarchitectureshowcase.features.home.data.StockInfoDTO
import com.example.cleanarchitectureshowcase.features.home.data.StockPictureDTO
import com.example.cleanarchitectureshowcase.features.home.presentation.StocksDataUI
import javax.inject.Inject

class GetStocksDataUsecase @Inject constructor(
    private val repository: DataRepository,
    private val dataPreparationHelper: DataPreparationHelper
): CoroutinesUseCase<String, StocksDataUI> {

    override suspend fun invoke(params: String): StocksDataUI {
        // this is a shuffled list of all stocks i don't really know where to store this
        val stocksList = repository.getStocksList().filter { it.exchangeShortName == "NASDAQ" }.shuffled()

        val firstTenStocks = stocksList.subList(0, 10)

        val stocksData = mutableListOf<StockInfoDTO>()
        val stocksPictures = mutableListOf<StockPictureDTO>()
        for (element in firstTenStocks) {
            stocksData.add(repository.getStockInfo(element.symbol).get(0))
            stocksPictures.add(repository.getStockPicture(element.symbol).get(0))
        }

        val result = dataPreparationHelper.combineForUI(stocksData, stocksPictures)
        return result.toUI()
    }
}
