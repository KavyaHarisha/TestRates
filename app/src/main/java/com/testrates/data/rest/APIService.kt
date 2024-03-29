package com.testrates.data.rest

import com.google.gson.JsonObject
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {

    @GET("latest")
    fun getResponseRates(@Query("base") base: String): Observable<Response<JsonObject>>
}