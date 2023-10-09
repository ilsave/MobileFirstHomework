package com.example.cleanarchitectureshowcase.features.home.data

import com.google.gson.annotations.SerializedName

data class StockDTO(
    @SerializedName("symbol")
    var symbol: String,
    @SerializedName("name")
    var name: String,
    @SerializedName("price")
    var price : Double,
    @SerializedName("exchange")
    var exchange: String,
    @SerializedName("exchangeShortName")
    var exchangeShortName : String,
    @SerializedName("type")
    var type: String
)