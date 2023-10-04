package com.example.cleanarchitectureshowcase.core.domain

interface CoroutinesUseCase<IN: Any?, OUT: Any?> {
    suspend fun invoke(params: IN): OUT
}
