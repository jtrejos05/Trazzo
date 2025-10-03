package com.example.myapplication.data.datasource.services

import com.example.myapplication.data.dtos.CreateObraDto
import com.example.myapplication.data.dtos.ObraDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ObraRetrofitService {

    @GET("obras")
    suspend fun getObras():List<ObraDto>

    @POST("obras")
    suspend fun createObra(@Body obra: CreateObraDto): Unit

    @DELETE("obras/{id}")
    suspend fun deleteObra(@Path("id") id: Int):Unit

    @PUT("obras/{id}")
    suspend fun updateObra(@Path("id") id: Int, @Body obra: CreateObraDto): Unit

    @GET("obras/{id}")
    suspend fun getObraById(@Path("id") id: Int): ObraDto
}