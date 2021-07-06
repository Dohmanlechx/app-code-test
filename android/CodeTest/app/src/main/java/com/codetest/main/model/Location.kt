package com.codetest.main.model

import com.google.gson.JsonObject

data class Location(
    val id: String? = null,
    val name: String? = null,
    val temperature: String? = null,
    val status: Status? = null
) {

    companion object {
        fun from(jsonObject: JsonObject): Location {
            return Location(
                jsonObject.get("id").asString,
                jsonObject.get("name").asString,
                jsonObject.get("temperature").asString,
                Status.from(jsonObject.get("status").asString)
            )
        }
    }
}