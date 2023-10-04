package com.example.cleanarchitectureshowcase.features.home.domain

import com.example.cleanarchitectureshowcase.features.home.presentation.DataUI

data class DataDomain(
    val title: String,
    val subTitle: String,
    val description: String,
    val importantDataForDomain: String
) {
    fun toUI() = DataUI(title, subTitle, description)
}
