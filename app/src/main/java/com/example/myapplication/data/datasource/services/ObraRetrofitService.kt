package com.example.myapplication.data.datasource.services

import com.example.myapplication.data.dtos.CreateObraDto
import com.example.myapplication.data.dtos.ObraDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ObraRetrofitService {

    @GET("obras")
    suspend fun getObras():List<ObraDto>

    @POST("obras")
    suspend fun createObra(@Body obra: CreateObraDto): Unit




}