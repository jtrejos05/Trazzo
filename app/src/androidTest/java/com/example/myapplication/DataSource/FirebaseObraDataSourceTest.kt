package com.example.myapplication.DataSource

import com.example.myapplication.data.datasource.impl.Firestore.ObraFirestoreDataSourceImpl
import com.example.myapplication.data.dtos.ArtistaObraDto
import com.example.myapplication.data.dtos.ObraDto
import com.example.myapplication.data.dtos.TagDto
import com.google.common.truth.Truth
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

class FirebaseObraDataSourceTest {

    private val db = Firebase.firestore

    private lateinit var dataSource: ObraFirestoreDataSourceImpl

    private fun generateUser(i:Int): ArtistaObraDto = ArtistaObraDto(
        nombre = "Artista $i",
        fotousuario = "https://picsum.photos/200/200?random=${i + 12}"
    )
    private fun generateTag(i: Int): TagDto = TagDto(
        tag = "Tag Nombre $i"
    )

    private fun generateObra(i: Int): ObraDto = ObraDto(
        id = "obra_$i",
        artistaId = "artista_$i",
        titulo = "Obra Titulo $i",
        descripcion = "Descripcion de la obra $i",
        obraIMG = "https://picsum.photos/200/200?random=$i",
        numComentarios = i,
        numLikes = i + 2,
        compartidos = i + 3,
        vistas = i + 4,
        createdAt = "2023-10-01T12:00:00Z",
        updatedAt = "2023-10-01T12:00:00Z",
        artista = generateUser(i),
        tags = listOf(generateTag(i)),
        liked = false
    )
    @Before
    fun setUp() = runTest {
        try {
            db.useEmulator("10.0.2.2", 8080)
        } catch (e: Exception) {
            // Emulator already in use
        }


        dataSource = ObraFirestoreDataSourceImpl(db)

        //replicables
        //autonomas

        val batch = db.batch()
        repeat(10) { i ->
            val obra = generateObra(i)
            batch.set(
                db.collection("obras").document(obra.id), obra
            )
        }
        batch.commit().await()

    }
    @Test
    fun getObras_correctList() = runTest {

        //AAA
        //Arrange
        val nombre1 = "Obra Titulo 1"
        val nombre5 = "Obra Titulo 5"
        val nombre9 = "Obra Titulo 9"
        //Act
        val result = dataSource.getObras()
        //Assert
        Truth.assertThat(result).isNotNull()
        Truth.assertThat(result[1].titulo).isEqualTo(nombre1)
        Truth.assertThat(result[5].titulo).isEqualTo(nombre5)
        Truth.assertThat(result[9].titulo).isEqualTo(nombre9)


    }

    @Test
    fun getObra_validId_correctObra() = runTest {
        val validId = "obra_3"
        val nombre = "Obra Titulo 3"

        val result = dataSource.getObraById(validId, "")

        Truth.assertThat(result).isNotNull()
        Truth.assertThat(result!!.id).isEqualTo(validId)
        Truth.assertThat(result.titulo).isEqualTo(nombre)
    }
    @Test
    fun getObra_invalidId_null() = runTest {
        val invalidId = "obra_30"

        val result = dataSource.getObraById(invalidId, "")

        Truth.assertThat(result).isNull()

    }

    @After
    fun tearUp() = runTest {
        val users = db.collection("obras").get().await()
        users.documents.forEach { doc ->
            db.collection("obras").document(doc.id).delete().await()
        }
    }

}