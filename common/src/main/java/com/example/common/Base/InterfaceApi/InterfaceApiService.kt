package com.example.common.Base.InterfaceApi

import com.example.common.Base.DataClass.QuoteList
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class InterfaceApiService {
    interface QuotesApi {
        @GET("/quotes")
        suspend fun getQuotes() : Response<QuoteList>?
    }
}
