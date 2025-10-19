package com.example.myapplication.ui.Trending

import android.util.Log
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.Obra
import com.example.myapplication.data.local.ProveedorObras
import com.example.myapplication.data.repository.AuthRepository
import com.example.myapplication.data.repository.ObraRepository
import com.example.myapplication.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TrendingViewModel @Inject constructor(
    private val UserRepo: UserRepository,
    private val AuthRepo: AuthRepository,
    private val ObrasRepo: ObraRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(TrendingState())
    var uiState: MutableStateFlow<TrendingState> = _uiState

    fun getObras(){
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            try {
                val resultSeguidos = UserRepo.getSeguidosOrSeguidores(true, AuthRepo.currentUser?.uid ?: "")
                if (resultSeguidos.isSuccess) {
                    val seguidos = resultSeguidos.getOrDefault(emptyList())
                    Log.d("ObrasSeguidos", "La cantidad de seguidos son ${seguidos.size}")
                    // Combinar todas las obras de los artistas seguidos
                    val obras: List<Obra> = seguidos.flatMap { artista ->
                        Log.d("ObrasSeguidos", "Artista ${artista.id}")
                        val resultObras = ObrasRepo.getObrasById(artista.id)
                        Log.d("ObrasSeguidos", "La cantidad de obras son ${resultObras.getOrDefault(emptyList()).size}")
                        resultObras.getOrDefault(emptyList()) // si falla, devuelve lista vacía
                    }
                    _uiState.update { it.copy(isLoading = false, obras = obras) }
                    Log.d("ObrasSeguidos", "Cant de obras total ${obras.size}")
                } else {
                    resultSeguidos.exceptionOrNull()?.printStackTrace()
                }
            } catch (e: Exception){
                _uiState.update { it.copy(
                    isLoading = false,
                    errorMessage = "Error al cargar las obras: ${e.message}"
                ) }
            }
        }
    }

    /*
    fun getObras(){
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            try {
                val result=ObrasRepo.getTrendingObras()
                if (result.isSuccess){
                    _uiState.update { it.copy(obras = result.getOrDefault(emptyList())) }
                }else{
                    Log.e("PerfilViewModel", "Error al obtener usuario")
                    result.exceptionOrNull()?.printStackTrace()
                }
                //Error si la lista está vacía.
                if (_uiState.value.obras.isEmpty()) {
                    throw Exception("No se encontraron obras para mostrar. Por favor, revisa la fuente de datos.")
                }

                // Actualiza el estado con las obras si la carga es exitosa.
                _uiState.update { it.copy(isLoading = false) }

            } catch (e: Exception) {
                // Si ocurre un error, actualiza el estado con el mensaje de error.
                _uiState.update { it.copy(
                    isLoading = false,
                    errorMessage = "Error al cargar las obras: ${e.message}"
                ) }
            }
        }
    }
     */
    init {
        getObras()
    }
}