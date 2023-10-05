package com.example.cleanarchitectureshowcase.features.home.data

import com.example.cleanarchitectureshowcase.features.home.domain.DataRepository

class DataRepositoryImpl(
    private val api: ServerDataApi
): DataRepository {

    override suspend fun getStocks(): List<StocksDTO> {
        val response = api.getStocks()
        return response
    }

    override suspend fun getStocksPictures(): List<StocksPictureDTO> {
        val response = api.getPicsForStocks()
        return response
    }
}
