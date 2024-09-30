package com.developerdaya.retrofit_http_requests.data.api

import com.developerdaya.retrofit_http_requests.data.model.EmployeeResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("v1/1a44a28a-7c86-4738-8a03-1eafeffe38c8")
    fun getEmployees(): Call<EmployeeResponse>
}
