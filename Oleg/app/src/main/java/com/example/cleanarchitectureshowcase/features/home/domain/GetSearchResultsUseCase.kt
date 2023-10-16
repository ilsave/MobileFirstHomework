package com.example.cleanarchitectureshowcase.features.home.domain

import com.example.cleanarchitectureshowcase.core.domain.CoroutinesUseCase
import com.example.cleanarchitectureshowcase.features.home.presentation.StocksDataUI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class GetSearchResultsUseCase @Inject constructor(
    private val repository: DataRepository,
    private val dataPreparationHelper: DataPreparationHelper
): CoroutinesUseCase<String, StocksDataUI> {

    override suspend fun invoke(params: String): StocksDataUI {
        val searchResults = CoroutineScope(Dispatchers.IO).async {
            repository.getStocksByQuery(params, SEARCH_RESULTS_LIMIT, SEARCH_EXCHANGE)
        }.await()

        var searchResultsString = ""
        for (element in searchResults) {
            searchResultsString = searchResultsString.plus(element.symbol).plus(',')
        }
        searchResultsString = searchResultsString.dropLast(1)

        val stocksDataDeferred =
            CoroutineScope(Dispatchers.IO).async {
                repository.getStockInfo(searchResultsString)
            }

        val stocksPicturesDeferred =
            CoroutineScope(Dispatchers.IO).async {
                repository.getStockPicture(searchResultsString)
            }

        val stocksData = stocksDataDeferred.await()
        val stocksPictures = stocksPicturesDeferred.await()
        val result = dataPreparationHelper.combineForUI(stocksData, stocksPictures)
        return result.toUI()
    }

    companion object {
        const val SEARCH_RESULTS_LIMIT = 5
        const val SEARCH_EXCHANGE = "NASDAQ"
    }
}
