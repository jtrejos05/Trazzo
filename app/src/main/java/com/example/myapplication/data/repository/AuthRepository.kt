package com.example.myapplication.data.repository

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.myapplication.R
import com.example.myapplication.data.datasource.AuthRemoteDataSource
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject


class AuthRepository @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource
) {

    val currentUser: FirebaseUser?
        get() = authRemoteDataSource.currentUser

    suspend fun signIn(email: String, password:String): Result<Unit>{
        return try {
            authRemoteDataSource.signIn(email,password)
            Result.success(Unit)
        }
        catch (e: FirebaseAuthInvalidCredentialsException){
            Result.failure(Exception("Credenciles invalidas"))
        }
        catch (e: FirebaseAuthInvalidUserException){
            Result.failure(Exception("El usuario no existe"))
        }
        catch (e: Exception){
            Result.failure(Exception("Error al Iniciar Sesion"))
        }

    }
    suspend fun signUp(email: String,password: String): Result<Unit>{
        return try {
            authRemoteDataSource.signUp(email, password)
            Log.d("USER","UID  ${currentUser?.uid ?: "NULL"}")
            Result.success(Unit)
        }catch(e: FirebaseAuthWeakPasswordException){
            Result.failure(Exception("La contraseña es debil"))
        } catch (e: FirebaseAuthInvalidCredentialsException){
            Result.failure(Exception("Correo electronico invalido"))
        } catch(e: FirebaseAuthUserCollisionException){
            Result.failure(Exception("El correo ya esta registrado"))
        } catch(e: FirebaseNetworkException){
            Result.failure(Exception("Error al conectar con la aplicación"))
        } catch (e: FirebaseTooManyRequestsException){
            Result.failure(Exception("Se han hecho bastantes intentos en poco tiempo. Espere un poco"))
        } catch (e: Exception){
             Result.failure(e)
        }

    }
    fun signOut(){
        authRemoteDataSource.signOut()
    }

}