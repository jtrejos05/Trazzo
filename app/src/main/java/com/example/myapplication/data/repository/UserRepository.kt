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
            Log.d("UserRepository", "🔎 Buscando usuario en DataSource con id=$id")

            val artistaDto = DataSource.getUserById(id)
            Log.d("UserRepository", "📦 DTO recibido: $artistaDto")

            val usuario= artistaDto.toArtista()
            Log.d("UserRepository", "✅ Mapeado a entidad: $usuario")


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