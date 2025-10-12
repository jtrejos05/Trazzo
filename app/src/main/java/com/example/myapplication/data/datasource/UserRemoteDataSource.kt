package com.example.myapplication.data.datasource

import com.example.myapplication.data.Artista
import com.example.myapplication.data.dtos.ArtistaDto
import com.example.myapplication.data.dtos.ObraDto
import com.example.myapplication.data.dtos.RegisterUserDto

interface UserRemoteDataSource {
    suspend fun getUserById(id: String): ArtistaDto

    suspend fun registerUser(registerUserDto: RegisterUserDto, userId: String): Unit

    suspend fun getUserObras(id:String): List<ObraDto>

    suspend fun updateUser(registerUserDto: RegisterUserDto,userId: String ): Unit
}