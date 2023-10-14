package com.example.cleanarchitectureshowcase.features.home.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cleanarchitectureshowcase.R
import com.example.cleanarchitectureshowcase.features.home.data.StockInfoDTO
import com.example.cleanarchitectureshowcase.features.home.data.StockPictureDTO

class StocksAdapter: RecyclerView.Adapter<StocksAdapter.StocksHolder>() {

    private var originalData: StocksDataUI? = null
    private var stocks: List<StockInfoDTO> = listOf()
    private var pics: List<StockPictureDTO> = listOf()

    class StocksHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val ticket: TextView = itemView.findViewById(R.id.tv_ticket)
        val companyName: TextView = itemView.findViewById(R.id.tv_company_name)
        val price: TextView = itemView.findViewById(R.id.tv_current_price)
        val dayChangePercent: TextView = itemView.findViewById(R.id.tv_day_delta)
        val stockPicture: ImageView = itemView.findViewById(R.id.iv_company)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StocksHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_stocks_element, parent, false)
        return StocksHolder(view)
    }

    override fun onBindViewHolder(holder: StocksHolder, position: Int) {
        val item = stocks[position]
        val pic = pics[position]
        val context = holder.itemView.context

        // this abomination...
        with(holder) {
            Glide.with(context)
                .load(pic.image)
                .error(R.drawable.stock_placeholder)
                .placeholder(R.color.white)
                .into(stockPicture)

            ticket.text = item.symbol
            companyName.text = item.name
            price.text = "$".plus(item.price)
            if (item.changesPercentage >= 0) {
                dayChangePercent.text = "+$"
                    .plus(item.change)
                    .plus(" (")
                    .plus(String.format("%.2f", item.changesPercentage))
                    .plus("%)")
            } else {

                dayChangePercent.text = "-$"
                    .plus(kotlin.math.abs(item.change))
                    .plus(" (")
                    .plus(String.format("%.2f", item.changesPercentage))
                    .plus("%)")
            }
        }

        val defaultColor = holder.itemView.background
        val conditionColor = if (position % 2 == 0) R.color.bg_grey else R.color.white
        val changesPercentageColor = if (item.changesPercentage < 0) R.color.red else R.color.green

        defaultColor.setTint(ContextCompat.getColor(context, conditionColor))
        holder.dayChangePercent.setTextColor(ContextCompat.getColor(context, changesPercentageColor))
    }

    override fun getItemCount(): Int {
        return stocks.size
    }

    fun setItemsAndPics(newItems: List<StockInfoDTO>, newPics: List<StockPictureDTO>) {
        stocks = newItems
        pics = newPics
        notifyDataSetChanged()
    }

    fun setOriginalData(data: StocksDataUI) {
        originalData = data
    }

    fun retrieveOriginalData() {
        originalData?.let {
            stocks = it.stocks
            pics = it.pics
        }
        notifyDataSetChanged()
    }
}
