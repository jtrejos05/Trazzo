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

class AuthRepositoryIntegrationTest {

    private val db = Firebase.firestore
    private val auth = Firebase.auth

    private lateinit var authRepository: AuthRepository

    @Before
    fun setUp() = runTest {
        try {
            db.useEmulator("10.0.2.2", 8080)
        } catch (e: Exception) {
            // Emulator already in use
        }

        authRepository = AuthRepository(AuthRemoteDataSource(auth))
    }

    @Test
    fun singUp_validEmail_successResult() = runTest {
        // Arrange
        val email = "userrrr@example.com"
        val password = "userpassword12"
        // Act
        val result = authRepository.signUp(email, password)
        Log.d("TESTEAR2", result.exceptionOrNull()?.message.toString())
        // Assert
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun singUp_invalidEmail_failureResult() = runTest {
        // Arrange
        val email = "useremail"
        val password = "userpassword12"
        // Act
        val result = authRepository.signUp(email, password)
        // Assert
        Truth.assertThat(result.isFailure).isTrue()
        Truth.assertThat(result.exceptionOrNull()?.message).isEqualTo("Correo electronico invalido")
    }

    @Test
    fun singIn_existingUser_successResult() = runTest {
        // Arrange
        val email = "existinguser@example.com"
        val password = "userpassword12"
        // Act
        authRepository.signUp(email, password)
        val result = authRepository.signIn(email, password)
        // Assert
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun singUp_unexistingUser_failureResult() = runTest {
        // Arrange
        val email = "nonexistinguser@example.com"
        val password = "userpassword12"
        // Act
        val result = authRepository.signIn(email, password)
        // Assert
        Log.d("TESTEAR", result.exceptionOrNull()?.message.toString())
        Truth.assertThat(result.isFailure).isTrue()
        Truth.assertThat(result.exceptionOrNull()?.message).isEqualTo("Credenciles invalidas")
    }

    @After
    fun tearUp() = runTest {
        val users = db.collection("users").get().await()
        users.documents.forEach { doc ->
            db.collection("users").document(doc.id).delete().await()
        }
    }
}