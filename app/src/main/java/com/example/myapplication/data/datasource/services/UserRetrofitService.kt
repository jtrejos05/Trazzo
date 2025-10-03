package com.example.myapplication.data.datasource.services

import com.example.myapplication.data.dtos.ArtistaDto
import retrofit2.http.GET
import retrofit2.http.Path

interface UserRetrofitService {
    @GET("/artistas/{id}")
    suspend fun getUsuarioById(@Path("id") id:Int): ArtistaDto
}