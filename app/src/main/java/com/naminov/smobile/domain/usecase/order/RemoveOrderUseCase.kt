package com.naminov.smobile.domain.usecase.order

interface RemoveOrderUseCase {
    suspend operator fun invoke(id: String)
}