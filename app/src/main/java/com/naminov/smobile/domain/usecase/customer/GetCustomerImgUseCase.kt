package com.naminov.smobile.domain.usecase.customer

import com.naminov.smobile.domain.model.Customer

interface GetCustomerImgUseCase {
    suspend operator fun invoke(customer: Customer): String
}