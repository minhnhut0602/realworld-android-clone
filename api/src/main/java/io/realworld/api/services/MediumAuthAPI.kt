package io.realworld.api.services

import io.realworld.api.models.entities.User
import io.realworld.api.models.response.UserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface MediumAuthAPI {



    @GET("user")
    suspend fun getUser() :Response<UserResponse>
}