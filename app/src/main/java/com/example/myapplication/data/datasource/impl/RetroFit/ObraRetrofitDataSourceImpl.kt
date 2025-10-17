package com.example.myapplication.data.datasource.impl.RetroFit

import com.example.myapplication.data.datasource.ObraRemoteDataSource
import com.example.myapplication.data.datasource.services.ObraRetrofitService
import com.example.myapplication.data.dtos.CreateObraDto
import com.example.myapplication.data.dtos.ObraDto
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class ObraRetrofitDataSourceImpl @Inject constructor(
    val service: ObraRetrofitService
): ObraRemoteDataSource {
    override suspend fun getObras(): List<ObraDto> {
        return service.getObras()
    }
    override suspend fun getObraById(id: String, currentUserId: String): ObraDto {
        return service.getObraById(id.toInt())
    }
    override suspend fun createObra(obra: CreateObraDto) {
        service.createObra(obra)
    }
    override suspend fun deleteObra(id: String) {
        service.deleteObra(id.toInt())
    }
    override suspend fun updateObra(
        id: String,
        obra: CreateObraDto
    ) {
        service.updateObra(id.toInt(),obra)
    }

    override suspend fun SendorDeleteLike(obraId: String, userId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun listenAllObras(): Flow<List<ObraDto>> {
        TODO("Not yet implemented")
    }
}