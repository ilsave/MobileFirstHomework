package com.example.cleanarchitectureshowcase.features.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleanarchitectureshowcase.features.home.domain.GetStocksDataUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getStocksDataUsecase: GetStocksDataUsecase
): ViewModel() {

    private val _state = MutableStateFlow<StocksDataUI?>(null)
    val state = _state

    fun getStocksData() {
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
            setItems(data.stocks)
            setPics(data.pics)
        }
    }
}
