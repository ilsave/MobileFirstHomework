package com.example.cleanarchitectureshowcase.features.home.data

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import com.example.cleanarchitectureshowcase.BuildConfig

interface ServerDataApi {

    @GET("v3/available-traded/list")
    suspend fun getAllStocks(
        @Query("apikey") apikey: String = API_KEY
    ) : List<StockDTO>

//    @GET("v3/quote/AMGN,CRM,HON,GS,WBA,AAPL,NKE,V,UNH,CSCO,TRV,CVX,VZ,HD,INTC,MSFT,JNJ,WMT,CAT,DIS,DOW,JPM,BA,KO,MCD,AXP,IBM,MRK,MMM,PG")
@GET("v3/quote/{tickerSymbol}")
    suspend fun getStockFullInfo(
        @Path("tickerSymbol") tickerSymbol: String,
        @Query("apikey") apikey: String = API_KEY
    ): List<StockInfoDTO>

    @GET("v3/profile/{tickerSymbol}")
    suspend fun getStockPicture(
        @Path("tickerSymbol") tickerSymbol: String,
        @Query("apikey") apikey: String = API_KEY
    ): List<StockPictureDTO>

    companion object {
        const val API_KEY = BuildConfig.FINANCIALMODELINGPREP_API_KEY
        const val BASE_URL = "https://financialmodelingprep.com/api/"
    }
}
