package com.example.myapplication.data.repository

import coil.network.HttpException
import com.example.myapplication.data.Obra
import com.example.myapplication.data.datasource.impl.ObraRetrofitDataSourceImpl
import com.example.myapplication.data.dtos.ArtistaObraDto
import com.example.myapplication.data.dtos.CreateObraDto
import com.example.myapplication.data.dtos.TagDto
import com.example.myapplication.data.dtos.toArtista
import com.example.myapplication.data.dtos.toObra
import javax.inject.Inject
import kotlin.Int

class ObraRepository @Inject constructor(
    private val ObraRemoteDataSource: ObraRetrofitDataSourceImpl
){
    suspend fun getObras(): Result<List<Obra>> {
        return try{
            val obras= ObraRemoteDataSource.getObras()
            val obrasInfo=obras.map {it.toObra()}
            Result.success(obrasInfo)
        }catch (e: HttpException){
            Result.failure(e)
        }
        catch (e: Exception){
            Result.failure(e)
        }
    }

    suspend fun getObra(id: String): Result<Obra>{
        return try {
            val obraDto = ObraRemoteDataSource.getObraById(id)
            val obra= obraDto.toObra()
            Result.success(obra)
        }catch (e: HttpException){
            e.response.code
            Result.failure(e)
        }
        catch (e: Exception){
            Result.failure(e)
        }
    }
}