package com.example.myapplication.ViewModel

import com.example.myapplication.data.repository.AuthRepository
import com.example.myapplication.ui.InicioSesion.InicioSesionViewModel
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test

class IniciarSesionViewModelUnitTest {
    private lateinit var viewModel: InicioSesionViewModel
    private lateinit var authRepo: AuthRepository

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        authRepo = mockk()
        viewModel = InicioSesionViewModel(authRepo)
    }

    @Test
    fun InicioSesion_Succes_UpdateUI() =runTest{
        viewModel.updateCorreo("juan@gmail.com")
        viewModel.updateContraseña("jjjjjj")
        coEvery { authRepo.signIn(any(),any()) } returns Result.success(Unit)
        viewModel.loginButtonPressed()
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertThat(state.navegar).isTrue()
        assertThat(state.error).isEmpty()
        assertThat(state.mostrarError).isFalse()

    }

    @Test
    fun InicioSesion_Error_ShowError()=runTest {
        viewModel.updateCorreo("juan@gmail.com")
        viewModel.loginButtonPressed()

        val state = viewModel.uiState.value
        assertThat(state.navegar).isFalse()
        assertThat(state.error).isEqualTo("Por favor, complete todos los campos")
        assertThat(state.mostrarError).isTrue()

    }

    @Test
    fun InicioSesion_ErrorContraseña_ShowError()=runTest {
        viewModel.updateCorreo("juan@gmail.com")
        viewModel.updateContraseña("jjjjjj")
        coEvery { authRepo.signIn(any(),any()) } returns Result.failure(Exception("Credenciles invalidas"))
        viewModel.loginButtonPressed()
        advanceUntilIdle()
        val state = viewModel.uiState.value
        assertThat(state.navegar).isFalse()
        assertThat(state.error).isEqualTo("Credenciles invalidas")
        assertThat(state.mostrarError).isTrue()
    }

}