package com.example.cleanarchitectureshowcase.features.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleanarchitectureshowcase.features.home.domain.GetSearchResultsUseCase
import com.example.cleanarchitectureshowcase.features.home.domain.GetStocksDataUsecase
import com.example.cleanarchitectureshowcase.features.home.domain.UserSearchHistoryService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getStocksDataUsecase: GetStocksDataUsecase,
    private val getSearchResultsUseCase: GetSearchResultsUseCase,
): ViewModel() {

    private val userSearchHistoryService = UserSearchHistoryService()

    private val _state = MutableStateFlow<StocksDataUI?>(null)
    val state = _state
    private var searchJob: Job? = null

    private fun getStocksData() {
        viewModelScope.launch {
            val result = getStocksDataUsecase.invoke("Params")
            state.value = result
        }
    }

    fun activityCreated() {
        getStocksData()
    }

    fun setDataInStocksAdapter(data: StocksDataUI, adapter: StocksAdapter) {
        adapter.apply {
            setItemsAndPics(data.stocks, data.pics)
            setOriginalData(data)
        }
    }

    fun updateSearch(query: String?, adapter: StocksAdapter) {
        searchJob?.cancel()

        if (query.isNullOrEmpty()) {
            adapter.retrieveOriginalData()
        } else {
            searchJob = viewModelScope.launch {
                delay(400)
                val result = query.let { getSearchResultsUseCase.invoke(it) }
                adapter.setItemsAndPics(result.stocks, result.pics)
            }
        }
    }

    fun addToSearchHistory(query: String?, adapter: StaggeredAdapter) {
        if (query != null && !userSearchHistoryService.contains(query)) {
            userSearchHistoryService.add(query)
            setDataInStaggeredAdapter(userSearchHistoryService.searchHistory, adapter)
        }
    }

    private fun setDataInStaggeredAdapter(data: List<String>, adapter: StaggeredAdapter) {
        adapter.setData(data)
    }
}
