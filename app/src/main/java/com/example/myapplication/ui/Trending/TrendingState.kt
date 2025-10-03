package com.example.myapplication.ui.Trending

import com.example.myapplication.data.Obra

data class TrendingState(
    var obras: List<Obra> = emptyList(),
    var errorMessage: String? = null,
    var isLoading: Boolean = false,
)