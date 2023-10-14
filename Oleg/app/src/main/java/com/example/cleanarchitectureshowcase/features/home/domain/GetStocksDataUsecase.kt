package com.example.cleanarchitectureshowcase.features.home.domain

import com.example.cleanarchitectureshowcase.core.domain.CoroutinesUseCase
import com.example.cleanarchitectureshowcase.features.home.presentation.StocksDataUI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class GetStocksDataUsecase @Inject constructor(
    private val repository: DataRepository,
    private val dataPreparationHelper: DataPreparationHelper
): CoroutinesUseCase<String, StocksDataUI> {

    override suspend fun invoke(params: String): StocksDataUI {
        val stocksList = CoroutineScope(Dispatchers.IO).async {
            repository.getStocksList().filter { it.exchangeShortName == "NASDAQ" }.shuffled()
        }.await()

        val firstTenStocks = stocksList.subList(0, 10)
        var firstTenStocksString = ""
        for (element in firstTenStocks) {
            firstTenStocksString = firstTenStocksString.plus(element.symbol).plus(',')
        }
        firstTenStocksString = firstTenStocksString.dropLast(1)

        val stocksDataDeferred =
            coroutineScope {
                async(Dispatchers.IO) {
                    repository.getStockInfo(firstTenStocksString)
                }
            }

        val stocksPicturesDeferred =
            coroutineScope {
                async(Dispatchers.IO) {
                    repository.getStockPicture(firstTenStocksString)
                }
            }

        val stocksData = stocksDataDeferred.await()
        val stocksPictures = stocksPicturesDeferred.await()
        val result = dataPreparationHelper.combineForUI(stocksData, stocksPictures)
        return result.toUI()
    }
}
