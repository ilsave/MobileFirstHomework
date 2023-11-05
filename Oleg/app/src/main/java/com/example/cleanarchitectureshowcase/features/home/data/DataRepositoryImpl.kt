package com.example.cleanarchitectureshowcase.features.home.data

import com.example.cleanarchitectureshowcase.features.home.domain.DataRepository

class DataRepositoryImpl(
    private val api: ServerDataApi
): DataRepository {

    override suspend fun getStocksList(): List<StockDTO> {
        return api.getAllStocks()
    }

    override suspend fun getStockInfo(stock: String): List<StockInfoDTO> {
        return api.getStockFullInfo(stock)
    }

    override suspend fun getStockPicture(stock: String): List<StockPictureDTO> {
        return api.getStockPicture(stock)
    }

    override suspend fun getStocksByQuery(query: String, limit: Int, exchange: String): List<StockQueryDTO> {
        return api.getStocksByQuery(query, limit, exchange)
    }
}
