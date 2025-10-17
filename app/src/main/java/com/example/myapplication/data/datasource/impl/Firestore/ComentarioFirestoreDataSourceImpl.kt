package com.example.myapplication.data.datasource.impl.Firestore

import android.util.Log
import com.example.myapplication.data.datasource.ComentarioRemoteDataSource
import com.example.myapplication.data.dtos.ComentarioDto
import com.example.myapplication.data.dtos.CreateCommentDto
import com.example.myapplication.data.dtos.ObraDto
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.awaitAll
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

    override suspend fun getAllComentariosByObraId(id: String, userId: String): List<ComentarioDto> {
        Log.d("COMENTS", "OBRAID  ${id}")
        val commentsRef =db.collection("comments").whereEqualTo("obraId",id).get().await()
        val comments = commentsRef.toObjects(ComentarioDto::class.java)

        //Mirar si un comentario ya se le dio like


         if (userId.isNotEmpty()){
             val likedFlags = commentsRef.documents.map { d ->
                 d.reference.collection("likes").document(userId).get().await().exists()
             }
             Log.d("Like", "${likedFlags.size}")
             comments.forEachIndexed { i, c ->
                 if (likedFlags.isNotEmpty()) {
                     c.liked = likedFlags[i]
                     Log.d("Like", "hasLIKED")
                 }
             }
         }
        return comments
    }

    override suspend fun getAllComentariosByArtistaId(id: String): List<ComentarioDto> {
        return db.collection("comments").whereEqualTo("artistaId",id).get().await().toObjects(
            ComentarioDto::class.java)
    }

    override suspend fun createCommentario(comment: CreateCommentDto) {
        val docRef=db.collection("comments").document()  // Se crea un documento
        val comentario = comment
        comentario.id=docRef.id
        docRef.set(comment).await()
    }

    override suspend fun updateCommentario(
        id: String,
        comentario: CreateCommentDto
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteComentario(id: String): Response<Unit> {
        db.collection("comments").document(id).delete()
        return Response.success(Unit)
    }

    override suspend fun sendOrDeleteLike(commentId: String, userId: String) {
        val commentRef= db.collection("comments").document(commentId)
        val likesRef = commentRef.collection("likes").document(userId)

        db.runTransaction { transaction ->

            val likeDoc = transaction.get(likesRef)
            if (likeDoc.exists()){
                transaction.delete(likesRef)
                transaction.update(commentRef, "numLikes", FieldValue.increment(-1))
            }else{
                transaction.set(likesRef, mapOf("timestamp" to FieldValue.serverTimestamp()))
                transaction.update(commentRef, "numLikes", FieldValue.increment(1))
            }
        }
    }
}