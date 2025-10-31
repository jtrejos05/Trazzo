package com.example.myapplication.e2e

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.example.myapplication.MainActivity
import com.example.myapplication.data.datasource.AuthRemoteDataSource
import com.example.myapplication.data.datasource.impl.Firestore.UserFirestoreDataSourceImpl
import com.example.myapplication.data.repository.AuthRepository
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
class FollowUserE2E {
    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    var composeRule = createAndroidComposeRule<MainActivity>()

    private lateinit var authRepository: AuthRepository
    private lateinit var userRepository: UserRepository

    @Before
    fun setUp(){
        hiltRule.inject()
        try {
            Firebase.auth.useEmulator("10.0.2.2", 9099)
            Firebase.firestore.useEmulator("10.0.2.2", 8080)
        }catch (e: Exception){

        }

        val authRemoteDataSource = AuthRemoteDataSource(Firebase.auth)
        val userRemoteDataSource = UserFirestoreDataSourceImpl(Firebase.firestore)

        authRepository = AuthRepository(authRemoteDataSource)
        userRepository = UserRepository(userRemoteDataSource, authRepository, FirebaseMessaging.getInstance())

        runBlocking {
            authRepository.signIn("prueba@prueba.com", "123456")
            authRepository.signIn("prueba@prueba.com", "123456")

            val userId = authRepository.currentUser?.uid?: return@runBlocking

            userRepository.registerUser(
                usuario = "prueba",
                edad = "20",
                profesion = "prueba",
                bio = "prueba",
                userId = userId
            )
        }
    }

    @Test
    fun followUser_FollowedUserArt(){

    }


    @After
    fun tearDown() = runTest {
        val user = Firebase.auth.currentUser
        if(user != null) {
            Firebase.auth.signOut()
        }
        user?.delete()?.await()
        Firebase.firestore.collection("users").document("prueba@prueba.com").delete().await()
    }
}