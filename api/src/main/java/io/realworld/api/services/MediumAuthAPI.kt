package io.realworld.api.services

import io.realworld.api.models.entities.User
import retrofit2.Response
import retrofit2.http.POST

interface MediumAuthAPI {


    @POST("users")
    suspend fun registerUser() : Response<User>
}