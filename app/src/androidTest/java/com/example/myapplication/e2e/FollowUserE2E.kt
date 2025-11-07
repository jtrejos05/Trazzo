package com.example.myapplication.e2e

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.example.myapplication.MainActivity
import com.example.myapplication.data.datasource.AuthRemoteDataSource
import com.example.myapplication.data.datasource.impl.Firestore.ComentarioFirestoreDataSourceImpl
import com.example.myapplication.data.datasource.impl.Firestore.ObraFirestoreDataSourceImpl
import com.example.myapplication.data.datasource.impl.Firestore.UserFirestoreDataSourceImpl
import com.example.myapplication.data.dtos.CreateObraDto
import com.example.myapplication.data.repository.AuthRepository
import com.example.myapplication.data.repository.ComentarioRepository
import com.example.myapplication.data.repository.ObraRepository
import com.example.myapplication.data.repository.UserRepository
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class FollowUserE2E {
    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    var composeRule = createAndroidComposeRule<MainActivity>()

    private lateinit var authRepository: AuthRepository
    private lateinit var userRepository: UserRepository
    private lateinit var obraRepository: ObraRepository


    @Before
    fun setUp(){
        hiltRule.inject()
        try {
            //Firebase.auth.useEmulator("10.0.2.2", 9099)
            //Firebase.firestore.useEmulator("10.0.2.2", 8080)
        }catch (e: Exception){

        }

        val authRemoteDataSource = AuthRemoteDataSource(Firebase.auth)
        val userRemoteDataSource = UserFirestoreDataSourceImpl(Firebase.firestore)
        val obraRemoteDataSource = ObraFirestoreDataSourceImpl(Firebase.firestore)

        authRepository = AuthRepository(authRemoteDataSource)
        userRepository = UserRepository(userRemoteDataSource, authRepository, FirebaseMessaging.getInstance())
        obraRepository = ObraRepository(obraRemoteDataSource)

        runBlocking {
            authRepository.signUp("prueba@prueba.com", "123456")

            val userId = authRepository.currentUser?.uid?: return@runBlocking

            userRepository.registerUser(
                usuario = "prueba",
                edad = "20",
                profesion = "prueba",
                bio = "prueba",
                userId = userId
            )
            val obra = CreateObraDto(
                id = "123",
                artistaId = userId,
                obraIMG = "https://firebasestorage.googleapis.com/v0/b/trazzo-f1120.firebasestorage.app/o/obras%2Fthe-starry-night-1889(1).jpg?alt=media&token=f988edec-b0ad-44df-b949-ee80f907c40b",
                titulo = "Obra de prueba",
                descripcion = "Descripcion de prueba"
            )

            obraRepository.createObra(obra)
        }
    }

    @Test
    fun followUser_FollowedUserArt(){
        composeRule.onNodeWithTag("Login_button").performClick()

        composeRule.onNodeWithTag("InicioSesionScreen").assertIsDisplayed()
        composeRule.onNodeWithTag("FormCorreoElectronico").performTextInput("adminPrueba@gmail.com")
        composeRule.onNodeWithTag("FormContraseña").performTextInput("123456")

        composeRule.onNodeWithTag("BotonIniciarSesion").performClick()

        composeRule.waitUntil(5000){
            composeRule.onAllNodesWithTag("PrincipalScreen").fetchSemanticsNodes().isNotEmpty()
        }

        composeRule.onNodeWithTag("PrincipalScreen").assertIsDisplayed()

        composeRule.onNodeWithTag("Perfil_prueba").performClick()

        composeRule.waitUntil(5000){
            composeRule.onAllNodesWithTag("PerfilScreen").fetchSemanticsNodes().isNotEmpty()
        }

        composeRule.onNodeWithTag("PerfilScreen").assertIsDisplayed()

        composeRule.onNodeWithTag("TextNombre").assertTextEquals("prueba")
        composeRule.onNodeWithTag("TextProfesion").assertTextEquals("prueba")
        composeRule.onNodeWithTag("TextBiografia").assertTextEquals("prueba")

        composeRule.onNodeWithTag("BotonSeguir").performClick()

        composeRule.onNodeWithTag("Seguidores").assertTextEquals("1")

        composeRule.onNodeWithTag("trending").performClick()

        composeRule.waitUntil(20000) {
            composeRule.onAllNodesWithTag("TrendingScreen").fetchSemanticsNodes().isNotEmpty()
        }

        composeRule.onNodeWithTag("TrendingScreen").assertIsDisplayed()

        composeRule.onNodeWithTag("TarjetaPublicacion_prueba").performClick()

        composeRule.waitUntil(5000){
            composeRule.onAllNodesWithTag("PerfilScreen").fetchSemanticsNodes().isNotEmpty()
        }

        composeRule.onNodeWithTag("PerfilScreen").assertIsDisplayed()

        composeRule.onNodeWithTag("TextNombre").assertTextEquals("prueba")
        composeRule.onNodeWithTag("TextProfesion").assertTextEquals("prueba")
        composeRule.onNodeWithTag("TextBiografia").assertTextEquals("prueba")
    }


    @After
    fun tearDown() = runTest {
        val user = Firebase.auth.currentUser
        if(user != null) {
            Firebase.auth.signOut()
        }
        user?.delete()?.await()
        Firebase.firestore.collection("users").document("prueba@prueba.com").delete().await()
    }
}