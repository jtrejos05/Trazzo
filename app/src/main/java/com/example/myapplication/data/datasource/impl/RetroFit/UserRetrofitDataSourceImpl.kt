package com.example.myapplication.data.datasource.impl.RetroFit

import android.util.Log
import com.example.myapplication.data.datasource.UserRemoteDataSource
import com.example.myapplication.data.datasource.services.UserRetrofitService
import com.example.myapplication.data.dtos.ArtistaDto
import com.example.myapplication.data.dtos.ObraDto
import com.example.myapplication.data.dtos.RegisterUserDto
import javax.inject.Inject

class UserRetrofitDataSourceImpl @Inject constructor(
    val service: UserRetrofitService
): UserRemoteDataSource {
    override suspend fun getUserById(id:String): ArtistaDto {
        try {
            val usuario = service.getUsuarioById(id.toInt())
            return usuario
        }catch (e: Exception){
            Log.d("UserRemoteDS", "🚨 Error en getUsuarioById", e)
            return ArtistaDto(
                "0",
                "",
                "",
                "",
                "",
                "0",
                "",
                "",
                0,
                0,
                0,
                "",
                "",
                emptyList(),
                emptyList()
            )
        }
    }

    override suspend fun registerUser(registerUserDto: RegisterUserDto, userId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getUserObras(id: String): List<ObraDto> {
        TODO("Not yet implemented")
    }

    override suspend fun updateUser(
        registerUserDto: RegisterUserDto,
        userId: String
    ) {
        TODO("Not yet implemented")
    }
}