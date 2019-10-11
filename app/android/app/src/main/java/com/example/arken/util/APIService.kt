package com.example.arken.util

import com.example.arken.model.LoginUser
import com.example.arken.model.SignupUser

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST

interface APIService {
    @Headers("Content-Type: application/json")
    @POST("signup")
    suspend fun signup(@Body signupUser: SignupUser): Response<String>

    @Headers("Content-Type: application/json")
    @POST("login")
    suspend fun login(@Body loginUser: LoginUser): Response<String>
}
