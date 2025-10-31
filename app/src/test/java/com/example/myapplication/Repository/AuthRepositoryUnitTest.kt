package com.example.myapplication.Repository

import android.util.Log
import com.example.myapplication.data.datasource.AuthRemoteDataSource
import com.example.myapplication.data.datasource.impl.Firestore.UserFirestoreDataSourceImpl
import com.example.myapplication.data.repository.AuthRepository
import com.google.common.truth.Truth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import io.mockk.coEvery
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.test.runTest
import org.junit.Test

class AuthRepositoryUnitTest {

    private val mockDataSource = mockk<AuthRemoteDataSource>()

    private val authRepository = AuthRepository(mockDataSource)

    @Test
    fun singUp_validEmail_successResult() = runTest {
        val email = "userrrr@example.com"
        val password = "userpassword12"

        // Arrange
        coEvery { mockDataSource.signUp(email, password) } just runs

        // Act
        val result = authRepository.signUp(email, password)

        // Assert
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun singUp_invalidEmail_failureResult() = runTest {
        val email = "userrrr@example.com"
        val password = "userpassword12"

        // Arrange
        coEvery { mockDataSource.signUp(email, password) } throws Exception("Correo electronico invalido")
        // Act
        val result = authRepository.signUp(email, password)
        // Assert
        Truth.assertThat(result.isFailure).isTrue()
        Truth.assertThat(result.exceptionOrNull()?.message).isEqualTo("Correo electronico invalido")
    }

    @Test
    fun singIn_existingUser_successResult() = runTest {
        val email = "existinguser@example.com"
        val password = "userpassword12"

        // Arrange
        coEvery { mockDataSource.signIn(email,password) } just runs
        // Act
        val result = authRepository.signIn(email, password)
        // Assert
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun singUp_unexistingUser_failureResult() = runTest {
        val email = "existinguser@example.com"
        val password = "userpassword12"

        // Arrange
        coEvery { mockDataSource.signIn(email,password) } throws Exception("Credenciales invalidas")
        // Act
        val result = authRepository.signIn(email, password)
        // Assert
        Truth.assertThat(result.isFailure).isTrue()
        Truth.assertThat(result.exceptionOrNull()?.message).isEqualTo("Credenciales invalidas")
    }
}