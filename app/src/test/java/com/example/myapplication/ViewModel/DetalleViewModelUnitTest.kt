package com.example.myapplication.ViewModel

import com.example.myapplication.data.Obra
import com.example.myapplication.data.datasource.UserRemoteDataSource
import com.example.myapplication.data.repository.AuthRepository
import com.example.myapplication.data.repository.ComentarioRepository
import com.example.myapplication.data.repository.ObraRepository
import com.example.myapplication.data.repository.UserRepository
import com.example.myapplication.ui.Perfil.PerfilViewModel
import com.example.myapplication.ui.VistasObras.VistaObrasViewModel
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

class DetalleViewModelUnitTest {
    private lateinit var viewModel: VistaObrasViewModel
    private lateinit var authRepo: AuthRepository
    private lateinit var comentarioRepo: ComentarioRepository
    private lateinit var obraRepo: ObraRepository



    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        authRepo = mockk()
        comentarioRepo = mockk()
        obraRepo = mockk()
        val fakeUser = mockk<FirebaseUser>()
        coEvery { fakeUser.uid } returns "123"
        coEvery {   authRepo.currentUser } returns fakeUser
        viewModel = VistaObrasViewModel(obraRepo,comentarioRepo,authRepo)
    }

    @Test
    fun Vista_Likeado_succes_UpdateUi() = runTest {

        coEvery { obraRepo.sendOrDeleteLike(any(),any()) } returns Result.success(Unit)
        val fakeObra = Obra(
            "",
            "",
            "",
            "",
            "",
            emptyList(),
            "",
            "0",
            "",
            "",
            obraId = "obra",
            artistaId = "user",
            liked = false

        )

        viewModel.updateObra(fakeObra)
        viewModel.sendOrDeleteLike("obra", "user")
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertThat(state.obra!!.liked).isTrue()

    }
}