package com.example.myapplication.data.datasource.impl.Firestore

import android.util.Log
import com.example.myapplication.data.Artista
import com.example.myapplication.data.datasource.UserRemoteDataSource
import com.example.myapplication.data.dtos.ArtistaDto
import com.example.myapplication.data.dtos.ComentarioDto
import com.example.myapplication.data.dtos.ObraDto
import com.example.myapplication.data.dtos.RegisterUserDto
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import jakarta.inject.Inject
import kotlinx.coroutines.tasks.await

class UserFirestoreDataSourceImpl @Inject constructor(
    private val db: FirebaseFirestore
): UserRemoteDataSource  {
    override suspend fun getUserById(id: String, userId:String): ArtistaDto? {
        val docRef = db.collection("users").document(id)
        val snapshot = docRef.get().await()
        val user = snapshot.toObject(ArtistaDto::class.java) ?: return null
        if (userId != ""){
            val seguidoresDoc= docRef.collection("seguidores").document(userId).get().await()
            val exist = seguidoresDoc.exists()
            user.seSiguen = exist
        }
        return user

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

    override suspend fun followOrUnfollowUser(userId: String, target: String) {
        val userRef = db.collection("users").document(userId)
        val targetRef = db.collection("users").document(target)

        val siguiendoRef = userRef.collection("seguidos").document(target)
        val seguidoresRef = targetRef.collection("seguidores").document(userId)

        db.runTransaction { transaction ->
            val siguiendoSnap = transaction.get(siguiendoRef)
            if (siguiendoSnap.exists()){
                transaction.delete(siguiendoRef)
                transaction.delete(seguidoresRef)
                transaction.update(userRef, "numSeguidos", FieldValue.increment(-1))
                transaction.update(targetRef, "numSeguidores", FieldValue.increment(-1))
            }else{
                transaction.set(siguiendoRef, mapOf("timestamp" to FieldValue.serverTimestamp()))
                transaction.set(seguidoresRef, mapOf("timestamp" to FieldValue.serverTimestamp()))
                transaction.update(userRef, "numSeguidos", FieldValue.increment(1))
                transaction.update(targetRef, "numSeguidores", FieldValue.increment(1))
            }
        }
    }

    override suspend fun getSeguidores(id: String): List<ArtistaDto> {
        val userRef = db.collection("users").document(id)
        val userSnap = userRef.get().await()
        val seguidoresRef = userRef.collection("seguidores")
        val seguidoresSnapshot = seguidoresRef.get().await()
        return seguidoresSnapshot.documents.map { doc ->
            val artist = db.collection("users").document(doc.id).get().await()
            val artista = artist.toObject(ArtistaDto::class.java)
            artista?.copy(id = doc.id) ?: throw Exception("seguidor no encontrado")
        }
    }

    override suspend fun getSeguidos(id: String): List<ArtistaDto> {
        val userRef = db.collection("users").document(id)
        val userSnap = userRef.get().await()
        val seguidoresRef = userRef.collection("seguidos")
        val seguidoresSnapshot = seguidoresRef.get().await()
        return seguidoresSnapshot.documents.map { doc ->
            val artist = db.collection("users").document(doc.id).get().await()
            val artista = artist.toObject(ArtistaDto::class.java)
            artista?.copy(id = doc.id) ?: throw Exception("seguido no encontrado")
        }
    }
}