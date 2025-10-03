package com.example.myapplication.data.datasource.impl

import com.example.myapplication.data.datasource.ComentarioRemoteDataSource
import com.example.myapplication.data.datasource.services.ComentarioRetrofitService
import com.example.myapplication.data.dtos.ComentarioDto
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

}