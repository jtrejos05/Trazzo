package com.example.myapplication.data.datasource.impl.Firestore

import android.util.Log
import com.example.myapplication.data.datasource.UserRemoteDataSource
import com.example.myapplication.data.dtos.ArtistaDto
import com.example.myapplication.data.dtos.ComentarioDto
import com.example.myapplication.data.dtos.ObraDto
import com.example.myapplication.data.dtos.RegisterUserDto
import com.google.firebase.firestore.FirebaseFirestore
import jakarta.inject.Inject
import kotlinx.coroutines.tasks.await

class UserFirestoreDataSourceImpl @Inject constructor(
    private val db: FirebaseFirestore
): UserRemoteDataSource  {
    override suspend fun getUserById(id: String): ArtistaDto {
        val docRef = db.collection("users").document(id)
        val snapshot = docRef.get().await()
        if (!snapshot.exists()) error("Usuario no existe")
        return snapshot.toObject(ArtistaDto::class.java)
            ?: error("No pudimos mapear el usuario (DTO inválido o tipos distintos)")
    }


    override suspend fun registerUser(
        registerUserDto: RegisterUserDto,
        userId: String
    ) {
        val docRef=db.collection("users").document(userId)  // Se crea un documento
        docRef.set(registerUserDto).await()  // Insertando el documento
    }

    override suspend fun getUserObras(id: String): List<ObraDto> {
        Log.d("Obras", "LLEGO AL DATASOURCE")
        return db.collection("obras").whereEqualTo("artistaId",id).get().await().toObjects(
            ObraDto::class.java)
    }

    override suspend fun updateUser(
        registerUserDto: RegisterUserDto,
        userId: String
    ) {
        registerUserDto.id=userId
        db.collection("users").document(userId).set(registerUserDto).await()
    }
}