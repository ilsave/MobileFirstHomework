package com.example.cleanarchitectureshowcase.features.home.data

import com.example.cleanarchitectureshowcase.features.home.domain.DataRepository

class DataRepositoryImpl(
    private val api: ServerDataApi
): DataRepository {

    override suspend fun getData() : DataDTO {
        // условный вызов вашей апишки
        // используйте апи здесь
        return DataDTO(
            title = "title",
            subTitle = "subtitle",
            description = "description",
            someDumpInfo = "DumpiDump",
            importantDataForDomain = "topSecret"
        )
    }

    override suspend fun getStocks(): List<StocksDTO> {
        val response = api.getStocks()
        val smth = response.get(0)
        return response
    }

    override suspend fun getStocksPictures(): List<StocksPictureDTO> {
        val response = api.getPicsForStocks()
        val smth = response
        return response
    }
}
