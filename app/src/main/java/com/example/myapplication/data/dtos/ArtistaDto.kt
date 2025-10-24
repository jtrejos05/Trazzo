package com.example.myapplication.data.dtos

import com.example.myapplication.data.Artista
import com.example.myapplication.data.Comentario
import com.example.myapplication.data.Obra


data class ObrasArtistaDto(
    val id: Int,
    val titulo: String,
    val descripcion: String,
    val obraIMG: String,
    val numComentarios: Int,
    val likes: Int,
    val compartidos: Int,
    val vistas: Int,
    val createdAt: String,
    val updatedAt: String? = null
)

data class InteresesDto(
    val interes:String
)
data class ArtistaDto(
    val id: String,
    val nombre: String,
    val correo: String,
    val fotousuario: String?,
    val contraseña: String,
    val edad: String,
    val profesion: String,
    val biografia: String?,
    val numSeguidores: Int,
    val numSeguidos: Int,
    val likes: Int,
    val createdAt: String,
    val updatedAt: String,
    val obras: List<ObrasArtistaDto>,
    val intereses: List<InteresesDto>,
    var seSiguen: Boolean = false
){
    constructor(): this("0","","","","","0","","",0,0,0,"","",emptyList(),emptyList())
}
fun ObrasArtistaDto.toObra(fotousuario: String, usuario: String, id:Int): Obra{
    var likesString = ""
    if (likes>1000) {
        val likesInK = likes / 1000.0
        likesString = String.format("%.1fk", likesInK)
    } else {
        likesString = likes.toString()
    }

    return Obra(
        fotous = fotousuario,
        usuario = usuario,
        hora = this.createdAt,
        titulo = this.titulo,
        descripcion = this.descripcion,
        foto = this.obraIMG,
        likes = likesString,
        comentarios = this.numComentarios.toString(),
        compartidos = this.compartidos.toString(),
        vistas = this.vistas.toString(),
        obraId = this.id.toString(),
        Tags = emptyList(),
        artistaId = id.toString()
    )
}
fun ArtistaDto.toArtista(): Artista {
    var obras = this.obras.map { it.toObra(this.fotousuario ?: "", this.nombre, this.id.toInt()) }
    var intereses = this.intereses.map { it.interes }

    return Artista(
        id=this.id.toString(),
        img = this.fotousuario ?: "",
        correo = this.correo,
        contrasena = this.contraseña,
        usuario = this.nombre,
        edad = this.edad.toInt(),
        profesion = this.profesion,
        biografia = this.biografia ?: "No hay biografia",
        seguidores = this.numSeguidores,
        siguiendo = this.numSeguidos,
        likes = likes,
        obras=  obras,
        interses = intereses,
        seSiguen = this.seSiguen


    )
}
