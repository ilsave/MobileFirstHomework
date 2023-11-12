package com.example.cleanarchitectureshowcase.features.home.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cleanarchitectureshowcase.R

class StaggeredAdapter: RecyclerView.Adapter<StaggeredAdapter.RecentSearchesHolder>() {

    private var items: List<String> = emptyList()

    private lateinit var recyclerViewInterface: StaggeredRecyclerViewInterface

    class RecentSearchesHolder(itemView: View, staggeredRecyclerViewInterface: StaggeredRecyclerViewInterface): RecyclerView.ViewHolder(itemView) {
        val searchQueue: TextView = itemView.findViewById(R.id.tv_search_queue)

        init {
            itemView.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    staggeredRecyclerViewInterface.onItemClick(adapterPosition)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecentSearchesHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.staggered_item, parent, false)
        return RecentSearchesHolder(view, recyclerViewInterface)
    }

    override fun onBindViewHolder(
        holder: RecentSearchesHolder,
        position: Int
    ) {
        val search = items[position]
        holder.searchQueue.text = search
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun getItem(position: Int): String {
        return items[position]
    }

    fun setData(list: List<String>) {
        items = list
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(listener: StaggeredRecyclerViewInterface) {
        recyclerViewInterface = listener
    }
}
