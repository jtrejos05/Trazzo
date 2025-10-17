package com.example.myapplication.ui.SeguidoresoSeguidosScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.myapplication.data.Artista
import com.example.myapplication.ui.utils.profileAssyncImage

@Composable
fun tarjetaUser(artista: Artista,
                modifier: Modifier= Modifier
){
    Row(modifier = modifier) {
        profileAssyncImage(artista.img,70)
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = artista.usuario
        )
    }
}
@Composable
fun SeguidoresoSeguidosScreen(viewmodel: SeguidoresoSeguidosViewModel, user: String, seguidos: Boolean, modifier: Modifier = Modifier){
    val state by viewmodel.uiState.collectAsState()
    LaunchedEffect(Unit) {
        viewmodel.getUsers(seguidos,user)
    }
    when{
        state.isLoading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                CircularProgressIndicator()
            }
        }
        state.errorMessage != null ->{
            Box(modifier= Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                Text(text= state.errorMessage ?: "Error desconocido")
            }
        }
        else -> {
            LazyColumn(
                modifier = modifier
            ) {
                items(state.lista){ user->
                    tarjetaUser(user)
                }
            }
        }
    }

}