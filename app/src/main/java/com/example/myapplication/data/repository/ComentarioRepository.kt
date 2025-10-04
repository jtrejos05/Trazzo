package com.example.myapplication.data.repository

import android.util.Log
import coil.network.HttpException
import com.example.myapplication.data.Comentario
import com.example.myapplication.data.datasource.impl.ComentarioRetrofitDataSourceImpl
import com.example.myapplication.data.dtos.CreateCommentDto
import com.example.myapplication.data.dtos.toComentario
import javax.inject.Inject

class ComentarioRepository @Inject constructor(
    private val DataSource: ComentarioRetrofitDataSourceImpl
) {
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

    suspend fun getComenarioById(id: String): Result<Comentario>{
        return try {
            val comentarioDto = DataSource.getComentarioById(id)
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

    suspend fun getComentarioByObraId(id: String): Result<List<Comentario>>{
        return try {
            val comentarios = DataSource.getAllComentariosByObraId(id)
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

    suspend fun createComentario(comentario: String, calificacion: Double, artistaId: String, obraId: String, parentCommentId: String?, commentId: String?): Result<Unit>{
        return try {
            val createCommentDto = CreateCommentDto(calificacion,comentario,artistaId.toInt(),obraId.toInt(),parentCommentId?.toInt(), commentId?.toInt())
            if (commentId != null){
                DataSource.updateCommentario(commentId,createCommentDto)
            }else{
                DataSource.createCommentario(createCommentDto)
            }
            Result.success(Unit)
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

}