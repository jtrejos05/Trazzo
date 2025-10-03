package com.example.myapplication.data.datasource.impl

import android.util.Log
import com.example.myapplication.data.datasource.ComentarioRemoteDataSource
import com.example.myapplication.data.datasource.services.ComentarioRetrofitService
import com.example.myapplication.data.dtos.ComentarioDto
import com.example.myapplication.data.dtos.CreateCommentDto
import javax.inject.Inject

class ComentarioRetrofitDataSourceImpl @Inject constructor(
    val service: ComentarioRetrofitService
): ComentarioRemoteDataSource {
    override suspend fun getAllComentariosByObraId(id: String): List<ComentarioDto> {
        return service.getAllComentariosByObraId(id.toInt())
    }

    override suspend fun getAllComentariosByArtistaId(id: String): List<ComentarioDto> {
        return service.getAllComentariosByArtistaId(id.toInt())
    }

    override suspend fun getAllCommentarios(): List<ComentarioDto> {
        return service.getAllComentarios()
    }

    override suspend fun getComentarioById(id: String): ComentarioDto {
        return service.getComentarioById(id.toInt())
    }

    override suspend fun createCommentario(comment: CreateCommentDto) {
        return service.createComentario(comment)
    }

    override suspend fun updateCommentario(id: String, comentario: CreateCommentDto) {

        Log.d("Actualizado", "$id $comentario")

        return service.updateComentario(id.toInt(), comentario)
    }

    override suspend fun deleteComentario(id: String) {
        Log.d("IDESITAR L", "AYDA JESUS")
        return service.deleteComentario(id.toInt())
    }

}