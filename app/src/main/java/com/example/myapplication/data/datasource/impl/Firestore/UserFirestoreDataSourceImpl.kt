package com.example.myapplication.data.datasource.impl.Firestore

import com.example.myapplication.data.datasource.UserRemoteDataSource
import com.example.myapplication.data.dtos.ArtistaDto
import com.example.myapplication.data.dtos.RegisterUserDto
import com.google.firebase.firestore.FirebaseFirestore
import jakarta.inject.Inject
import kotlinx.coroutines.tasks.await

class UserFirestoreDataSourceImpl @Inject constructor(private val db: FirebaseFirestore): UserRemoteDataSource  {
    override suspend fun getUserById(id: String): ArtistaDto {
        TODO("Not yet implemented")
    }

    override suspend fun registerUser(
        registerUserDto: RegisterUserDto,
        userId: String
    ) {
        val docRef=db.collection("users").document(userId)  // Se crea un documento
        docRef.set(registerUserDto).await()  // Insertando el documento
    }
}