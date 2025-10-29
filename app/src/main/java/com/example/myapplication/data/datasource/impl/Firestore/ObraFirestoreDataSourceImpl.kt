package com.example.myapplication.data.datasource.impl.Firestore


import com.example.myapplication.data.datasource.ObraRemoteDataSource
import com.example.myapplication.data.dtos.CreateObraDto
import com.example.myapplication.data.dtos.ObraDto
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
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

    override suspend fun getObraById(id: String, currentUserId: String): ObraDto? {
        val obraRef = db.collection("obras").document(id)
        val obrasnapshot = obraRef.get().await()
        val obra = obrasnapshot.toObject(ObraDto::class.java) ?: null

        if (currentUserId.isNotEmpty()){
            val likesnapshot = obraRef.collection("likes").document(currentUserId).get().await()
            val hasLiked = likesnapshot.exists()
            if (hasLiked){
                obra!!.liked = true
            }
        }
        return obra
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

    override suspend fun SendorDeleteLike(obraId: String, userId: String) {
        val obraRef= db.collection("obras").document(obraId)
        val likesRef = obraRef.collection("likes").document(userId)

        db.runTransaction { transaction ->

            val likeDoc = transaction.get(likesRef)
            if (likeDoc.exists()){
                transaction.delete(likesRef)
                transaction.update(obraRef, "numLikes", FieldValue.increment(-1))

            }else{
                transaction.set(likesRef, mapOf("timestamp" to FieldValue.serverTimestamp()))
                transaction.update(obraRef, "numLikes", FieldValue.increment(1))

            }
        }
    }

    override suspend fun listenAllObras(): Flow<List<ObraDto>> = callbackFlow{
        val listener = db.collection("obras").addSnapshotListener{ snapshot, error ->
            if (error != null){
                close(error)
                return@addSnapshotListener
            }
            if (snapshot != null){
                val obras = snapshot.documents.map { doc->
                    val obra = doc.toObject(ObraDto::class.java)
                    obra?.copy(id = doc.id) ?: throw Exception("Obra no encontrada")
                }
                trySend(obras).isSuccess
            }
        }
        awaitClose { listener.remove() }
    }

    override suspend fun getObrasByUserId(userId: String): List<ObraDto> {
        val snapshot = db.collection("obras")
            .whereEqualTo("artistaId", userId)
            .get()
            .await()
        return snapshot.documents.map { doc ->
            val obra = doc.toObject(ObraDto::class.java)
            obra?.copy(id = doc.id) ?: throw Exception("Obra no encontrada")
        }
    }
}