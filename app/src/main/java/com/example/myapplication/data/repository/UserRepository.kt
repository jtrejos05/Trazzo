package com.example.myapplication.data.repository

import android.util.Log
import coil.network.HttpException
import com.example.myapplication.data.Artista
import com.example.myapplication.data.Comentario
import com.example.myapplication.data.datasource.UserRemoteDataSource
import com.example.myapplication.data.datasource.impl.Firestore.UserFirestoreDataSourceImpl
import com.example.myapplication.data.datasource.impl.UserRetrofitDataSourceImpl
import com.example.myapplication.data.dtos.RegisterUserDto
import com.example.myapplication.data.dtos.toArtista
import com.example.myapplication.data.dtos.toComentario
import com.google.firebase.firestore.auth.User
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val UserRemoteDataSource: UserFirestoreDataSourceImpl
) {
    suspend fun getUserById(id: String): Result<Artista>{
        return try {
            val artistaDto = UserRemoteDataSource.getUserById(id)
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

    suspend fun registerUser(
        usuario:String,
        edad:String,
        profesion:String,
        bio:String,
        userId: String
    ): Result<Unit> {
        return try{
            val registerUserDto= RegisterUserDto(usuario,bio,edad,profesion)
            UserRemoteDataSource.registerUser(registerUserDto,userId)
            Result.success(Unit)
        }catch(e: Exception){
            Log.d("TAB","getUserById: $(e.message)")
            Result.failure(e)
        }
    }
}


