package com.example.myapplication.data.datasource

import android.net.Uri
import android.util.Log
import com.example.myapplication.data.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRemoteDataSource @Inject constructor(private val auth: FirebaseAuth) {

    val currentUser: FirebaseUser?
        get() = auth.currentUser

    suspend fun signIn(email: String, password: String): Unit{
        auth.signInWithEmailAndPassword(email,password).await()
    }

    suspend fun signUp(email: String, password: String): Unit{
        auth.createUserWithEmailAndPassword(email,password).await()
        Log.d("User","Remote Data Source ${currentUser!!.uid}")
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