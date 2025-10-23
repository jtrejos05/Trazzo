package com.example.myapplication.data.repository

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.privacysandbox.ads.adservices.adid.AdId
import coil.network.HttpException
import com.example.myapplication.data.Obra
import com.example.myapplication.data.datasource.impl.Firestore.ObraFirestoreDataSourceImpl
import com.example.myapplication.data.datasource.impl.RetroFit.ObraRetrofitDataSourceImpl
import com.example.myapplication.data.dtos.ArtistaObraDto
import com.example.myapplication.data.dtos.CreateObraDto
import com.example.myapplication.data.dtos.TagDto
import com.example.myapplication.data.dtos.toArtista
import com.example.myapplication.data.dtos.toObra
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlin.Int

class ObraRepository @Inject constructor(
    private val ObraRemoteDataSource: ObraFirestoreDataSourceImpl
){
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getObras(): Result<List<Obra>> {
        return try{
            Log.d("Obras", "Entro AL REPO")
            val obras= ObraRemoteDataSource.getObras()
            Log.d("Obras", "llego del datasource")
            val obrasInfo=obras.map {it.toObra()}
            Log.d("Obras", "MAPEADO")
            Result.success(obrasInfo)
        }catch (e: HttpException){
            Log.d("Obras", e.message.toString())
            Result.failure(e)
        }
        catch (e: Exception){
            Log.d("Obras 2", e.message.toString())
            Result.failure(e)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getObra(id: String, userId: String): Result<Obra>{
        return try {
            val obraDto = ObraRemoteDataSource.getObraById(id,userId)
            if(obraDto==null){
                return Result.failure(Exception("Usuario no encontrado"))
            }
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

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getObrasById(userId: String): Result<List<Obra>> {
        return try{
            var obras= ObraRemoteDataSource.getObrasByUserId(userId)
            val obrasInfo=obras.map {it.toObra()}
            Result.success(obrasInfo)
        }catch (e: HttpException){
            Result.failure(e)
        }
        catch (e: Exception){
            Result.failure(e)
        }
    }

    suspend fun sendOrDeleteLike(obraId: String, userId: String): Result<Unit>{
        return try {
            ObraRemoteDataSource.SendorDeleteLike(obraId = obraId, userId = userId)
            Result.success(Unit)
        }catch (e: Exception){
            Result.failure(e)
        }
    }

    suspend fun listeAllObras(): Flow<List<Obra>> {
        return ObraRemoteDataSource.listenAllObras().map { obras->
            obras.map { it.toObra() }
        }
    }
}