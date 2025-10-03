package com.example.myapplication.data.datasource.impl

import android.util.Log
import com.example.myapplication.data.datasource.UserRemoteDataSource
import com.example.myapplication.data.datasource.services.UserRetrofitService
import com.example.myapplication.data.dtos.ArtistaDto
import javax.inject.Inject

class UserRetrofitDataSourceImpl @Inject constructor(
    val service: UserRetrofitService
): UserRemoteDataSource {
    override suspend fun getUserById(id:String): ArtistaDto{
        try {
            Log.d("UserRemoteDS", "🌐 Llamando API getUsuarioById con id=$id")
            val usuario = service.getUsuarioById(id.toInt())
            Log.d("UserRemoteDS", "🌐 Llamando API encontro usuario=$usuario")
            return usuario
        }catch (e: Exception){
            Log.d("UserRemoteDS", "🚨 Error en getUsuarioById", e)
            return ArtistaDto(0,"","","","",0,"","",0,0,0,"","",emptyList(),emptyList())
        }
    }
}