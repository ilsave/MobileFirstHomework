package com.example.cleanarchitectureshowcase.features.home.presentation

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cleanarchitectureshowcase.R
import com.example.cleanarchitectureshowcase.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    val viewModel: MainViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var stocksRVAdapter: StocksAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // mock data for popular requests
        binding.popularRequests.setItems(resources.getStringArray(R.array.popular_requests).asList())

        binding.popularRequests.adapter.setOnItemClickListener(object : StaggeredRecyclerViewInterface {
            override fun onItemClick(position: Int) {
                viewModel.setTextInEditText(editText = binding.etSearch, text = binding.popularRequests.adapter.getItem(position))
                viewModel.addToSearchHistory(binding.etSearch.text.toString(), binding.searchedForThis.adapter)
            }
        })

        binding.searchedForThis.adapter.setOnItemClickListener(object : StaggeredRecyclerViewInterface {
            override fun onItemClick(position: Int) {
                viewModel.setTextInEditText(editText = binding.etSearch, text = binding.searchedForThis.adapter.getItem(position))
            }
        })

        binding.rvStocks.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = stocksRVAdapter
        }

        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // show search results
                hideSearchScreenElements()
                hideMainScreenElements()
                showSearchResultScreenElements()
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.updateSearch(query = binding.etSearch.text.toString().lowercase())
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        binding.etSearch.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId != EditorInfo.IME_ACTION_SEARCH) {
                    return false
                }
                if (binding.etSearch.text.isNullOrEmpty()) {
                    return false
                }
                viewModel.addToSearchHistory(binding.etSearch.text.toString(), binding.searchedForThis.adapter)
                return true
            }
        })

        binding.etSearch.setOnFocusChangeListener(object : View.OnFocusChangeListener {
            override fun onFocusChange(v: View?, hasFocus: Boolean) {
                lifecycleScope.launch {
                    viewModel.searchFocusChanged(focus = hasFocus)
                    viewModel.searchFocusState.collectLatest {state ->
                        when (state) {
                            "Search screen" -> {
                                hideMainScreenElements()
                                hideSearchResultScreenElements()
                                showSearchScreenElements()
                            }
                            "Main screen" -> {
                                hideSearchScreenElements()
                                hideSearchResultScreenElements()
                                showMainScreenElements()
                                v?.hideKeyboard()
                            }
                        }
                    }
                }
            }
        })

        lifecycleScope.launch {
            viewModel.activityCreated()
            viewModel.state.collectLatest {data ->
                data?.let {
                    viewModel.setDataInStocksAdapter(data = data)
                    hideProgressBar(binding.pbRvLoading)
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
        binding.rvStocks.visibility = View.VISIBLE
        binding.tvStocks.visibility = View.VISIBLE
        binding.tvFavourite.visibility = View.VISIBLE
    }
    fun hideMainScreenElements() {
        binding.rvStocks.visibility = View.GONE
        binding.tvStocks.visibility = View.GONE
        binding.tvFavourite.visibility = View.GONE
    }
    fun showSearchResultScreenElements() {
        binding.rvStocks.visibility = View.VISIBLE
        binding.tvStocksOnSearch.visibility = View.VISIBLE
        binding.tvShowMore.visibility = View.VISIBLE
    }
    fun hideSearchResultScreenElements() {
        binding.tvStocksOnSearch.visibility = View.GONE
        binding.tvShowMore.visibility = View.GONE
    }
    fun showSearchScreenElements() {
        binding.tvPopularRequests.visibility = View.VISIBLE
        binding.popularRequests.visibility = View.VISIBLE
        binding.tvSearchedForThis.visibility = View.VISIBLE
        binding.searchedForThis.visibility = View.VISIBLE
    }
    fun hideSearchScreenElements() {
        binding.tvPopularRequests.visibility = View.GONE
        binding.popularRequests.visibility = View.GONE
        binding.tvSearchedForThis.visibility = View.GONE
        binding.searchedForThis.visibility = View.GONE
    }

    fun View.hideKeyboard() {
        val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(windowToken, 0)
    }
}
