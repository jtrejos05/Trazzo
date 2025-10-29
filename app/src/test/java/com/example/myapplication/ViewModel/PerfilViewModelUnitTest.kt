package com.example.myapplication.ViewModel

import com.example.myapplication.data.Artista
import com.example.myapplication.data.datasource.UserRemoteDataSource
import com.example.myapplication.data.repository.AuthRepository
import com.example.myapplication.data.repository.ComentarioRepository
import com.example.myapplication.data.repository.ObraRepository
import com.example.myapplication.data.repository.UserRepository
import com.example.myapplication.ui.InicioSesion.InicioSesionViewModel
import com.example.myapplication.ui.Perfil.PerfilViewModel
import com.google.common.truth.Truth.assertThat
import com.google.firebase.auth.FirebaseUser
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test

class PerfilViewModelUnitTest {
    private lateinit var viewModel: PerfilViewModel
    private lateinit var authRepo: AuthRepository
    private lateinit var comentarioRepo: ComentarioRepository
    private lateinit var userRepo: UserRepository
    private lateinit var obraRepo: ObraRepository

    private lateinit var UserRemoteDataSource: UserRemoteDataSource

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        authRepo = mockk()
        comentarioRepo = mockk()
        userRepo = mockk()
        obraRepo = mockk()
        UserRemoteDataSource = mockk()
        val scheduler = TestCoroutineScheduler()
        val dispatcher = StandardTestDispatcher(scheduler)
        Dispatchers.setMain(dispatcher)
        val fakeUser = mockk<FirebaseUser>()
        coEvery { fakeUser.uid } returns "123"
        coEvery {   authRepo.currentUser } returns fakeUser
        viewModel = PerfilViewModel(authRepo,comentarioRepo,userRepo,obraRepo,dispatcher)
    }

    @Test
    fun Perfil_SeSiguen_succes_UpdateUi() = runTest {

        coEvery { UserRemoteDataSource.followOrUnfollowUser(any(),any()) }

        coEvery { userRepo.followOrUnfollowUser(any(),any()) } returns Result.success(Unit)
        viewModel.followOrUnfollowUser("")
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertThat(state.usuario.seSiguen).isTrue()

    }

    @Test
    fun Perfil_YaNoSeSiguen_Success_UpdateUi() = runTest {
        coEvery { UserRemoteDataSource.followOrUnfollowUser(any(),any()) }
        coEvery { userRepo.followOrUnfollowUser(any(),any()) } returns Result.success(Unit)
        viewModel.updateArtista(
            Artista(
                "",
                "",
                "",
                "",
                "",
                0,
                "",
                "",
                0,
                0,
                0,
                listOf(),
                listOf(""),
                true
            )
        )
        viewModel.followOrUnfollowUser("")
        advanceUntilIdle()
        val state = viewModel.uiState.value
        assertThat(state.usuario.seSiguen).isFalse()
    }
}