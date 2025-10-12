package com.example.myapplication.data.repository

import android.util.Log
import coil.network.HttpException
import com.example.myapplication.data.Artista
import com.example.myapplication.data.Comentario
import com.example.myapplication.data.Obra
import com.example.myapplication.data.datasource.UserRemoteDataSource
import com.example.myapplication.data.datasource.impl.Firestore.UserFirestoreDataSourceImpl
import com.example.myapplication.data.datasource.impl.Firestore.UserFirestoreDataSourceImpl_Factory
import com.example.myapplication.data.datasource.impl.RetroFit.UserRetrofitDataSourceImpl
import com.example.myapplication.data.dtos.ArtistaDto
import com.example.myapplication.data.dtos.RegisterUserDto
import com.example.myapplication.data.dtos.toArtista
import com.example.myapplication.data.dtos.toComentario
import com.example.myapplication.data.dtos.toObra
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.auth.User
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val UserRemoteDataSource: UserFirestoreDataSourceImpl
) {
    suspend fun getUserById(id: String): Result<Artista>{
        return try {
            Log.d("User", "busca")
            val artistaDto = UserRemoteDataSource.getUserById(id)
            Log.d("User", "DTO")
            val usuario= artistaDto.toArtista()
            Log.d("User", usuario.biografia)
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
            val registerUserDto= RegisterUserDto(nombre = usuario,edad = edad, profesion = profesion, biografia = bio, id = userId, fotousuario = "")
            UserRemoteDataSource.registerUser(registerUserDto,userId)
            Log.d("USER", "Se guardo")
            Result.success(Unit)
        }catch(e: Exception){
            Log.d("TAB","getUserById: $(e.message)")
            Result.failure(e)
        }
    }

    suspend fun getObrasByArtista(id: String): Result<List<Obra>>{
        return try {
            Log.d("Obras", "ENTRO AL REPO")
            val obraDto = UserRemoteDataSource.getUserObras(id)
            Log.d("Obras", "LLEGO DEL DATASOURCE")
            val obras= obraDto.map { obra-> obra.toObra() }
            Log.d("Obra", "MAPEO")
            Result.success(obras)
        }catch (e: HttpException){
            Log.d("Obras", e.response.message)
            Result.failure(e)
        }
        catch (e: Exception){
            Log.d("Obras", e.message!!)
            Result.failure(e)
        }
    }

    suspend fun updateArtista( usuario:String,
                               edad:String,
                               profesion:String,
                               bio:String,
                               fotous:String,
                               userId: String): Result<Unit>{
        return try{
            val registerUserDto= RegisterUserDto(nombre = usuario,edad = edad, profesion = profesion, biografia = bio, id = userId, fotousuario = fotous)
            UserRemoteDataSource.registerUser(registerUserDto,userId)
            Log.d("USER", "Se guardo")
            Result.success(Unit)
        }catch(e: Exception){
            Log.d("TAB","getUserById: $(e.message)")
            Result.failure(e)
        }
    }
}


