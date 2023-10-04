package com.example.cleanarchitectureshowcase.features.home.data

import com.example.cleanarchitectureshowcase.features.home.domain.DataDomain

data class DataDTO(
    val title: String,
    val subTitle: String,
    val description: String,
    val someDumpInfo: String,
    val importantDataForDomain: String
) {
    fun toDomain() = DataDomain(
        title, subTitle, description, importantDataForDomain
    )
}
