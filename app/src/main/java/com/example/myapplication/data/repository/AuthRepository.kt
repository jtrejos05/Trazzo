package com.example.myapplication.data.repository

import com.example.myapplication.data.datasource.AuthRemoteDataSource
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject


class AuthRepository @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource
) {

    val currentUser: FirebaseUser?= authRemoteDataSource.currentUser

    suspend fun signIn(email: String, password:String): Result<Unit>{
        return try {
            authRemoteDataSource.signIn(email,password)
            Result.success(Unit)
        }
        catch (e: FirebaseAuthInvalidCredentialsException){
            Result.failure(Exception("Credenciales Incorrectas"))
        }
        catch (e: FirebaseAuthInvalidUserException){
            Result.failure(Exception("El usuario no existe"))
        }
        catch (e: Exception){
            Result.failure(Exception("Error al Iniciar Sesion"))
        }

    }
    suspend fun signUp(email: String,password: String): Result<Unit>{
        try {
            authRemoteDataSource.signUp(email,password)
            return Result.success(Unit)
        }catch (e: Exception){
            return Result.failure(e)
        }

    }
    fun signOut(){
        authRemoteDataSource.signOut()
    }

}