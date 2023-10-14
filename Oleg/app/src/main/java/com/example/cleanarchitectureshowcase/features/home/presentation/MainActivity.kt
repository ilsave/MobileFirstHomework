package com.example.cleanarchitectureshowcase.features.home.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
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

    private lateinit var stocksRecyclerView: RecyclerView
    private lateinit var stocksRVAdapter: StocksAdapter
    private lateinit var progressBarStocksRecyclerView: ProgressBar
    private lateinit var searchEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        stocksRecyclerView = findViewById(R.id.rv_stocks)
        stocksRVAdapter = StocksAdapter()
        progressBarStocksRecyclerView = findViewById(R.id.pb_rv_loading)
        searchEditText = findViewById(R.id.et_search)

        stocksRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = stocksRVAdapter
        }

        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.updateSearch(query = searchEditText.text.toString().lowercase(), adapter = stocksRVAdapter)
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        lifecycleScope.launch {
            viewModel.activityCreated()
            viewModel.state.collectLatest {data ->
                data?.let {
                    viewModel.setDataInStocksAdapter(data = data, adapter = stocksRVAdapter)
                    hideProgressBar(progressBarStocksRecyclerView)
                }
            }
        }
    }

    fun showProgressBar(progressBar: ProgressBar) {
        progressBar.visibility = View.VISIBLE
    }

    fun hideProgressBar(progressBar: ProgressBar) {
        progressBar.visibility = View.GONE
    }
}
