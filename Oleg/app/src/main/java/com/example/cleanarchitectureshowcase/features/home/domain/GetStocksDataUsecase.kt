package com.example.cleanarchitectureshowcase.features.home.domain

import com.example.cleanarchitectureshowcase.core.domain.CoroutinesUseCase
import com.example.cleanarchitectureshowcase.features.home.presentation.StocksDataUI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetStocksDataUsecase @Inject constructor(
    private val repository: DataRepository,
    private val dataPreparationHelper: DataPreparationHelper
): CoroutinesUseCase<String, StocksDataUI> {

    override suspend fun invoke(params: String): StocksDataUI = withContext(Dispatchers.IO) {
        val stocksList = async {
            repository.getStocksList().filter { it.exchangeShortName == DEFAULT_EXCHANGE }.shuffled()
        }.await()
        println("got getstockslist()")

        val firstTenStocks = stocksList.subList(0, 10)
        println("got sublist(0 10)")
        val firstTenStocksAsString =
            firstTenStocks.map { it.symbol }.reduceOrNull { acc, value -> "$acc,$value" }
        println("reduced")
        // if there are no stocks we return empty lists
        if (firstTenStocksAsString.isNullOrEmpty()) {
            return@withContext StocksDataUI(emptyList(), emptyList())
        }
        println("check for null is complete")
        val stocksDataDeferred = async {
            repository.getStockInfo(firstTenStocksAsString)
        }
        val stocksPicturesDeferred = async {
            repository.getStockPicture(firstTenStocksAsString)
        }
        val stocksData = stocksDataDeferred.await()
        println("awaited stocksdata")
        val stocksPictures = stocksPicturesDeferred.await()
        println("awaited stockspictures")
        val stocks = dataPreparationHelper.combineForUI(stocksData, stocksPictures)
        println("combined for ui")
        stocks.toUI()
    }

    companion object {
        const val DEFAULT_EXCHANGE = "NASDAQ"
    }
}
