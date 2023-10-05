package com.example.cleanarchitectureshowcase.features.home.data

import retrofit2.http.GET
import retrofit2.http.Query

interface ServerDataApi {

    @GET("v3/quote/AMGN,CRM,HON,GS,WBA,AAPL,NKE,V,UNH,CSCO,TRV,CVX,VZ,HD,INTC,MSFT,JNJ,WMT,CAT,DIS,DOW,JPM,BA,KO,MCD,AXP,IBM,MRK,MMM,PG")
    suspend fun getStocks(
        @Query("apikey") apikey: String = API_KEY
    ): List<StocksDTO>

    @GET("v3/profile/AMGN,CRM,HON,GS,WBA,AAPL,NKE,V,UNH,CSCO,TRV,CVX,VZ,HD,INTC,MSFT,JNJ,WMT,CAT,DIS,DOW,JPM,BA,KO,MCD,AXP,IBM,MRK,MMM,PG")
    suspend fun getPicsForStocks(
        @Query("apikey") apikey: String = API_KEY
    ): List<StocksPictureDTO>

    companion object {
        const val API_KEY = "2ad6dfabbf2e78a686987e9a57d328a1"
        const val BASE_URL = "https://financialmodelingprep.com/api/"
    }
}
