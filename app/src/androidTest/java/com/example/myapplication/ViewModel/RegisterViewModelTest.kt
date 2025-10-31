package com.example.myapplication.ViewModel


import android.util.Log
import com.example.myapplication.data.datasource.AuthRemoteDataSource
import com.example.myapplication.data.datasource.impl.Firestore.UserFirestoreDataSourceImpl
import com.example.myapplication.data.repository.AuthRepository
import com.example.myapplication.data.repository.UserRepository
import com.example.myapplication.ui.Register.RegisterViewModel
import com.google.common.truth.Truth.assertThat
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class RegisterViewModelTest {
    private lateinit var viewModel: RegisterViewModel

    @Before
    fun setUp(){
        try {
            Firebase.auth.useEmulator("10.0.2.2", 9099)
            Firebase.firestore.useEmulator("10.0.2.2", 8080)
        }catch (e: Exception){

        }
        val authRepo = AuthRepository(AuthRemoteDataSource(Firebase.auth))
        val userRepo = UserRepository(
            UserFirestoreDataSourceImpl(Firebase.firestore),
            AuthRepository(AuthRemoteDataSource(Firebase.auth)),
            FirebaseMessaging.getInstance())
        val scheduler = TestCoroutineScheduler()
        val dispatcher = StandardTestDispatcher(scheduler)
        Dispatchers.setMain(dispatcher)
        viewModel = RegisterViewModel(authRepo,userRepo, dispatcher)
    }

    @Test
    fun Register_Succes_CreateUser_UpdateUI() = runTest {

        viewModel.updateCorreo("juan@gmail.com")
        viewModel.updateContraseña("jjjjjj")
        viewModel.updateUsuario("Juan")
        viewModel.updateEdad("16")
        viewModel.updateProfesion("pintor")
        viewModel.updateBio("apasionado de la pintura")

        viewModel.registerButtonPressed()
        val navigate = viewModel.uiState.map { it.navegar }.first { it }
        val state = viewModel.uiState.value
        assertThat(state.navegar).isTrue()
        assertThat(state.mensajeError).isEmpty()
        assertThat(state.mostrarMensajeError).isFalse()

    }


    @Test
    fun Register_ErrorContraseñaCorta_ShowError() = runTest {

        viewModel.updateCorreo("juan@gmail.com")
        viewModel.updateContraseña("jjj")
        viewModel.updateUsuario("Juan")
        viewModel.updateEdad("16")
        viewModel.updateProfesion("pintor")
        viewModel.updateBio("apasionado de la pintura")

        viewModel.registerUserOnline()

        val state = viewModel.uiState.value
        Log.d("UAUAUA", state.mensajeError)
        assertThat(state.navegar).isFalse()
        assertThat(state.mensajeError).isEqualTo("La contraseña debe tener al menos 6 caracteres ")
        assertThat(state.mostrarMensajeError).isTrue()

    }

    @After
    fun tearUp() = runTest {
        val user = Firebase.auth.currentUser
        if (user != null){
            Firebase.auth.signOut()
        }
        user?.delete()
    }
}