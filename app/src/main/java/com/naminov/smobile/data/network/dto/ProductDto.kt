package com.naminov.smobile.data.network.dto

import com.google.gson.annotations.SerializedName

data class ProductDto(
    @field:SerializedName("id")
    val id: String,
    @field:SerializedName("name")
    val name: String
)