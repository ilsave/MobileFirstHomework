package com.example.cleanarchitectureshowcase.features.home.presentation

sealed class SearchScreenState {
    object SearchScreen: SearchScreenState()
    object MainScreen: SearchScreenState()
}