package com.example.myapplication.data.datasource

import com.example.myapplication.data.Artista
import com.example.myapplication.data.dtos.ArtistaDto
import com.example.myapplication.data.dtos.ObraDto
import com.example.myapplication.data.dtos.RegisterUserDto

interface UserRemoteDataSource {
    suspend fun getUserById(id: String, userId: String): ArtistaDto

    suspend fun registerUser(registerUserDto: RegisterUserDto, userId: String): Unit

    suspend fun getUserObras(id:String): List<ObraDto>

    suspend fun updateUser(registerUserDto: RegisterUserDto,userId: String ): Unit

    suspend fun followOrUnfollowUser(userId: String, target: String): Unit

    suspend fun getSeguidores(id: String): List<ArtistaDto>

    suspend fun getSeguidos(id: String): List<ArtistaDto>
}