package com.example.myapplication.Repository

import com.example.myapplication.data.datasource.impl.Firestore.UserFirestoreDataSourceImpl
import com.example.myapplication.data.dtos.ArtistaDto
import com.example.myapplication.data.dtos.RegisterUserDto
import com.example.myapplication.data.repository.AuthRepository
import com.example.myapplication.data.repository.UserRepository
import com.google.android.gms.tasks.Tasks
import com.google.common.truth.Truth
import com.google.firebase.messaging.FirebaseMessaging
import io.mockk.coEvery
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class UserRepositoryUnitTest {

    private val mockDataSource = mockk<UserFirestoreDataSourceImpl>()
    private val authRepository = mockk<AuthRepository>()
    private val mockFirebaseMessaging = mockk<FirebaseMessaging>()

    private val repository = UserRepository(mockDataSource, authRepository, mockFirebaseMessaging)

    @Test
    fun getUserById_validId_SuccessResult() = runTest{

        val artista = ArtistaDto(
            id = "1",
            nombre = "Juan Pérez",
            correo = "juan.perez@example.com",
            fotousuario = "https://example.com/fotos/juan.jpg",
            contraseña = "123456",
            edad = "30",
            profesion = "Pintor",
            biografia = "Apasionado por el arte abstracto.",
            numSeguidores = 120,
            numSeguidos = 80,
            likes = 350,
            createdAt = "2025-01-01T10:00:00Z",
            updatedAt = "2025-10-30T10:00:00Z",
            obras = emptyList(),
            intereses = emptyList(),
            seSiguen = false
        )

        val currentUserId = "111"

        // Arrange
        coEvery { authRepository.currentUser?.uid ?: "" } returns currentUserId
        coEvery { mockDataSource.getUserById(artista.id,currentUserId) } returns artista

        //Act
        val result = repository.getUserById(artista.id)

        //Assert
        Truth.assertThat(result.isSuccess).isTrue()
        Truth.assertThat(result).isNotNull()
        Truth.assertThat(result.getOrNull()?.id).isEqualTo(artista.id)
        Truth.assertThat(result.getOrNull()?.usuario).isEqualTo(artista.nombre)
    }

    @Test
    fun getUserById_invalidId_returnFailure() = runTest {
        val invalidUserId = "999"
        val currentUserId = "111"

        // Arrange
        coEvery { authRepository.currentUser?.uid ?: "" } returns currentUserId
        coEvery { mockDataSource.getUserById(invalidUserId,currentUserId) } returns null

        // Act
        val result = repository.getUserById(invalidUserId)

        // Assert
        Truth.assertThat(result.isFailure).isTrue()
        Truth.assertThat(result.exceptionOrNull()?.message).isEqualTo("Usuario no encontrado")
    }

    @Test
    fun registerUser_validData_successResult() = runTest {
        val artista = ArtistaDto(
            id = "1",
            nombre = "Juan Pérez",
            correo = "juan.perez@example.com",
            fotousuario = "https://example.com/fotos/juan.jpg",
            contraseña = "123456",
            edad = "30",
            profesion = "Pintor",
            biografia = "Apasionado por el arte abstracto.",
            numSeguidores = 120,
            numSeguidos = 80,
            likes = 350,
            createdAt = "2025-01-01T10:00:00Z",
            updatedAt = "2025-10-30T10:00:00Z",
            obras = emptyList(),
            intereses = emptyList(),
            seSiguen = false
        )

        val fcmToken = "token"

        val registerUserDto= RegisterUserDto(
            nombre = artista.nombre,
            edad = artista.edad,
            profesion = artista.profesion,
            biografia = artista.biografia!!,
            id = artista.id,
            fotousuario = "",
            FCMToken = fcmToken)

        // Arrange
        every { mockFirebaseMessaging.token } returns Tasks.forResult(fcmToken)
        coEvery { mockDataSource.registerUser(registerUserDto,artista.id) } just runs
        coEvery { mockFirebaseMessaging.token.await() } returns fcmToken

        // Act
        val result = repository.registerUser(
            artista.nombre,
            artista.edad,
            artista.profesion,
            artista.biografia,
            artista.id
        )

        // Assert
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun registerUser_repeatedId_successResult() = runTest {
        val artista = ArtistaDto(
            id = "1",
            nombre = "Juan Pérez",
            correo = "juan.perez@example.com",
            fotousuario = "https://example.com/fotos/juan.jpg",
            contraseña = "123456",
            edad = "30",
            profesion = "Pintor",
            biografia = "Apasionado por el arte abstracto.",
            numSeguidores = 120,
            numSeguidos = 80,
            likes = 350,
            createdAt = "2025-01-01T10:00:00Z",
            updatedAt = "2025-10-30T10:00:00Z",
            obras = emptyList(),
            intereses = emptyList(),
            seSiguen = false
        )

        val fcmToken = "token"

        val registerUserDto= RegisterUserDto(
            nombre = artista.nombre,
            edad = artista.edad,
            profesion = artista.profesion,
            biografia = artista.biografia!!,
            id = artista.id,
            fotousuario = "",
            FCMToken = fcmToken)

        // Arrange
        coEvery { mockDataSource.registerUser(registerUserDto,artista.id) } throws(Exception("Usuario ya registrado"))
        coEvery { mockFirebaseMessaging.token.await() } returns fcmToken

        // Act
        val result = repository.registerUser(
            artista.nombre,
            artista.edad,
            artista.profesion,
            artista.biografia,
            artista.id
        )

        // Assert
        Truth.assertThat(result.isFailure).isTrue()
        Truth.assertThat(result.exceptionOrNull()?.message).equals("Usuario ya registrado")
    }
}