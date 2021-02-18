package io.realworld.api.services

import io.realworld.api.models.entities.User
import io.realworld.api.models.requests.AuthRequest
import io.realworld.api.models.requests.UserUpdateRequest
import io.realworld.api.models.response.UserResponse
import retrofit2.Response
import retrofit2.http.*

interface MediumAuthAPI {



    @GET("user")
    suspend fun getUser() :Response<UserResponse>

    @PUT("user")
    suspend fun updateUser(
        @Body user : UserUpdateRequest
    ) :Response<UserResponse>
}