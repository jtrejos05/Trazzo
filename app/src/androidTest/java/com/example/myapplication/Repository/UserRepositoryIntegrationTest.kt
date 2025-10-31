package com.example.myapplication.Repository

import android.util.Log
import com.example.myapplication.data.datasource.AuthRemoteDataSource
import com.example.myapplication.data.datasource.impl.Firestore.UserFirestoreDataSourceImpl
import com.example.myapplication.data.dtos.RegisterUserDto
import com.example.myapplication.data.repository.AuthRepository
import com.example.myapplication.data.repository.UserRepository
import com.google.common.truth.Truth
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

class UserRepositoryIntegrationTest{

    private val db = Firebase.firestore
    private val auth = Firebase.auth

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

    private lateinit var userRepository: UserRepository

    @Before
    fun setUp() = runTest {
        try {
            db.useEmulator("10.0.2.2", 8080)
        } catch (e: Exception) {
            // Emulator already in use
        }

        userRepository = UserRepository(UserFirestoreDataSourceImpl(db), AuthRepository(AuthRemoteDataSource(auth)))

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
        // Arrange
        val id = "user_9"
        val expectedName = "Usuario, 9"
        // Act
        val result = userRepository.getUserById(id)
        // Assert
        Truth.assertThat(result.isSuccess).isTrue()
        Truth.assertThat(result).isNotNull()
        Truth.assertThat(result.getOrNull()?.usuario).isEqualTo(expectedName)
    }

    @Test
    fun getUserById_invalidId_returnFailure() = runTest {
        // Arrange
        val id = "user_999"
        // Act
        val result = userRepository.getUserById(id)
        // Assert
        Truth.assertThat(result.isFailure).isTrue()
        Truth.assertThat(result.exceptionOrNull()?.message).isEqualTo("Usuario no encontrado")
    }

    @Test
    fun registerUser_validData_successResult() = runTest {
        // Arrange
        val user = generateUser(20)
        // Act
        val result = userRepository.registerUser(user.nombre, user.edad, user.profesion, user.biografia, user.id)
        // Assert
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun registerUser_repeatedId_successResult() = runTest {
        // Arrange
        val user = generateUser(3)
        // Act
        val result = userRepository.registerUser(user.nombre, user.edad, user.profesion, user.biografia, user.id)
        // Assert
        Log.d("TESTEAR3", result.exceptionOrNull()?.message.toString())
        Truth.assertThat(result.isSuccess).isTrue()
    }


    @After
    fun tearUp() = runTest {
        val users = db.collection("users").get().await()
        users.documents.forEach { doc ->
            db.collection("users").document(doc.id).delete().await()
        }
    }
}