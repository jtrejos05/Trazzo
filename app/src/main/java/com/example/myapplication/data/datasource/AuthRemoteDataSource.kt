package com.example.myapplication.data.datasource

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRemoteDataSource @Inject constructor(private val auth: FirebaseAuth) {

    val currentUser: FirebaseUser? = auth.currentUser

    suspend fun signIn(email: String, password: String): Unit{
        auth.signInWithEmailAndPassword(email,password).await()
    }

    suspend fun signUp(email: String, password: String): Unit{
        auth.createUserWithEmailAndPassword(email,password).await()
    }

    fun signOut(){
        auth.signOut()
    }

    suspend fun updateProfileImage(url: String): Unit{
        val uri = Uri.parse(url)
        currentUser?.updateProfile(
            UserProfileChangeRequest.Builder()
                .setPhotoUri(uri)
                .build()
        )
    }

}