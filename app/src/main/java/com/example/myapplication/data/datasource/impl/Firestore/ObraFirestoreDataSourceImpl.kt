package com.example.myapplication.data.datasource.impl.Firestore

import android.util.Log
import com.example.myapplication.data.datasource.ObraRemoteDataSource
import com.example.myapplication.data.dtos.ArtistaDto
import com.example.myapplication.data.dtos.CreateObraDto
import com.example.myapplication.data.dtos.ObraDto
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObjects
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ObraFirestoreDataSourceImpl @Inject constructor(
    private val db: FirebaseFirestore
) : ObraRemoteDataSource {
    override suspend fun getObras(): List<ObraDto> {
        val snapshot = db.collection("obras").get().await()
        return snapshot.documents.map { doc->
            val obra = doc.toObject(ObraDto::class.java)
            obra?.copy(id = doc.id) ?: throw Exception("Obra no encontrada")
        }
    }

    override suspend fun getObraById(id: String): ObraDto {
        val docRef = db.collection("obras").document(id)
        val snapshot = docRef.get().await()
        if (!snapshot.exists()) error("obra no existe")
        return snapshot.toObject(ObraDto::class.java) ?: error("No pudimos mapear la obra (DTO inválido o tipos distintos)")
    }

    override suspend fun createObra(obra: CreateObraDto) {
        db.collection("obras").add(obra).await()
    }

    override suspend fun deleteObra(id: String) {
        TODO("Not yet implemented")
    }

    override suspend fun updateObra(
        id: String,
        obra: CreateObraDto
    ) {
        TODO("Not yet implemented")
    }
}