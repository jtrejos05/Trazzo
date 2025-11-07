package com.example.myapplication.e2e

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextClearance
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
class LikesE2E {
    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    var composeRule = createAndroidComposeRule<MainActivity>()

    private lateinit var authRepository: AuthRepository
    private lateinit var userRepository: UserRepository
    private lateinit var obraRepository: ObraRepository
    private lateinit var comentarioRepository: ComentarioRepository

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
        val comentarioRemoteDataSource = ComentarioFirestoreDataSourceImpl(Firebase.firestore)



        authRepository = AuthRepository(authRemoteDataSource)
        userRepository = UserRepository(userRemoteDataSource, authRepository, FirebaseMessaging.getInstance())
        obraRepository = ObraRepository(obraRemoteDataSource)
        comentarioRepository = ComentarioRepository(comentarioRemoteDataSource, userRemoteDataSource, authRemoteDataSource)

        runBlocking {
            authRepository.signUp("prueba@prueba.com", "123456")
            authRepository.signIn("prueba@prueba.com", "123456")

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
            comentarioRepository.createComentario(
                comentario = "Comentario de prueba",
                calificacion = 3.0,
                artistaId = userId,
                obraId = obra.id,
                parentCommentId = null,
                commentId = null,
            )

            authRepository.signOut()
        }
    }

    @Test
    fun verifyLikesModification_CountNumberOfLikes(){
        composeRule.onNodeWithTag("Register_button").performClick()
        composeRule.onNodeWithTag("FormCorreoElectronico").performTextInput("nuevoAdmin@gmail.com")
        composeRule.onNodeWithTag("FormContraseña").performTextInput("1234")
        composeRule.onNodeWithTag("FormNombreUsuario").performTextInput("pruebae2e")
        composeRule.onNodeWithTag("FormEdad").performTextInput("30")
        composeRule.onNodeWithTag("FormProfesion").performTextInput("Robot")
        composeRule.onNodeWithTag("FormBio").performTextInput("Soy un bot de prueba")

        composeRule.onNodeWithTag("BotonCrearCuenta").performClick()

        composeRule.onNodeWithTag("MensajeError").assertTextEquals("La contraseña debe tener al menos 6 caracteres ")

        composeRule.onNodeWithTag("FormCorreoElectronico").performTextClearance()
        composeRule.onNodeWithTag("FormContraseña").performTextClearance()
        composeRule.onNodeWithTag("FormNombreUsuario").performTextClearance()
        composeRule.onNodeWithTag("FormEdad").performTextClearance()
        composeRule.onNodeWithTag("FormProfesion").performTextClearance()
        composeRule.onNodeWithTag("FormBio").performTextClearance()

        composeRule.onNodeWithTag("FormCorreoElectronico").performTextInput("adminPrueba@gmail.com")
        composeRule.onNodeWithTag("FormContraseña").performTextInput("123456")
        composeRule.onNodeWithTag("FormNombreUsuario").performTextInput("pruebae2e")
        composeRule.onNodeWithTag("FormEdad").performTextInput("30")
        composeRule.onNodeWithTag("FormProfesion").performTextInput("Robot")
        composeRule.onNodeWithTag("FormBio").performTextInput("Soy un bot de prueba")

        composeRule.onNodeWithTag("BotonCrearCuenta").performClick()

        composeRule.waitUntil(5000){
            composeRule.onAllNodesWithTag("InicioSesionScreen").fetchSemanticsNodes().isNotEmpty()
        }

        composeRule.onNodeWithTag("InicioSesionScreen").assertIsDisplayed()
        composeRule.onNodeWithTag("FormCorreoElectronico").performTextInput("adminPrueba@gmail.com")
        composeRule.onNodeWithTag("FormContraseña").performTextInput("123456")

        composeRule.onNodeWithTag("BotonIniciarSesion").performClick()

        composeRule.waitUntil(5000){
            composeRule.onAllNodesWithTag("PrincipalScreen").fetchSemanticsNodes().isNotEmpty()
        }

        composeRule.onNodeWithTag("PrincipalScreen").assertIsDisplayed()

        composeRule.onNodeWithTag("TarjetaPublicacion_Obra de prueba").performClick()

        composeRule.waitUntil(5000){
            composeRule.onAllNodesWithTag("VistaObrasScreen").fetchSemanticsNodes().isNotEmpty()
        }

        composeRule.onNodeWithTag("VistaObrasScreen").assertIsDisplayed()

        composeRule.onNodeWithTag("like").performClick()
        composeRule.onNodeWithTag("likesCount").assertTextEquals("1")

        composeRule.onNodeWithTag("like").performClick()
        composeRule.onNodeWithTag("likesCount").assertTextEquals("0")
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