package com.example.myapplication.data.repository

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import coil.network.HttpException
import com.example.myapplication.data.Comentario
import com.example.myapplication.data.datasource.AuthRemoteDataSource
import com.example.myapplication.data.datasource.impl.Firestore.ComentarioFirestoreDataSourceImpl
import com.example.myapplication.data.datasource.impl.Firestore.UserFirestoreDataSourceImpl
import com.example.myapplication.data.datasource.impl.RetroFit.ComentarioRetrofitDataSourceImpl
import com.example.myapplication.data.datasource.impl.RetroFit.UserRetrofitDataSourceImpl
import com.example.myapplication.data.dtos.CreateCommentDto
import com.example.myapplication.data.dtos.CreateCommentUserDto
import com.example.myapplication.data.dtos.toComentario
import javax.inject.Inject

class ComentarioRepository @Inject constructor(
    private val DataSource: ComentarioFirestoreDataSourceImpl,
    private val UserDataSource: UserFirestoreDataSourceImpl,
    private val authDataSource: AuthRemoteDataSource
) {
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getAllComentarios(): Result<List<Comentario>>{
        return try {
            val commentarios = DataSource.getAllCommentarios()
            val comentario= commentarios.map{ it.toComentario() }
            Result.success(comentario)
        }
        catch (e: HttpException){
            e.response.code
            Result.failure(e)
        }
        catch (e: Exception){
            Result.failure(e)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getComenarioById(id: String): Result<Comentario>{
        return try {
            val comentarioDto = DataSource.getComentarioById(id)
            if (comentarioDto == null){
                return Result.failure(Exception("Comentario not found"))
            }
            val comentario= comentarioDto.toComentario()
            Result.success(comentario)
        }catch (e: HttpException){
            e.response.code
            Result.failure(e)
        }
        catch (e: Exception){
            Result.failure(e)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getComentarioByArtistaId(id: String): Result<List<Comentario>>{
        return try {
            val comentarios = DataSource.getAllComentariosByArtistaId(id)
            val comentario = comentarios.map { it.toComentario() }
            Result.success(comentario)
        }catch (e: HttpException){
            e.response.code
            Result.failure(e)
        }
        catch (e: Exception){
            Result.failure(e)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getComentarioByObraId(id: String): Result<List<Comentario>>{
        return try {
            Log.d("COMENTS", "SALIO AL DATA")
            val comentarios = DataSource.getAllComentariosByObraId(id,authDataSource.currentUser?.uid ?: "")
            Log.d("COMENTS", "LLEGO ALGO DEL DATA")
            val comentario = comentarios.map { it.toComentario() }
            Log.d("COMENTS","SE MAPEO")
            Result.success(comentario)
        }catch (e: HttpException){
            Log.d("COMENT",e.response.message)
            Result.failure(e)
        }
        catch (e: Exception){
            Log.d("COMENT",e.message!!)
            Result.failure(e)
        }
    }

    suspend fun createComentario(comentario: String, calificacion: Double, artistaId: String, obraId: String, parentCommentId: String?, commentId: String?, img:String? = null): Result<String>{
        return try {
            Log.d("Users", "Repo: User")
            val user = UserDataSource.getUserById(artistaId, authDataSource.currentUser?.uid ?: "")
            Log.d("Users", "Repo: photo")
            val photoUrl = authDataSource.currentUser?.photoUrl?.toString()
            if (user == null){
                return Result.failure(Exception("User not found"))
            }
            val createCommentUserDto = CreateCommentUserDto(
                nombre = user.nombre,
                fotousuario = photoUrl
            )
            Log.d("Users", "Repo: create")
            val createCommentDto = CreateCommentDto(calificacion,comentario,artistaId,obraId,parentCommentId?.toInt(), commentId ?: null,createCommentUserDto,img)
            Log.d("User", "CREANDO DTO")
            val id: String
            if (commentId != null){
                id =DataSource.updateCommentario(commentId,createCommentDto)
            }else{
                Log.d("User", "CREando comment")
                id = DataSource.createCommentario(createCommentDto)
            }
            Result.success(id)
        }catch (e: HttpException){
            e.response.code
            Result.failure(e)
        }
        catch (e: Exception){
            Result.failure(e)
        }
    }

    suspend fun deleteComentario(id:String): Result<Unit>{
        return try {
            DataSource.deleteComentario(id)
            Result.success(Unit)
        }catch (e: HttpException){
            e.response.code
            Result.failure(e)
        }
        catch (e: Exception){
            Result.failure(e)
        }
    }
    suspend fun sendOrDeleteLike(commentId: String, userId: String): Result<Unit>{
        return try {
            DataSource.sendOrDeleteLike(commentId = commentId, userId=userId)
            Result.success(Unit)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}