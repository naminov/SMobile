package com.naminov.smobile.data.network.dto

import com.google.gson.annotations.SerializedName

data class OrderHistoryDto(
    @field:SerializedName("id")
    val id: String,
    @field:SerializedName("number")
    val number: String,
    @field:SerializedName("date")
    val date: String,
    @field:SerializedName("customer")
    val customer: String,
    @field:SerializedName("sum")
    val sum: String,
    @field:SerializedName("payment")
    val payment: Boolean,
    @field:SerializedName("documents")
    val documents: Boolean,
    @field:SerializedName("completed")
    val completed: Boolean
)