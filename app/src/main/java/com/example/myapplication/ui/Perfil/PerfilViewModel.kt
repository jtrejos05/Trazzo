package com.example.myapplication.ui.Perfil

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.R
import com.example.myapplication.data.Artista
import com.example.myapplication.data.Obra
import com.example.myapplication.data.local.ProveedorActividad
import com.example.myapplication.data.local.ProveedorNotificaciones
import com.example.myapplication.data.local.ProveedorObras
import com.example.myapplication.data.repository.AuthRepository
import com.example.myapplication.data.repository.ComentarioRepository
import com.example.myapplication.data.repository.ObraRepository
import com.example.myapplication.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PerfilViewModel @Inject constructor(
    private  val authRepository: AuthRepository,
    private val comentarioRepo: ComentarioRepository,
    private val userRepo: UserRepository,
    private val obraRepo: ObraRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(PerfilState())
    var uiState: MutableStateFlow<PerfilState> = _uiState



    fun onOutClick(){
        authRepository.signOut()
    }

    //FUncion para cambiar de tabs
    fun updateSelectedTab(nuevaSelectedTab: Int = 0) {
        _uiState.update { it.copy(selectedTab = nuevaSelectedTab) }
    }
    fun updateError(msg: String){
        _uiState.update { it.copy(errormsg = msg) }
    }
    fun updateArtista(artista: Artista){
        _uiState.update { it.copy(usuario = artista) }
    }
    // ✅ FUNCIÓN CORREGIDA
    fun getUsuario(id: String){
        viewModelScope.launch {
            Log.d("Seguir", "Se busca el user ${id}")
            _uiState.update { it.copy(isLoading = true, errormsg = null) }

            val result = userRepo.getUserById(id)

            if (result.isSuccess){
                val usuario = result.getOrNull()

                // ✅ Verificar si el usuario realmente existe
                if (usuario != null && usuario.id.isNotEmpty()) {
                    // Usuario encontrado exitosamente
                    _uiState.update { it.copy(usuario = usuario, isLoading = false, errormsg = null) }
                    Log.d("USER", usuario.usuario)

                    // Cargar reviews y obras
                    getReviews()
                    if (usuario.obras.isEmpty()){
                        getObraByArtista()
                    }

                    Log.d("SEGUIR 4", "seSiguen: ${usuario.seSiguen}")
                } else {
                    // El usuario no existe o está vacío
                    _uiState.update {
                        it.copy(
                            errormsg = "El usuario no existe o no está disponible",
                            isLoading = false
                        )
                    }
                    Log.e("PerfilViewModel", "Usuario no encontrado o ID vacío")
                }
            } else {
                // Error al obtener el usuario
                val errorMsg = result.exceptionOrNull()?.message ?: "Error al obtener usuario"
                _uiState.update { it.copy(errormsg = errorMsg, isLoading = false) }
                Log.e("PerfilViewModel", "Error al obtener usuario: $errorMsg")
            }
        }
    }
   fun getObraByReviewId(id:String): Obra{
        viewModelScope.launch {
            _uiState.update { it.copy(cargandoObra = true) }
            val result = obraRepo.getObra(id,authRepository.currentUser?.uid ?: "")
            if (result.isSuccess){
                _uiState.update { it.copy(ObraReview = result.getOrNull() ?: Obra("","","","","", listOf(),"", "", "", "", "0", "","")) }
                _uiState.update { it.copy(cargandoObra = false) }
            }else{
                _uiState.update { it.copy(errormsg = "La obra no se pudo Obtener") }
                _uiState.update { it.copy(cargandoObra = false) }
            }
        }
        return _uiState.value.ObraReview
    }
    fun getReviews(){
        viewModelScope.launch {

            val result = comentarioRepo.getComentarioByArtistaId(_uiState.value.usuario.id)
            if (result.isSuccess){
                _uiState.update { it.copy(reviews = result.getOrNull() ?: emptyList()) }

            }else{
                result.exceptionOrNull()?.printStackTrace()
            }

        }

    }

    fun getObraByArtista(){
        viewModelScope.launch {
            val result= userRepo.getObrasByArtista(_uiState.value.usuario.id)
            if (result.isSuccess){
                var obras = result.getOrNull()
                var usuario = _uiState.value.usuario
                usuario.obras = obras ?: emptyList()
                _uiState.update { it.copy(usuario=usuario) }
            }
        }
    }
    fun getNotificaciones(){
        _uiState.update { it.copy(notificaciones = ProveedorNotificaciones.notificaciones) }
    }

    fun deleteComment(id: String){
        viewModelScope.launch {
            val result = comentarioRepo.deleteComentario(id)
            if (result.isSuccess){
                _uiState.value = _uiState.value.copy(
                    reviews = _uiState.value.reviews.filter { it.id != id }
                )

            }else{
                _uiState.update { it.copy(errormsg = "Fallo la eliminacion") }
            }
        }
    }
    fun followOrUnfollowUser(target: String){
        val currentUser = _uiState.value.currentUser
        viewModelScope.launch {
            Log.d("SEGUIR 2", "USERID: ${currentUser}")
            Log.d("SEGUIR 2", "TARGETID: ${target}")
            Log.d("SEGUIR 2", "seSIguen: ${_uiState.value.usuario.seSiguen}")
            val result = userRepo.followOrUnfollowUser(currentUser,target)
            if (result.isSuccess){
                _uiState.value = _uiState.value.copy(
                    usuario = _uiState.value.usuario.copy(
                        seSiguen = !_uiState.value.usuario.seSiguen
                    ),
                    seguir = !_uiState.value.usuario.seSiguen

                )
            }
            Log.d("SEGUIR 3", "seSIguen: ${_uiState.value.usuario.seSiguen}")

        }
    }
    init {
        _uiState.update { it.copy(currentUser = authRepository.currentUser?.uid ?: "") }
        getNotificaciones()

    }
}