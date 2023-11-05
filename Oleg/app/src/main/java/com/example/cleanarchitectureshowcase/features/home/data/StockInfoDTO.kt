package com.example.cleanarchitectureshowcase.features.home.data

import com.google.gson.annotations.SerializedName

data class StockInfoDTO (
    @SerializedName("symbol")
    val symbol:String,
    @SerializedName("name")
    val name:String,
    @SerializedName("price")
    val price: Double,
    @SerializedName("changesPercentage")
    val changesPercentage: Double,
    @SerializedName("change")
    val change: Double,
    @SerializedName("dayLow")
    val dayLow: Double,
    @SerializedName("dayHigh")
    val dayHigh: Double,
    @SerializedName("yearHigh")
    val yearHigh: Double,
    @SerializedName("yearLow")
    val yearLow: Double,
    @SerializedName("marketCap")
    val marketCap: Long,
    @SerializedName("priceAvg50")
    val priceAvg50: Double,
    @SerializedName("priceAvg200")
    val priceAvg200: Double,
    @SerializedName("exchange")
    val exchange: String,
    @SerializedName("volume")
    val volume: Int,
    @SerializedName("avgVolume")
    val avgVolume: Int,
    @SerializedName("open")
    val open: Double,
    @SerializedName("previousClose")
    val previousClose: Double,
    @SerializedName("eps")
    val eps: Double,
    @SerializedName("pe")
    val pe: Double,
    @SerializedName("earningsAnnouncement")
    val earningsAnnouncement: String,
    @SerializedName("sharesOutstanding")
    val sharesOutstanding: Long,
    @SerializedName("timestamp")
    val timestamp: Long
)
