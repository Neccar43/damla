package com.novacodestudios.model.screen

import kotlinx.serialization.Serializable

@Serializable
sealed class Graph {
    @Serializable
    data object Main : Graph()

    @Serializable
    data object Root : Graph()
}