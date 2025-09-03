package com.example.myapplication.data.repository

import com.example.myapplication.data.datasource.AuthRemoteDataSource
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject


class AuthRepository @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource
) {

    val currentUser: FirebaseUser?= authRemoteDataSource.currentUser

    suspend fun signIn(email: String, password:String){
        authRemoteDataSource.signIn(email,password)
    }
    suspend fun signUp(email: String,password: String){
        authRemoteDataSource.signUp(email,password)
    }
    fun signOut(){
        authRemoteDataSource.signOut()
    }

}