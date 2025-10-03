package com.example.myapplication.data.repository

import coil.network.HttpException
import com.example.myapplication.data.Obra
import com.example.myapplication.data.datasource.impl.ObraRetrofitDataSourceImpl
import com.example.myapplication.data.dtos.ArtistaObraDto
import com.example.myapplication.data.dtos.CreateObraDto
import com.example.myapplication.data.dtos.TagDto
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
                           ) : Result<Unit> {
        return try{
                val createObraDto= CreateObraDto(id, artistaId, titulo, descripcion, obraIMG, numComentarios, likes, compartidos, vistas, createdAt, updatedAt, artista, tags
                )
        ObraRemoteDataSource.createObra(createObraDto)
        }catch (Exception e){
            Result.failure(e)
        }
    }
}