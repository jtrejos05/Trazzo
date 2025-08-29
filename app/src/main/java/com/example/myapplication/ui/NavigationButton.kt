package com.example.myapplication.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import com.example.myapplication.data.Obra
import com.example.myapplication.data.local.ProveedorObras
import com.example.myapplication.data.local.ProveedorComentarios
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.ui.theme.primaryLight


// Composable para el botón de navegación
@Composable
fun NavigationButton(
    text: String,
    icon: ImageVector,
    selected: Boolean,
    onClick: () -> Unit = {}
) {
    // Se crear una columna para contener el icono y el texto del botón
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable { onClick() }
            .padding(8.dp)
    ) {
        // Se crea un icono con el vector de imagen proporcionado
        Icon(
            imageVector = icon,
            contentDescription = text,
            tint = if (selected) MaterialTheme.colorScheme.primary else Color.Gray, // Se cambia el color del icono si está seleccionado
            modifier = Modifier.size(24.dp)
        )
        // Se crea un texto con el texto proporcionado
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall.copy(
                fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal // Se cambia el estilo del texto si está seleccionado
            ),
            color = if (selected) MaterialTheme.colorScheme.primary else Color.Gray // Se cambia el color del texto si está seleccionado
        )
    }
}
