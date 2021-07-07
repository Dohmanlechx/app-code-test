package com.codetest.main.api.models

data class LocationRequest(
    val name: String,
    val temperature: Int,
    val status: String
)