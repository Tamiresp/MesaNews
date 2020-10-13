package com.example.mesanews.service

import com.example.mesanews.data.entity.AllResults
import com.example.mesanews.data.entity.Token
import com.example.mesanews.data.entity.User
import com.example.mesanews.data.entity.UserLogin
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.*

interface RestApi {
    @POST("/v1/client/auth/signup")
    @Headers("Content-Type: application/json")
    fun addUser(@Body userData: User): Call<Token>

    @POST("/v1/client/auth/signin")
    @Headers("Content-Type: application/json")
    fun loginUser(@Body userData: UserLogin): Call<Token>

    @Headers("Content-Type: application/json")
    @GET("/v1/client/news?")
    fun getAllNews(@Header("Authorization") token: String)  : Call<AllResults>

    @Headers("Content-Type: application/json")
    @GET("/v1/client/news?")
    fun getNewsForDate(@Query("published_at") published_at: String,
                       @Header("Authorization") token: String) : Call<AllResults>
}