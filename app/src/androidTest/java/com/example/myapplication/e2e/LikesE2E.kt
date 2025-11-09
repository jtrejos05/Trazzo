package com.example.myapplication.e2e
import android.util.Log
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
/*
    @Before
    fun setUp() {
        Log.d("Setup","ENtra al setup")
        hiltRule.inject()
        try {
            // Recomendado para estabilidad en E2E:
          //   Firebase.auth.useEmulator("10.0.2.2", 9099)
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
        Log.d("Setup","Va a entrar al runBlocking")
        runBlocking {
            Log.d("Setup","Entro al RunBlocking")
            try{

                var result  = authRepository.signUp("vamos@vamos.com", "123456")
                if(result.isSuccess){

                    Log.d("Setup","Se registro el usuario ")
                }else{

                    Log.d("Setup","No se registro el usuario ${result.exceptionOrNull()}")
                }

            }catch (e: Exception){

                Log.d("LikesE2E", "Usuario semilla ya existe: ${e.message}")
            }

            authRepository.signIn("vamos@vamos.com", "123456")
            Log.d("Setup","Inicio sesion")

            val userId = authRepository.currentUser?.uid ?: ""

            Log.d("Setup","Se registro el usuario ${userId}")
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
                titulo = "prueba",
                descripcion = "Descripcion de prueba"
            )
            obraRepository.createObra(obra)
            Log.d("Setup","Crea la obra")
            comentarioRepository.createComentario(
                comentario = "Comentario de prueba",
                calificacion = 3.0,
                artistaId = userId,
                obraId = obra.id,
                parentCommentId = null,
                commentId = null,
            )
            Log.d("Setup","Se creo comentario ")

            authRepository.signOut()
            Log.d("Setup","Se cerro sesion")
        }
    }
*/
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
        composeRule.onNodeWithTag("FormCorreoElectronico").performTextInput("nuevoAdmin16@gmail.com")
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

        composeRule.onNodeWithTag("FormCorreoElectronico").performTextInput("adminPrueba16@gmail.com")
        composeRule.onNodeWithTag("FormContraseña").performTextInput("123456")
        composeRule.onNodeWithTag("FormNombreUsuario").performTextInput("pruebae2e")
        composeRule.onNodeWithTag("FormEdad").performTextInput("30")
        composeRule.onNodeWithTag("FormProfesion").performTextInput("Robot")
        composeRule.onNodeWithTag("FormBio").performTextInput("Soy un bot de prueba")

        closeIme()

        composeRule.onNodeWithTag("BotonCrearCuenta", useUnmergedTree = true)
            .assertHasClickAction()
            .performSemanticsAction(SemanticsActions.OnClick)

        composeRule.waitUntil(30_000) {
            composeRule.onAllNodesWithTag("InicioSesionScreen", useUnmergedTree = true)
                .fetchSemanticsNodes().isNotEmpty()
        }

        composeRule.onNodeWithTag("InicioSesionScreen", useUnmergedTree = true).assertIsDisplayed()
        composeRule.onNodeWithTag("FormCorreoElectronico").performTextInput("adminPrueba16@gmail.com")
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
        val cardTag = "TarjetaPublicacion_prueba"

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
        composeRule.onNodeWithTag("VistaObrasScreen", useUnmergedTree = true).assertIsDisplayed().printToLog("VISTA_OBRAS_NODE")


        // Likes
        // Dentro del test, cuando ya estás en VistaObrasScreen:

        val commentLikeButtonTag = "CommentLikeButton_0"
        composeRule.onRoot().printToLog("E2E_TREE")
        val commentLikesCountTag = "CommentLikes_0"
        composeRule.onRoot().printToLog("E2E_TREE")
/*
        composeRule
            .onNodeWithTag("CommentsList")
            .performScrollToIndex(0) // Asegura que el primero esté visible
            */

/*
        composeRule
            .onNodeWithText("Texto del primer elemento")
            .performClick()


        // Haz scroll en la lista hasta el comentario
        composeRule.onNodeWithTag("CommentsList", useUnmergedTree = true)
            .performScrollToNode(hasTestTag(commentLikesCountTag))
        composeRule.onRoot().printToLog("E2E_TREE")
        */
        Thread.sleep(10000)
        // Imprime solo la lista

        composeRule.onNodeWithTag("CommentsList", useUnmergedTree = true)
            .printToLog("COMMENTS_LIST")



// Espera a que aparezca el comentario
        composeRule.waitUntil(15_000) {
            composeRule.onAllNodesWithTag(commentLikesCountTag, useUnmergedTree = true)
                .fetchSemanticsNodes().isNotEmpty()
        }

        composeRule.onNodeWithTag(commentLikesCountTag, useUnmergedTree = true).performScrollTo()
// (Opcional) comprueba valor inicial, ej. "1"
        composeRule.onNodeWithTag(commentLikesCountTag, useUnmergedTree = true)
            .assertTextEquals("1")   // si sabes que arranca en 1
            .printToLog("COMMENT_LIKES_BEFORE") // para ver qué valor tiene


// Click en el corazón del comentario
        composeRule.onNodeWithTag(commentLikeButtonTag, useUnmergedTree = true)
            .assertHasClickAction()
            .performClick()
        composeRule.onNodeWithTag(commentLikeButtonTag, useUnmergedTree = true)
            .printToLog("COMMENTS_LIST1")

// Espera a que el contador cambie a "2"
        composeRule.waitUntil(10_000) {
            composeRule.onAllNodes(
                hasTestTag(commentLikesCountTag) and hasText("2", substring = false),
                useUnmergedTree = true
            ).fetchSemanticsNodes().isNotEmpty()
        }

// Segundo click
        composeRule.onNodeWithTag(commentLikeButtonTag, useUnmergedTree = true)
            .assertHasClickAction()
            .performClick()

        composeRule.waitUntil(10_000) {
            composeRule.onAllNodes(
                hasTestTag(commentLikesCountTag) and hasText("1", substring = false),
                useUnmergedTree = true
            ).fetchSemanticsNodes().isNotEmpty()
        }

    }
/*
    @After
    fun tearDown() = runTest {
        // cierra sesión y borra usuario actual (adminPrueba) si aplica
        val current = Firebase.auth.currentUser
        if (current != null) {
            Firebase.auth.signOut()
            current.delete().await()
        }

        // si guardas también la obra por id "123", podrías limpiar aquí:
        // Firebase.firestore.collection("obras").document("123").delete().await()
    }
    */

}
