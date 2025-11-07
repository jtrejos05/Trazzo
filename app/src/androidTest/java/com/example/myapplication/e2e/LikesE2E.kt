package com.example.myapplication.e2e
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
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

    // guardamos el uid del usuario “semilla” creado en setUp
    private var seedUserUid: String? = null

    @Before
    fun setUp() {
        hiltRule.inject()
        try {
            // Recomendado para estabilidad en E2E:
            // Firebase.auth.useEmulator("10.0.2.2", 9099)
            // Firebase.firestore.useEmulator("10.0.2.2", 8080)
            // FirebaseMessaging.getInstance().isAutoInitEnabled = false
        } catch (_: Exception) {}

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

            val userId = authRepository.currentUser?.uid ?: return@runBlocking
            seedUserUid = userId

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

    @OptIn(ExperimentalTestApi::class)
    private fun closeIme() {
        // cierra teclado / overlays que puedan bloquear taps
        composeRule.onRoot().performKeyInput { pressKey(Key.Back) }
    }

    @Test
    fun verifyLikesModification_CountNumberOfLikes() {
        // Registro -> botón
        composeRule.onNodeWithTag("Register_button", useUnmergedTree = true)
            .assertIsDisplayed()
            .assertHasClickAction()
            .performSemanticsAction(SemanticsActions.OnClick)

        // Form con contraseña corta
        composeRule.onNodeWithTag("FormCorreoElectronico").performTextInput("nuevoAdmin@gmail.com")
        composeRule.onNodeWithTag("FormContraseña").performTextInput("1234")
        composeRule.onNodeWithTag("FormNombreUsuario").performTextInput("pruebae2e")
        composeRule.onNodeWithTag("FormEdad").performTextInput("30")
        composeRule.onNodeWithTag("FormProfesion").performTextInput("Robot")
        composeRule.onNodeWithTag("FormBio").performTextInput("Soy un bot de prueba")

        closeIme()

        composeRule.onNodeWithTag("BotonCrearCuenta", useUnmergedTree = true)
            .assertHasClickAction()
            .performSemanticsAction(SemanticsActions.OnClick)

        // Espera robusta del mensaje (evita assertTextEquals frágil)
        composeRule.waitUntil(10_000) {
            composeRule.onAllNodes(
                hasTestTag("MensajeError") and hasText("La contraseña", substring = true),
                useUnmergedTree = true
            ).fetchSemanticsNodes().isNotEmpty()
        }

        // Limpia y registra usuario válido
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

        closeIme()

        composeRule.onNodeWithTag("BotonCrearCuenta", useUnmergedTree = true)
            .assertHasClickAction()
            .performSemanticsAction(SemanticsActions.OnClick)

        composeRule.waitUntil(10_000) {
            composeRule.onAllNodesWithTag("InicioSesionScreen", useUnmergedTree = true)
                .fetchSemanticsNodes().isNotEmpty()
        }

        composeRule.onNodeWithTag("InicioSesionScreen", useUnmergedTree = true).assertIsDisplayed()
        composeRule.onNodeWithTag("FormCorreoElectronico").performTextInput("adminPrueba@gmail.com")
        composeRule.onNodeWithTag("FormContraseña").performTextInput("123456")

        closeIme()

        composeRule.onNodeWithTag("BotonIniciarSesion", useUnmergedTree = true)
            .assertHasClickAction()
            .performSemanticsAction(SemanticsActions.OnClick)

        composeRule.waitUntil(10_000) {
            composeRule.onAllNodesWithTag("PrincipalScreen", useUnmergedTree = true)
                .fetchSemanticsNodes().isNotEmpty()
        }

        composeRule.onNodeWithTag("PrincipalScreen", useUnmergedTree = true).assertIsDisplayed()

        // Click en la tarjeta de la obra creada en setUp (id = "123")
        val cardTag = "TarjetaPublicacion_123"

        composeRule.waitUntil(15_000) {
            composeRule.onAllNodesWithTag(cardTag, useUnmergedTree = true)
                .fetchSemanticsNodes().isNotEmpty()
        }

        // si está en un Lazy*, asegúrate de hacer scroll
        composeRule.onNodeWithTag(cardTag, useUnmergedTree = true).performScrollTo()

        composeRule.onNodeWithTag(cardTag, useUnmergedTree = true)
            .assertIsDisplayed()
            .assertHasClickAction()
            .performSemanticsAction(SemanticsActions.OnClick)

        composeRule.waitUntil(10_000) {
            composeRule.onAllNodesWithTag("VistaObrasScreen", useUnmergedTree = true)
                .fetchSemanticsNodes().isNotEmpty()
        }
        composeRule.onNodeWithTag("VistaObrasScreen", useUnmergedTree = true).assertIsDisplayed()

        // Likes
        composeRule.onNodeWithTag("like", useUnmergedTree = true)
            .assertHasClickAction()
            .performSemanticsAction(SemanticsActions.OnClick)
        composeRule.onNodeWithTag("likesCount").assertTextEquals("1")

        composeRule.onNodeWithTag("like", useUnmergedTree = true)
            .assertHasClickAction()
            .performSemanticsAction(SemanticsActions.OnClick)
        composeRule.onNodeWithTag("likesCount").assertTextEquals("0")
    }

    @After
    fun tearDown() = runTest {
        // cierra sesión y borra usuario actual (adminPrueba) si aplica
        val current = Firebase.auth.currentUser
        if (current != null) {
            Firebase.auth.signOut()
            current.delete().await()
        }
        // borra el doc del usuario semilla creado en setUp por uid (no por email)
        seedUserUid?.let { uid ->
            Firebase.firestore.collection("users").document(uid).delete().await()
        }
        // si guardas también la obra por id "123", podrías limpiar aquí:
        // Firebase.firestore.collection("obras").document("123").delete().await()
    }
}
