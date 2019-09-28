package com.testrates.data.repository

import com.google.gson.JsonObject
import com.testrates.data.rest.APIService
import io.reactivex.Observable
import retrofit2.Response
import javax.inject.Inject

class RateResponseRepository @Inject constructor(private val apiService: APIService) {

    fun getRateObserverResponse(baseParam: String): Observable<Response<JsonObject>> {
        return apiService.getResponseRates(baseParam)
    }
}