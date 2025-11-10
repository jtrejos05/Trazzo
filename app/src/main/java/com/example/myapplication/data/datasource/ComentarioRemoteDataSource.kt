package com.example.myapplication.data.datasource

import com.example.myapplication.data.Comentario
import com.example.myapplication.data.dtos.ComentarioDto
import com.example.myapplication.data.dtos.CreateCommentDto
import retrofit2.Response

interface ComentarioRemoteDataSource {
    suspend fun getAllCommentarios():List<ComentarioDto>
    suspend fun getComentarioById(id: String): ComentarioDto?
    suspend fun getAllComentariosByObraId(id: String,userId: String = ""): List<ComentarioDto>
    suspend fun getAllComentariosByArtistaId(id: String): List<ComentarioDto>
    suspend fun createCommentario(comment: CreateCommentDto): String
    suspend fun updateCommentario(id: String, comentario: CreateCommentDto): String
    suspend fun deleteComentario(id: String): Response<Unit>
    suspend fun sendOrDeleteLike(commentId: String, userId: String): Unit
}