package com.example.cleanarchitectureshowcase.features.home.data

import com.example.cleanarchitectureshowcase.features.home.domain.DataRepository

class DataRepositoryImpl(
    private val api: ServerDataApi
): DataRepository {

    override suspend fun getStocksList(): List<StockDTO> {
        val response = api.getAllStocks()
        return response
    }

    override suspend fun getStockInfo(stock: String): List<StockInfoDTO> {
        val response = api.getStockFullInfo(stock)
        return response
    }

    override suspend fun getStockPicture(stock: String): List<StockPictureDTO> {
        val response = api.getStockPicture(stock)
        return response
    }

    override suspend fun getStocksByQuery(query: String, limit: Int, exchange: String): List<StockQueryDTO> {
        val response = api.getStocksByQuery(query, limit, exchange)
        return response
    }
}
