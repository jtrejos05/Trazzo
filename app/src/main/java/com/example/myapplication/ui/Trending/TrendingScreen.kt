package com.example.myapplication.ui.Trending

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.myapplication.data.Obra
import com.example.myapplication.ui.TarjetaPublicacion
import com.example.myapplication.ui.Trending.TrendingViewModel

@Composable
fun TrendingScreen(viewmodel: TrendingViewModel,
                   obraPressed: (String) -> Unit = {},
                   modifier: Modifier = Modifier
) {
    val state by viewmodel.uiState.collectAsState()
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            Spacer(modifier = Modifier.height(8.dp))

            //Barra divisora
            HorizontalDivider(
                modifier = Modifier.padding(bottom = 10.dp),
                thickness = 1.dp,
                color = DividerDefaults.color
            )


            Spacer(modifier = Modifier.height(16.dp))



            // Lista de publicaciones
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                items(state.obras) { obra ->
                    TarjetaPublicacion(obra, {obraPressed(obra.obraId) })
                }
            }
        }
    }
}

