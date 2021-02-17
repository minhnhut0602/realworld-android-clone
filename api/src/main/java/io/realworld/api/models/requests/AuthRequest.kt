package io.realworld.api.models.requests

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import io.realworld.api.models.entities.AuthData

@JsonClass(generateAdapter = true)
data class AuthRequest(
    @Json(name = "user")
    val user: AuthData
)