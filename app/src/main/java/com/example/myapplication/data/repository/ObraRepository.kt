package com.example.myapplication.data.repository

import coil.network.HttpException
import com.example.myapplication.data.Obra
import com.example.myapplication.data.datasource.ObraRemoteDataSource
import com.example.myapplication.data.datasource.impl.ObraRetrofitDataSourceImpl
import com.example.myapplication.data.dtos.ArtistaObraDto
import com.example.myapplication.data.dtos.ObraDto
import com.example.myapplication.data.dtos.TagDto
import com.example.myapplication.data.dtos.toObra
import javax.inject.Inject

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

    suspend fun createObra(
         id: Int,
         artistaId: Int,
         titulo: String,
         descripcion: String,
         obraIMG: String,
         numComentarios: Int,
         likes: Int,
         compartidos: Int,
         vistas: Int,
         createdAt: String,
         updatedAt: String,
         artista: ArtistaObraDto,
         tags: List<TagDto>
    ){

    }
}