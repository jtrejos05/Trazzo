package com.example.myapplication

import android.app.Application
import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApplication: Application() {

    override fun onCreate() {

        super.onCreate()

        Log.d("SIMULADOR", "ONCREATE")
        if (BuildConfig.DEBUG){
            Log.d("SIMULADOR", "DEBUG")
            //Firebase.firestore.useEmulator("10.0.2.2",8080)
            //Firebase.auth.useEmulator("10.0.2.2",9099)
        }
        Log.d("SIMULADOR", "FIN ONCREATE")
    }


}