package com.example.cleanarchitectureshowcase.features.home.domain

import com.example.cleanarchitectureshowcase.features.home.data.StockInfoDTO
import com.example.cleanarchitectureshowcase.features.home.data.StockPictureDTO

class DataPreparationHelperImpl : DataPreparationHelper {

    override suspend fun combineForUI(stocks: List<StockInfoDTO>, pics: List<StockPictureDTO>): CombinedStocksDataDomain {
        return CombinedStocksDataDomain(stocksList = stocks, stockPictures = pics)
    }
}
