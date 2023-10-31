package com.example.cleanarchitectureshowcase.features.home.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ProgressBar
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
    private lateinit var stocksRecyclerView: RecyclerView
    private lateinit var stocksRVAdapter: StocksAdapter
    private lateinit var progressBarStocksRecyclerView: ProgressBar
    private lateinit var searchEditText: EditText
    private lateinit var tvPopularRequests: TextView
    private lateinit var tvStocks: TextView
    private lateinit var tvFavourite: TextView
    private lateinit var tvShowMore: TextView
    private lateinit var tvStocksOnSearch: TextView
    private lateinit var tvSearchedForThis: TextView
    private lateinit var popularRequests: StaggeredCustomView
    private lateinit var recentSearches: StaggeredCustomView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        stocksRecyclerView = findViewById(R.id.rv_stocks)
        stocksRVAdapter = StocksAdapter()
        progressBarStocksRecyclerView = findViewById(R.id.pb_rv_loading)
        searchEditText = findViewById(R.id.et_search)
        tvStocks = findViewById(R.id.tv_stocks)
        tvFavourite = findViewById(R.id.tv_favourite)
        tvShowMore = findViewById(R.id.tv_show_more)
        tvStocksOnSearch = findViewById(R.id.tv_stocks_on_search)
        tvPopularRequests = findViewById(R.id.tv_popular_requests)
        tvSearchedForThis = findViewById(R.id.tv_searched_for_this)
        popularRequests = findViewById(R.id.popular_requests)
        recentSearches = findViewById(R.id.searched_for_this)

        // mock data for popular requests
        popularRequests.setItems(listOf("Apple", "Amazon", "Google", "Tesla", "Microsoft", "First Solar", "Alibaba", "Facebook", "Mastercard"))

        popularRequests.adapter.setOnItemClickListener(object : StaggeredRecyclerViewInterface {
            override fun onItemClick(position: Int) {
                searchEditText.setText(popularRequests.adapter.getItem(position))
                viewModel.addToSearchHistory(searchEditText.text.toString(), recentSearches.adapter)
            }
        })

        recentSearches.adapter.setOnItemClickListener(object : StaggeredRecyclerViewInterface {
            override fun onItemClick(position: Int) {
                searchEditText.setText(recentSearches.adapter.getItem(position))
            }
        })

        stocksRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = stocksRVAdapter
        }

        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                hideMainScreenElements()
                hideSearchScreenElements()
                showSearchResultScreenElements()
                viewModel.updateSearch(query = searchEditText.text.toString().lowercase(), adapter = stocksRVAdapter)
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        searchEditText.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId != EditorInfo.IME_ACTION_SEARCH) {
                    return false
                }
                if (searchEditText.text.isNullOrEmpty()) {
                    return false
                }
                viewModel.addToSearchHistory(searchEditText.text.toString(), recentSearches.adapter)
                return true
            }
        })

        searchEditText.setOnFocusChangeListener(object : View.OnFocusChangeListener {
            override fun onFocusChange(v: View?, hasFocus: Boolean) {
                if (popularRequests.visibility == View.VISIBLE) {
                    hideSearchResultScreenElements()
                    hideSearchScreenElements()
                    showMainScreenElements()
                } else {
                    hideMainScreenElements()
                    hideSearchResultScreenElements()
                    showSearchScreenElements()
                }
            }
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
    fun showMainScreenElements() {
        stocksRecyclerView.visibility = View.VISIBLE
        tvStocks.visibility = View.VISIBLE
        tvFavourite.visibility = View.VISIBLE
    }
    fun hideMainScreenElements() {
        stocksRecyclerView.visibility = View.GONE
        tvStocks.visibility = View.GONE
        tvFavourite.visibility = View.GONE
    }
    fun showSearchResultScreenElements() {
        stocksRecyclerView.visibility = View.VISIBLE
        tvStocksOnSearch.visibility = View.VISIBLE
        tvShowMore.visibility = View.VISIBLE
    }
    fun hideSearchResultScreenElements() {
        tvStocksOnSearch.visibility = View.GONE
        tvShowMore.visibility = View.GONE
    }
    fun showSearchScreenElements() {
        tvPopularRequests.visibility = View.VISIBLE
        popularRequests.visibility = View.VISIBLE
        tvSearchedForThis.visibility = View.VISIBLE
        recentSearches.visibility = View.VISIBLE
    }
    fun hideSearchScreenElements() {
        tvPopularRequests.visibility = View.GONE
        popularRequests.visibility = View.GONE
        tvSearchedForThis.visibility = View.GONE
        recentSearches.visibility = View.GONE
    }
}
