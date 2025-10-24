package com.example.myapplication.DataSource

import com.example.myapplication.data.datasource.impl.Firestore.UserFirestoreDataSourceImpl
import com.example.myapplication.data.dtos.RegisterUserDto
import com.google.common.truth.Truth
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

class FirebaseUserDataSourceTest {

    private val db = Firebase.firestore

    private fun generateUser(i: Int): RegisterUserDto = RegisterUserDto(
        nombre = "Usuario, $i",
        edad = "20",
        profesion = "Artista$i",
        biografia = "Biografia del usuario $i",
        id = "user_$i",
        fotousuario = "https://picsum.photos/200/200?random=$i",
        numSeguidores = i,
        numSeguidos = i + 2
    )

    private lateinit var dataSource: UserFirestoreDataSourceImpl
    @Before
    fun setUp() = runTest {
        try {
            db.useEmulator("10.0.2.2", 8080)
        } catch (e: Exception) {
            // Emulator already in use
        }


        dataSource = UserFirestoreDataSourceImpl(db)

        //replicables
        //autonomas

        val batch = db.batch()
        repeat(10) { i ->
            val user = generateUser(i)
            batch.set(
                db.collection("users").document(user.id), user
            )
        }
        batch.commit().await()

    }

    @Test
    fun getUserById_validId_correctUser() = runTest {


        //AAA
        //Arrange
        val validId = "user_5"
        val name = "Usuario, 5"
        //Act
        val result = dataSource.getUserById(validId, "")
        //Assert
        Truth.assertThat(result).isNotNull()
        Truth.assertThat(result!!.nombre).isEqualTo(name)
        Truth.assertThat(result!!.id).isEqualTo(validId)

        //delete

    }



    @Test
    fun getUserById_invalidId_null() = runTest {


        //AAA
        //Arrange
        val invalidId = "user_11"
        //Act
        val result = dataSource.getUserById(invalidId, "")
        //Assert
        Truth.assertThat(result).isNull()


    }
    @After
    fun tearUp() = runTest {
        val users = db.collection("users").get().await()
        users.documents.forEach { doc ->
            db.collection("users").document(doc.id).delete().await()
        }
    }

    /*
    @Test
    fun updateUserById_validId_correctUser() = runTest {
        db.useEmulator("10.0.2.2", 8080)

        val dataSource = UserFirestoreDataSourceImpl(db)

        //replicables
        //autonomas

        val batch = db.batch()
        repeat(10){
                i -> val user = generateUser(i)
            batch.set(db.collection("users").document(user.id), user)
        }
        batch.commit().await()

        //AAA
        //Arrange
        val validId = "user_5"
        val name = "Usuario, 5"
        //Act
        val result = dataSource.updateUser(RegisterUserDto(
            nombre= "Usuario, 5",
            edad = "20",
            profesion = "Artista6",
            biografia= "Biografia del usuario 6",
            id= "user_5",
            fotousuario = "https://picsum.photos/200/200?random=5",
            numSeguidores = 5,
            numSeguidos= 7
        ), validId)
        //Assert
        Truth.assertThat(result).isNotNull()
        Truth.assertThat(result.).isEqualTo(name)
        Truth.assertThat(result.id).isEqualTo(validId)

    }

    @Test
    fun updateUserById_validId_incorrectUser() = runTest {
        db.useEmulator("10.0.2.2", 8080)

        val dataSource = UserFirestoreDataSourceImpl(db)

        //replicables
        //autonomas

        val batch = db.batch()
        repeat(10){
                i -> val user = generateUser(i)
            batch.set(db.collection("users").document(user.id), user
            )
        }
        batch.commit().await()

        //AAA
        //Arrange
        val validId = "user_5"
        val name = "Usuario, 5"
        //Act
        val result = dataSource.getUserById(validId,"")
        //Assert
        Truth.assertThat(result).isNotNull()
        Truth.assertThat(result.nombre).isEqualTo(name)
        Truth.assertThat(result.id).isEqualTo(validId)

    }
*/
}