package com.example.myapplication.data.datasource

import com.example.myapplication.data.Obra
import com.example.myapplication.data.dtos.CreateObraDto
import com.example.myapplication.data.dtos.ObraDto

interface ObraRemoteDataSource {
    suspend fun getObras(): List<ObraDto>
    suspend fun getObraById(id: String): ObraDto
    suspend fun createObra(obra: CreateObraDto): Unit
    suspend fun deleteObra(id: String): Unit
    suspend fun updateObra(id:String, obra: CreateObraDto): Unit
}