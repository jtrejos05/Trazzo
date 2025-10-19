package com.example.myapplication.data.datasource

import com.example.myapplication.data.Obra
import com.example.myapplication.data.dtos.CreateObraDto
import com.example.myapplication.data.dtos.ObraDto
import kotlinx.coroutines.flow.Flow

interface ObraRemoteDataSource {
    suspend fun getObras(): List<ObraDto>
    suspend fun getObraById(id: String, currentUserId: String = ""): ObraDto
    suspend fun createObra(obra: CreateObraDto): Unit
    suspend fun deleteObra(id: String): Unit
    suspend fun updateObra(id:String, obra: CreateObraDto): Unit
    suspend fun SendorDeleteLike(obraId: String,userId:String): Unit
    suspend fun listenAllObras(): Flow<List<ObraDto>>
    suspend fun getObrasByUserId(userId: String): List<ObraDto>
}