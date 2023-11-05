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

    private val userSearchHistoryService = UserSearchHistoryService()

    private val _state = MutableStateFlow<StocksDataUI?>(null)
    val state = _state
    private var searchJob: Job? = null

    val searchFocusState = MutableStateFlow<String?>(null)

    private fun getStocksData() {
        viewModelScope.launch {
            val result = getStocksDataUsecase.invoke("Params")
            state.value = result
        }
    }

    fun activityCreated() {
        getStocksData()
    }

    fun searchFocusChanged(focus: Boolean) {
        when (focus) {
            true -> {
                searchFocusState.value = "Search screen"
            }
            false -> {
                searchFocusState.value = "Main screen"
            }
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
                delay(400)
                val result = query.let { getSearchResultsUseCase.invoke(it) }
                stocksAdapter.setItemsAndPics(result.stocks, result.pics)
            }
        }
    }

    fun addToSearchHistory(query: String?, adapter: StaggeredAdapter) {
        if (query != null && !userSearchHistoryService.contains(query)) {
            userSearchHistoryService.add(query)
            setDataInStaggeredAdapter(userSearchHistoryService.getSearchHistory(), adapter)
        }
    }

    private fun setDataInStaggeredAdapter(data: List<String>, adapter: StaggeredAdapter) {
        adapter.setData(data)
    }

    fun setTextInEditText(editText: EditText, text: String) {
        editText.setText(text)
    }
}
