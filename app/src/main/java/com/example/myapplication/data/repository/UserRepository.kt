package com.example.myapplication.data.repository

import android.util.Log
import coil.network.HttpException
import com.example.myapplication.data.Artista
import com.example.myapplication.data.Comentario
import com.example.myapplication.data.datasource.UserRemoteDataSource
import com.example.myapplication.data.datasource.impl.UserRetrofitDataSourceImpl
import com.example.myapplication.data.dtos.toArtista
import com.example.myapplication.data.dtos.toComentario
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val DataSource: UserRetrofitDataSourceImpl
) {
    suspend fun getUserById(id: String): Result<Artista>{
        return try {
            val artistaDto = DataSource.getUserById(id)
            val usuario= artistaDto.toArtista()

            Result.success(usuario)
        }catch (e: HttpException){
            e.response.code
            Result.failure(e)
        }
        catch (e: Exception){
            Result.failure(e)
        }
    }
}