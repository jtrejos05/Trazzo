import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import com.example.myapplication.data.Obra
import com.example.myapplication.data.local.ProveedorObras
import com.example.myapplication.ui.TarjetaPublicacion


@Composable
fun ProfileScreen(
    obras: List<Obra>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .background(Color(0xFF00C6A9)) // teal gradient color
        )
        Spacer(modifier = Modifier.height(-60.dp))
        Surface(
            shape = CircleShape,
            modifier = Modifier.size(100.dp),
            shadowElevation = 6.dp
        ) {
            Image(
                painter = painterResource(R.drawable.maria_foto), // replace with profile picture
                contentDescription = "Profile Picture",
                contentScale = ContentScale.Crop

            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                "Juantrev05",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Icon(
                painter = painterResource(R.drawable.edit), // add an edit icon in your drawable
                contentDescription = "Edit username",
                modifier = Modifier
                    .size(18.dp)
                    .padding(start = 4.dp),
                tint = Color.Gray
            )

        }
        Text(
            "@juantrev05",
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier.padding(8.dp)
        )
        // Profile info
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {


                        Text(
                "Artista en Trazzo, explorando diferentes formas de expresión creativa.",
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.padding(8.dp)
            )

            // Tags (chips)
            Row(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.padding(8.dp)
            ) {
                Chip("Artista Emergente")
                Chip("Arte Digital")
                Chip("Pintura")
                Chip("Nuevo Artista")
            }

            // Stats
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                StatItem("24", "Posts")
                StatItem("1346", "Seguidores")
                StatItem("416", "Siguiendo")
                StatItem("2459", "Likes")
            }

            Button(
                onClick = { /* Share profile action */ },
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00C6A9))
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.shareicon),
                        contentDescription = "Share Profile",
                        tint = Color.White,
                                modifier = Modifier.size(20.dp)
                    )
                    Text("Compartir Perfil")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text("Mis Creaciones", fontWeight = FontWeight.Bold, fontSize = 18.sp)

            // Grid of artworks
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(8.dp),
                contentPadding = PaddingValues(4.dp)
            ) {
                items(obras) { Obra ->
                    TarjetaPublicacion(Obra)
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ProfileScreenpreview(){
    ProfileScreen(ProveedorObras.obras)
}
@Composable
fun Chip(text: String) {
    Surface(
        shape = RoundedCornerShape(20.dp),
        color = Color(0xFFE0F7FA),
        modifier = Modifier.padding(2.dp)
    ) {
        Text(
            text = text,
            fontSize = 12.sp,
            color = Color(0xFF00796B),
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}

@Composable
@Preview(showBackground=true)
fun chipPreview(){
    Chip("Hola")
}

@Composable
fun StatItem(value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(value, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        Text(label, fontSize = 12.sp, color = Color.Gray)
    }
}
@Composable
@Preview(showBackground=true)
fun StatItemPreview() {
    StatItem("nknlknlk","jbskdc")
}

@Composable
fun ArtworkCard(title: String) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        tonalElevation = 3.dp,
        modifier = Modifier
            .padding(6.dp)
            .height(150.dp)
    ) {
        Box(contentAlignment = Alignment.BottomCenter) {
            // Placeholder for image
            Image(
               painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Text(
                text = title,
                color = Color.White,
                modifier = Modifier
                    .background(Color(0x88000000))
                    .fillMaxWidth()
                    .padding(4.dp),
                fontSize = 12.sp
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ArtworkCardPreview(){
    ArtworkCard("Hola")
}
