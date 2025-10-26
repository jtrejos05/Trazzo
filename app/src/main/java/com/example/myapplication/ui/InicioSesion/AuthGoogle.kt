package com.example.myapplication.ui.InicioSesion

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import com.example.myapplication.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

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
        val client = getClient(activity)
        client.signOut().addOnCompleteListener {
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
            FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnSuccessListener { onSuccess() }
                .addOnFailureListener {
                    e -> onError(e.localizedMessage ?: "Error desconocido al autenticar con Firebase") }
        } catch (e: Exception) {
            onError( "Error al procesar resultado de Google : ${e.localizedMessage}")
        }
    }
}