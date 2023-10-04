package com.example.cleanarchitectureshowcase.features.home.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cleanarchitectureshowcase.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    val viewModel: MainViewModel by viewModels()

    private lateinit var textView: TextView
    private lateinit var stocksRecyclerView: RecyclerView
    private lateinit var stocksRVAdapter: StocksAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        stocksRecyclerView = findViewById(R.id.rv_stocks)
        stocksRVAdapter = StocksAdapter()

        stocksRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = stocksRVAdapter
        }

        lifecycleScope.launch {
            viewModel.getStocksData()
            viewModel.state.collectLatest {data ->
                if (data != null) {
                    stocksRVAdapter.setItems(data.stocks)
                    stocksRVAdapter.setPics(data.pics)
                }
            }
        }
    }

    fun setText(longString: String) {
        textView.text = longString
    }
}
