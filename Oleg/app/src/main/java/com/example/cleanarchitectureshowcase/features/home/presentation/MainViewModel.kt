package com.example.cleanarchitectureshowcase.features.home.presentation

import android.widget.EditText
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

    @Inject
    lateinit var stocksAdapter: StocksAdapter

    @Inject
    lateinit var userSearchHistoryService: UserSearchHistoryService

    private val _state = MutableStateFlow<StocksDataUI?>(null)
    val state = _state
    private var searchJob: Job? = null

    val searchFocusState = MutableStateFlow<SearchScreenState?>(null)

    val history = MutableStateFlow<List<String>?>(null)

    private fun getStocksData() {
        viewModelScope.launch {
            val result = getStocksDataUsecase.invoke("Params")
            state.value = result
        }
    }

    fun activityCreated() {
        getStocksData()
    }

    fun searchFocusChanged(focused: Boolean) {
        if (focused) {
            searchFocusState.value = SearchScreenState.SearchScreen
        } else {
            searchFocusState.value = SearchScreenState.MainScreen
        }
    }

    fun setDataInStocksAdapter(data: StocksDataUI) {
        stocksAdapter.apply {
            setItemsAndPics(data.stocks, data.pics)
            setOriginalData(data)
        }
    }

    fun updateSearch(query: String?) {
        searchJob?.cancel()

        if (query.isNullOrEmpty()) {
            stocksAdapter.retrieveOriginalData()
        } else {
            searchJob = viewModelScope.launch {
                delay(DEBOUNCE_MILLIS)
                val result = query.let { getSearchResultsUseCase.invoke(it) }
                stocksAdapter.setItemsAndPics(result.stocks, result.pics)
            }
        }
    }

    fun addToSearchHistory(query: String) {
        if (!userSearchHistoryService.containsSearchQuery(query)) {
            userSearchHistoryService.add(query)
            history.value = userSearchHistoryService.getSearchHistory()
        }
    }

    fun setTextInEditText(editText: EditText, text: String) {
        editText.setText(text)
    }

    companion object {
        const val DEBOUNCE_MILLIS: Long = 400
    }
}
