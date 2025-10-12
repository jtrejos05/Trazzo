package com.example.myapplication.data.datasource.impl.Firestore

import android.util.Log
import com.example.myapplication.data.datasource.ComentarioRemoteDataSource
import com.example.myapplication.data.dtos.ComentarioDto
import com.example.myapplication.data.dtos.CreateCommentDto
import com.example.myapplication.data.dtos.ObraDto
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import retrofit2.Response
import javax.inject.Inject

class ComentarioFirestoreDataSourceImpl @Inject constructor(
    private val db: FirebaseFirestore
): ComentarioRemoteDataSource {
    override suspend fun getAllCommentarios(): List<ComentarioDto> {
        return emptyList()
    }

    override suspend fun getComentarioById(id: String): ComentarioDto {
        TODO("Not yet implemented")
    }

    override suspend fun getAllComentariosByObraId(id: String): List<ComentarioDto> {
        Log.d("COMENTS", "OBRAID  ${id}")
        return db.collection("comments").whereEqualTo("obraId",id).get().await().toObjects(
            ComentarioDto::class.java)

    }

    override suspend fun getAllComentariosByArtistaId(id: String): List<ComentarioDto> {
        return db.collection("comments").whereEqualTo("artistaId",id).get().await().toObjects(
            ComentarioDto::class.java)
    }

    override suspend fun createCommentario(comment: CreateCommentDto) {
        Log.d("User", "Comment DATA: ${comment.id}")
        Log.d("User", "Comment DATA: ${comment.comentario}")
        db.collection("comments").add(comment).await()
    }

    override suspend fun updateCommentario(
        id: String,
        comentario: CreateCommentDto
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteComentario(id: String): Response<Unit> {
        TODO("Not yet implemented")
    }
}