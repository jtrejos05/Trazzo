package com.example.myapplication.DataSource

import com.example.myapplication.data.datasource.impl.Firestore.ComentarioFirestoreDataSourceImpl
import com.example.myapplication.data.dtos.CreateCommentDto
import com.example.myapplication.data.dtos.CreateCommentUserDto
import com.google.common.truth.Truth
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

class FirebaseCommentDataSourceTest {
    private val db = Firebase.firestore

    private fun generateUser(i: Int): CreateCommentUserDto = CreateCommentUserDto(
        nombre = "usuario_$i",
        fotousuario = "https://picsum.photos/200/200?random=$i"
    )

    private fun generateComment(i: Int): CreateCommentDto = CreateCommentDto(
        calificacion = 5.0,
        comentario = "Comentario de prueba $i",
        artistaId = "artista_$i",
        obraId = "obra_$i",
        parentComentarioId = null,
        id = "comentario_$i",
        artista = generateUser(i)
    )

    private lateinit var dataSource: ComentarioFirestoreDataSourceImpl
    @Before
    fun setUp() = runTest {
        try {
            db.useEmulator("10.0.2.2", 8080)
        } catch (e: Exception) {
            // Emulator already in use
        }


        dataSource = ComentarioFirestoreDataSourceImpl(db)

        //replicables
        //autonomas

        val batch = db.batch()
        repeat(10) { i ->
            val comment = generateComment(i)
            batch.set(
                db.collection("comments").document(comment.id ?: "idNOENCOntrado$i"), comment
            )
        }
        batch.commit().await()

    }

    @Test
    fun getComments_correctList() = runTest {

        //AAA
        //Arrange
        val comentario1 = "Comentario de prueba 1"
        val comentario5 = "Comentario de prueba 5"
        val comentario9 = "Comentario de prueba 9"
        //Act
        val result = dataSource.getAllCommentarios()
        //Assert
        Truth.assertThat(result).isNotNull()
        Truth.assertThat(result[0].comentario).isEqualTo(comentario1)
        Truth.assertThat(result[4].comentario).isEqualTo(comentario5)
        Truth.assertThat(result[8].comentario).isEqualTo(comentario9)


    }

    @Test
    fun getComment_validId_correctComment() = runTest {
        val validId = "comentario_3"
        val comentario3 = "Comentario de prueba 3"

        val result = dataSource.getComentarioById(validId)

        Truth.assertThat(result).isNotNull()
        Truth.assertThat(result!!.id).isEqualTo(validId)
        Truth.assertThat(result.comentario).isEqualTo(comentario3)
    }
    @Test
    fun getComment_invalidId_null() = runTest {
        val invalidId = "comentario_30"

        val result = dataSource.getComentarioById(invalidId)

        Truth.assertThat(result).isNull()

    }


    @After
    fun tearUp() = runTest {
        val users = db.collection("comments").get().await()
        users.documents.forEach { doc ->
            db.collection("comments").document(doc.id).delete().await()
        }
    }
}