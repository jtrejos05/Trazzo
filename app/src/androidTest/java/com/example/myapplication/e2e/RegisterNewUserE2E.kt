package com.example.myapplication.e2e

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.example.myapplication.MainActivity
import com.example.myapplication.data.datasource.AuthRemoteDataSource
import com.example.myapplication.data.datasource.UserRemoteDataSource
import com.example.myapplication.data.datasource.impl.Firestore.UserFirestoreDataSourceImpl
import com.example.myapplication.data.repository.AuthRepository
import com.example.myapplication.data.repository.UserRepository
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
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
class RegisterNewUserE2E {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    var composeRule = createAndroidComposeRule<MainActivity>()

    private lateinit var authRepository: AuthRepository
    private lateinit var userRepository: UserRepository

    @Before
    fun setUp(){
        hiltRule.inject()
        try {
            Firebase.auth.useEmulator("10.0.2.2", 9099)
            Firebase.firestore.useEmulator("10.0.2.2", 8080)
        }catch (e: Exception){

        }

        val authRemoteDataSource = AuthRemoteDataSource(Firebase.auth)
        val userRemoteDataSource = UserFirestoreDataSourceImpl(Firebase.firestore)

        authRepository = AuthRepository(authRemoteDataSource)
        userRepository = UserRepository(userRemoteDataSource, authRepository, FirebaseMessaging.getInstance())

        runBlocking {
            authRepository.signUp("admin@admin.com","123456")
            authRepository.signOut()
        }
    }

    @Test
    fun navigate_fromStart_toLogin(){
        composeRule.onNodeWithTag("Login_button").performClick()
        composeRule.onNodeWithTag("InicioSesionScreen").assertIsDisplayed()
    }

    @Test
    fun registerUser_shortPassword_showMessage(){
        composeRule.onNodeWithTag("Register_button")
        composeRule.onNodeWithTag("FormCorreoElectronico").performTextInput("nuevoAdmin@gmail.com")
        composeRule.onNodeWithTag("FormContraseña").performTextInput("1234")
        composeRule.onNodeWithTag("FormNombreUsuario").performTextInput("pruebae2e")
        composeRule.onNodeWithTag("FormEdad").performTextInput("30")
        composeRule.onNodeWithTag("FormProfesion").performTextInput("Robot")
        composeRule.onNodeWithTag("FormBio").performTextInput("Soy un bot de prueba")

        composeRule.onNodeWithTag("BotonCrearCuenta").performClick()

        composeRule.onNodeWithTag("MensajeError").assertIsDisplayed()
    }

    @Test
    fun registerUser_usedEmail_showMessage(){
        composeRule.onNodeWithTag("Register_button")
        composeRule.onNodeWithTag("FormCorreoElectronico").performTextInput("admin@admin.com")
        composeRule.onNodeWithTag("FormContraseña").performTextInput("123456")
        composeRule.onNodeWithTag("FormNombreUsuario").performTextInput("pruebae2e")
        composeRule.onNodeWithTag("FormEdad").performTextInput("30")
        composeRule.onNodeWithTag("FormProfesion").performTextInput("Robot")
        composeRule.onNodeWithTag("FormBio").performTextInput("Soy un bot de prueba")

        composeRule.onNodeWithTag("BotonCrearCuenta").performClick()

        composeRule.onNodeWithTag("MensajeError").assertIsDisplayed()
    }

    @Test
    fun registerUser_allvalidInputs_navigatePrincipal(){
        composeRule.onNodeWithTag("Register_button")
        composeRule.onNodeWithTag("FormCorreoElectronico").performTextInput("adminPrueba@gmail.com")
        composeRule.onNodeWithTag("FormContraseña").performTextInput("123456")
        composeRule.onNodeWithTag("FormNombreUsuario").performTextInput("pruebae2e")
        composeRule.onNodeWithTag("FormEdad").performTextInput("30")
        composeRule.onNodeWithTag("FormProfesion").performTextInput("Robot")
        composeRule.onNodeWithTag("FormBio").performTextInput("Soy un bot de prueba")

        composeRule.onNodeWithTag("BotonCrearCuenta").performClick()

        composeRule.waitUntil(5000){
            composeRule.onAllNodesWithTag("PrincipalScreen").fetchSemanticsNodes().isNotEmpty()
        }

        composeRule.onNodeWithTag("PrincipalScreen").assertIsDisplayed()
    }

    @After
    fun tearDown() = runTest {
        val user = Firebase.auth.currentUser
        if(user != null) {
            Firebase.auth.signOut()
        }
        user?.delete()?.await()
    }
}
