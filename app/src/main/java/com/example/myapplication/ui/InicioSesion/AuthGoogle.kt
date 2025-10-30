package com.example.myapplication.ui.InicioSesion

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import com.example.myapplication.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

object AuthGoogle {


    fun getClient(activity: Activity): GoogleSignInClient {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(activity.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        return GoogleSignIn.getClient(activity, gso)
    }

    fun signIn(
        activity: Activity,
        launcher: ActivityResultLauncher<Intent>
    ) {
        Log.d("AuthGoogle", "Entrando a signIn()")
        val client = getClient(activity)
        client.signOut().addOnCompleteListener {
            Log.d("AuthGoogle", "Lanzando intent de Google SignIn...")
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
            val account = task.result
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
            val auth = FirebaseAuth.getInstance()
            Log.d("AuthGoogle", "Entrando a handleResult...")

            auth.signInWithCredential(credential)
                .addOnSuccessListener {
                    Log.d("AuthGoogle", "Autenticación con Firebase exitosa")
                    val user = auth.currentUser
                    if (user != null) {
                        val firestore = FirebaseFirestore.getInstance()
                        val usuarioRef = firestore.collection("users").document(user.uid)

                        usuarioRef.get()
                            .addOnSuccessListener { doc ->
                                Log.d("AuthGoogle", "Documento de usuario existe: ${doc.exists()}")
                                if (!doc.exists()) {
                                    Log.d("AuthGoogle", "Creando usuario nuevo en Firestore...")
                                    // Registrar el usuario si no existe
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
                                        "intereses" to listOf<String>(),
                                        "creadoEn" to FieldValue.serverTimestamp()
                                    )

                                    usuarioRef.set(nuevoUsuario)
                                        .addOnSuccessListener {
                                            Log.d("AuthGoogle", "Usuario creado exitosamente en Firestore")
                                            // ✅ Esperar un momento para asegurar que se sincronice
                                            android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
                                                onSuccess()
                                            }, 500) // Espera 500ms para sincronización
                                        }
                                        .addOnFailureListener { e ->
                                            Log.e("AuthGoogle", "Error al crear usuario: ${e.message}")
                                            onError("Error al crear usuario: ${e.localizedMessage}")
                                        }
                                } else {
                                    // Ya existe el documento, continuar inmediatamente
                                    Log.d("AuthGoogle", "Usuario ya existente, continuando...")
                                    onSuccess()
                                }
                            }
                            .addOnFailureListener { e ->
                                Log.e("AuthGoogle", "Error al verificar usuario: ${e.message}")
                                onError("Error al verificar usuario: ${e.localizedMessage}")
                            }
                    } else {
                        onError("No se pudo obtener el usuario autenticado")
                    }
                }
                .addOnFailureListener { e ->
                    Log.e("AuthGoogle", "Error en autenticación: ${e.message}")
                    onError(e.localizedMessage ?: "Error desconocido al autenticar con Firebase")
                }
        } catch (e: Exception) {
            Log.e("AuthGoogle", "Excepción en handleResult: ${e.message}")
            onError("Error al procesar resultado de Google: ${e.localizedMessage}")
        }
    }
}