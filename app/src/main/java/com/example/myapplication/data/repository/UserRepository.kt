package com.example.myapplication.data.repository

import android.util.Log
import coil.network.HttpException
import com.example.myapplication.data.Artista
import com.example.myapplication.data.Obra
import com.example.myapplication.data.datasource.impl.Firestore.UserFirestoreDataSourceImpl
import com.example.myapplication.data.dtos.ArtistaDto
import com.example.myapplication.data.dtos.RegisterUserDto
import com.example.myapplication.data.dtos.toArtista
import com.example.myapplication.data.dtos.toObra
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val UserRemoteDataSource: UserFirestoreDataSourceImpl,
    private val authRepository: AuthRepository,
    private val firebaseMessaging: FirebaseMessaging
) {
    suspend fun getUserById(id: String): Result<Artista>{
        val currentUser = authRepository.currentUser?.uid ?: ""
        //Log.d("Seguir", "USERID: ${currentUser}")
        return try {
            //Log.d("User", "busca")
            val artistaDto = UserRemoteDataSource.getUserById(id,currentUser)
            //Log.d("User", "DTO")
            if (artistaDto == null){
                return Result.failure(Exception("Usuario no encontrado"))
            }
            val usuario= artistaDto.toArtista()
            //Log.d("User", usuario.biografia)
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
            val fcmToken = firebaseMessaging.token.await()
            //Log.d("MENSAJE", fcmToken)
            val registerUserDto= RegisterUserDto(nombre = usuario,edad = edad, profesion = profesion, biografia = bio, id = userId, fotousuario = "", FCMToken = fcmToken)
            UserRemoteDataSource.registerUser(registerUserDto,userId)
            //Log.d("USER", "Se guardo")
            Result.success(Unit)
        }catch(e: Exception){
            //Log.d("TAB","getUserById: $(e.message)")
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
                               userId: String,
                               numSeguidores: Int,
                               numSeguidos: Int): Result<Unit>{
        return try{
            val fcmToken = FirebaseMessaging.getInstance().token.await()
            val registerUserDto= RegisterUserDto(nombre = usuario,edad = edad, profesion = profesion, biografia = bio, id = userId, fotousuario = fotous, FCMToken =fcmToken)
            UserRemoteDataSource.registerUser(registerUserDto,userId)
            Log.d("USER", "Se guardo")
            Result.success(Unit)
        }catch(e: Exception){
            Log.d("TAB","getUserById: $(e.message)")
            Result.failure(e)
        }
    }

    suspend fun followOrUnfollowUser(userId: String, target: String): Result<Unit>{
        return try {
            UserRemoteDataSource.followOrUnfollowUser(userId,target)
            Result.success(Unit)
        }catch (e: Exception){
            Log.d("follow", "getUSerbyId: ${e.message}")
            Result.failure(e)

        }
    }

    suspend fun getSeguidosOrSeguidores(seguidos: Boolean, id: String): Result<List<Artista>>{
        return try {
            var users: List<ArtistaDto>
            if (seguidos){
                users = UserRemoteDataSource.getSeguidos(id)
            }else{
               users = UserRemoteDataSource.getSeguidores(id)
            }
            val artistas =users.map { artista ->
                artista.toArtista()
            }
            return Result.success(artistas)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}


