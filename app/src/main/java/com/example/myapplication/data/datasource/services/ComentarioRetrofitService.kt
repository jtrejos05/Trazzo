package com.example.myapplication.data.datasource.services

import com.example.myapplication.data.Comentario
import com.example.myapplication.data.dtos.ComentarioDto
import com.example.myapplication.data.dtos.CreateCommentDto
import retrofit2.Response

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
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

    @POST("/reviews")
    suspend fun createComentario(@Body comment: CreateCommentDto): Unit

    @PUT("/reviews/{id}")
    suspend fun updateComentario(@Path("id") id: Int, @Body comment: CreateCommentDto): Unit

    @DELETE("/reviews/{id}")
    suspend fun deleteComentario(@Path("id") id: Int): Response<Unit>
}