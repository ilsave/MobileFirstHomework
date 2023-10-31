package com.example.cleanarchitectureshowcase.features.home.presentation

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.cleanarchitectureshowcase.R

class StaggeredCustomView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var recyclerView: RecyclerView
    var adapter = StaggeredAdapter()

    init {
        LayoutInflater.from(context).inflate(R.layout.staggered_view, this, true)
        recyclerView = findViewById(R.id.rv_recent_search)
        recyclerView.layoutManager = StaggeredGridLayoutManager(SPAN_COUNT, RecyclerView.HORIZONTAL)
        adapter = StaggeredAdapter()
        recyclerView.adapter = adapter
    }

    fun setItems(items: List<String>) {
        adapter.setData(items)
    }

    companion object {
        const val SPAN_COUNT = 2
    }
}
