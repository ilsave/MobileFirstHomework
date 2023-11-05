package com.example.cleanarchitectureshowcase.features.home.domain

import com.example.cleanarchitectureshowcase.core.domain.CoroutinesUseCase
import com.example.cleanarchitectureshowcase.features.home.presentation.StocksDataUI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class GetSearchResultsUseCase @Inject constructor(
    private val repository: DataRepository,
    private val dataPreparationHelper: DataPreparationHelper
): CoroutinesUseCase<String, StocksDataUI> {

    override suspend fun invoke(params: String): StocksDataUI = withContext(Dispatchers.IO) {
        val searchResults = CoroutineScope(Dispatchers.IO).async {
            repository.getStocksByQuery(params, SEARCH_RESULTS_LIMIT, SEARCH_EXCHANGE)
        }.await()

        val searchResultsString =
            searchResults.map { it.symbol }.reduceOrNull { acc, value -> "$acc,$value" }

        // if search results are empty we return empty lists
        if (searchResultsString.isNullOrEmpty()) {
            return@withContext StocksDataUI(stocks = emptyList(), pics = emptyList())
        }

        val stocksDataDeferred =
            async {
                repository.getStockInfo(searchResultsString)
            }

        val stocksPicturesDeferred =
            async {
                repository.getStockPicture(searchResultsString)
            }

        val stocksData = stocksDataDeferred.await()
        val stocksPictures = stocksPicturesDeferred.await()
        dataPreparationHelper.combineForUI(stocksData, stocksPictures).toUI()
    }

    companion object {
        const val SEARCH_RESULTS_LIMIT = 5
        const val SEARCH_EXCHANGE = "NASDAQ"
    }
}
