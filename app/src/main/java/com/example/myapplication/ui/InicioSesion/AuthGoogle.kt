package com.example.myapplication.ui.InicioSesion

import android.app.Activity
import android.content.Intent
import android.os.Looper
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import com.example.myapplication.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import android.content.pm.PackageManager
import android.util.Base64
import java.security.MessageDigest

fun logAppSigningInfo(context: android.content.Context) {
    try {
        val pm = context.packageManager
        val pkg = context.packageName
        val info = pm.getPackageInfo(pkg, PackageManager.GET_SIGNING_CERTIFICATES)
        val signer = info.signingInfo?.apkContentsSigners?.first() ?: null
        val cert = signer?.toByteArray()

        fun digest(alg: String): String {
            val md = MessageDigest.getInstance(alg)
            val d = md.digest(cert)
            return d.joinToString(":") { b -> "%02X".format(b) }
        }

        android.util.Log.d("AuthGoogle", "APK packageName: $pkg")
        android.util.Log.d("AuthGoogle", "APK SHA-1:  " + digest("SHA-1"))
        android.util.Log.d("AuthGoogle", "APK SHA-256:" + digest("SHA-256"))
        android.util.Log.d("AuthGoogle", "APK cert (base64): " + Base64.encodeToString(cert, Base64.NO_WRAP))
    } catch (e: Exception) {
        android.util.Log.e("AuthGoogle", "No pude obtener firmas del APK", e)
    }
}

object AuthGoogle {

    private const val TAG = "AuthGoogle"

    fun getClient(activity: Activity): GoogleSignInClient {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            // IMPORTANTÍSIMO: este ID debe ser el WEB CLIENT ID del google-services.json
            .requestIdToken("242022315495-lujiuj5j5isc7kvh6afb5qtkh7vhurla.apps.googleusercontent.com")
            .requestEmail()
            .build()
        Log.d("AuthGoogle", "WEB CLIENT ID usado: " + activity.getString(R.string.default_web_client_id))
        return GoogleSignIn.getClient(activity, gso)
    }

    fun signIn(
        activity: Activity,
        launcher: ActivityResultLauncher<Intent>
    ) {
        Log.d(TAG, "signIn(): preparando intent de Google")
        val client = getClient(activity)
        // Opcional: limpiar sesión previa. Si te causa bucles, elimínalo.
        client.signOut().addOnCompleteListener {
            Log.d(TAG, "signIn(): lanzando intent")
            launcher.launch(client.signInIntent)
        }
    }

    fun handleResult(
        data: Intent?,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val account = task.getResult(com.google.android.gms.common.api.ApiException::class.java) // ✅
            val idToken = account.idToken
            if (idToken.isNullOrBlank()) {
                onError("El token de Google llegó vacío. Revisa default_web_client_id (Web client ID).")
                return
            }

            val credential = GoogleAuthProvider.getCredential(idToken, null)
            val auth = FirebaseAuth.getInstance()

            auth.signInWithCredential(credential)
                .addOnSuccessListener { result ->
                    val user = result.user ?: auth.currentUser
                    if (user == null) {
                        onError("Autenticación completada, pero no se pudo obtener el usuario actual.")
                        return@addOnSuccessListener
                    }

                    val firestore = FirebaseFirestore.getInstance()
                    val usuarioRef = firestore.collection("users").document(user.uid)

                    usuarioRef.get()
                        .addOnSuccessListener { doc ->
                            if (!doc.exists()) {
                                val nuevoUsuario = mapOf(
                                    "uid" to user.uid,
                                    "nombre" to (user.displayName ?: ""),
                                    "correo" to (user.email ?: ""),
                                    "foto" to (user.photoUrl?.toString() ?: ""),
                                    "usuario" to (user.displayName ?: "Usuario"),
                                    "edad" to 0,
                                    "profesion" to "",
                                    "biografia" to "",
                                    "seguidores" to 0,
                                    "siguiendo" to 0,
                                    "likes" to 0,
                                    "intereses" to emptyList<String>(),
                                    "creadoEn" to FieldValue.serverTimestamp()
                                )
                                usuarioRef.set(nuevoUsuario)
                                    .addOnSuccessListener { onSuccess() }
                                    .addOnFailureListener { e ->
                                        Log.e("AuthGoogle", "Error al crear usuario", e)
                                        onError("No se pudo crear el usuario en Firestore: ${e.localizedMessage}")
                                    }
                            } else {
                                onSuccess()
                            }
                        }
                        .addOnFailureListener { e ->
                            Log.e("AuthGoogle", "Error al verificar usuario", e)
                            onError("No se pudo verificar el usuario en Firestore: ${e.localizedMessage}")
                        }
                }
                .addOnFailureListener { e ->
                    Log.e("AuthGoogle", "Fallo signInWithCredential", e) // ✅ stacktrace
                    onError(e.localizedMessage ?: "Error autenticando con Firebase.")
                }
        } catch (e: com.google.android.gms.common.api.ApiException) {
            Log.e("AuthGoogle", "ApiException GoogleSignIn status=${e.statusCode}", e) // ✅
            onError("No se pudo iniciar sesión con Google (código ${e.statusCode}).")
        } catch (e: Exception) {
            Log.e("AuthGoogle", "Excepción inesperada en handleResult", e) // ✅
            onError("Error procesando el resultado de Google: ${e.localizedMessage}")
        }
    }

}
