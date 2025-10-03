package com.example.myapplication.data.datasource

import com.example.myapplication.data.Artista
import com.example.myapplication.data.dtos.ArtistaDto

interface UserRemoteDataSource {
    suspend fun getUserById(id: String): ArtistaDto
}