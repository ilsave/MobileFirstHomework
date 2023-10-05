package com.example.cleanarchitectureshowcase.features.home.domain

import com.example.cleanarchitectureshowcase.features.home.data.StocksDTO
import com.example.cleanarchitectureshowcase.features.home.data.StocksPictureDTO

class BusinessLogicHelperImpl : BusinessLogicHelper {

    override suspend fun combineForUI(stocks: List<StocksDTO>, pics: List<StocksPictureDTO>): CombinedStocksDataDomain {
        return CombinedStocksDataDomain(stocksList = stocks, stockPictures = pics)
    }
}
