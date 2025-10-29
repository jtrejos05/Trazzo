package com.example.myapplication.ViewModel

import com.example.myapplication.data.repository.AuthRepository
import com.example.myapplication.data.repository.ComentarioRepository
import com.example.myapplication.ui.CrearEditarComment.CrearEditarCommentViewModel
import com.example.myapplication.ui.InicioSesion.InicioSesionViewModel
import com.google.common.truth.Truth.assertThat
import com.google.firebase.auth.FirebaseUser
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test

class CrearEditarCommentViewModelUnitTest {

    private lateinit var viewModel: CrearEditarCommentViewModel
    private lateinit var commentRepo: ComentarioRepository
    private lateinit var authRepository: AuthRepository

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        authRepository = mockk()
        commentRepo = mockk()
        val fakeUser = mockk<FirebaseUser>()
        coEvery { fakeUser.uid } returns "123"
        coEvery {   authRepository.currentUser } returns fakeUser
        viewModel = CrearEditarCommentViewModel(commentRepo,authRepository)

    }

    @Test
    fun CreateComment_Success_Update() = runTest {
        viewModel.onCommentChange("comentario")
        viewModel.onCalificacionChange(5.0)
        coEvery { commentRepo.createComentario(
            any(),
            any(),
            any(),
            any(),
            any(),
            any()
        ) } returns Result.success(Unit)
        viewModel.createComment("obraId")
        advanceUntilIdle()
        val state = viewModel.uiState.value
        assertThat(state.navigateBack).isTrue()

    }

    @Test
    fun CreateComment_Error_updateUI() = runTest {
        viewModel.onCalificacionChange(5.0)
        coEvery { commentRepo.createComentario(
            any(),
            any(),
            any(),
            any(),
            any(),
            any()
        ) } returns Result.success(Unit)
        viewModel.createComment("obraId")
        advanceUntilIdle()
        val state = viewModel.uiState.value
        assertThat(state.navigateBack).isFalse()
        assertThat(state.error).isEqualTo("Por favor, escriba un comentario")

    }

}