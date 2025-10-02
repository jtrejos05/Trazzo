package com.example.myapplication.data.datasource.services

import com.example.myapplication.data.Comentario
import com.example.myapplication.data.dtos.ComentarioDto
import retrofit2.http.GET
import retrofit2.http.Path

interface ComentarioRetrofitService {
    @GET("/reviews")
    suspend fun getAllComentarios(): List<ComentarioDto>

    @GET("/reviews/{id}")
    suspend fun getComentarioById(@Path("id") id:Int): ComentarioDto

    @GET("/artista/{artistaId}/reviews")
    suspend fun getAllComentariosByArtistaId(@Path("artistaId") id: Int): List<ComentarioDto>

    @GET("/obra/{obraId}/reviews")
    suspend fun getAllComentariosByObraId(@Path("obraId") id: Int): List<ComentarioDto>
}