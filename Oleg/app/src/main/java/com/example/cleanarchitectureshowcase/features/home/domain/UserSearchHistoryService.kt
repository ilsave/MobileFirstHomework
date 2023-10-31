package com.example.cleanarchitectureshowcase.features.home.domain

class UserSearchHistoryService() {

    val searchHistory = mutableListOf<String>()

    fun add(searchQuery: String) {
        if (searchHistory.size == SEARCH_HISTORY_SIZE) {
            searchHistory.removeFirst()
        }
        searchHistory.add(searchQuery)
    }

    fun contains(query: String): Boolean {
        return searchHistory.contains(query)
    }

    companion object {
        const val SEARCH_HISTORY_SIZE = 6
    }
}