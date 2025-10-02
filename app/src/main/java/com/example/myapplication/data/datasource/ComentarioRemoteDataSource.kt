package com.example.myapplication.data.datasource

import com.example.myapplication.data.dtos.ComentarioDto

interface ComentarioRemoteDataSource {
    suspend fun getAllCommentarios():List<ComentarioDto>
    suspend fun getComentarioById(id:String): ComentarioDto
    suspend fun getAllComentariosByObraId(id: String): List<ComentarioDto>
    suspend fun getAllComentariosByUsuarioId(id: String): List<ComentarioDto>
}