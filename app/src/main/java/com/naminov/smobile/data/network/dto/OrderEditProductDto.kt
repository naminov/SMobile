package com.naminov.smobile.data.network.dto

import com.google.gson.annotations.SerializedName

data class OrderEditProductDto(
    @field:SerializedName("id")
    val id: String,
    @field:SerializedName("number")
    val number: Int
)