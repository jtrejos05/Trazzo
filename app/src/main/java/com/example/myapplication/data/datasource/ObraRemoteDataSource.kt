package com.example.myapplication.data.datasource

import com.example.myapplication.data.Obra
import com.example.myapplication.data.dtos.ObraDto

interface ObraRemoteDataSource {
    suspend fun getObras(): List<ObraDto>
    suspend fun getObraById(id: String): ObraDto

}