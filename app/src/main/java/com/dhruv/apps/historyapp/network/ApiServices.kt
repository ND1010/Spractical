package com.dhruv.apps.historyapp.network

import com.dhruv.apps.historyapp.models.GitUser
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {

    @GET("users")
    fun getUser(
        @Query("per_page") per_page: String,
        @Query("since") since: String
    ): Call<GitUser>
}