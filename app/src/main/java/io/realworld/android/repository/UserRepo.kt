package io.realworld.android.repository

import io.realworld.api.MediumClient
import io.realworld.api.models.entities.AuthData
import io.realworld.api.models.entities.Profile
import io.realworld.api.models.entities.User
import io.realworld.api.models.entities.UserUpdateData
import io.realworld.api.models.requests.AuthRequest
import io.realworld.api.models.requests.UserUpdateRequest

object UserRepo {


    val api=MediumClient.mediumAPI
    val authApi=MediumClient.mediumAuthAPI
    suspend fun loginUser(email:String,password:String) : User?{

        val response=api.loginUser(AuthRequest(AuthData(email,password)))

        MediumClient.token= response.body()?.user?.token
        return response.body()?.user
    }

    suspend fun registerUser(username:String,email:String,password: String,) :User? {

        val response=api.registerUser(AuthRequest(AuthData(email,password,username)))

        return response.body()?.user
    }

    suspend fun getCurrentUser(token:String) :User?{
        MediumClient.token=token
        val response=authApi.getUser()
        return response.body()?.user
    }

    suspend fun updateUser(
        username: String?,
        email: String?,
        bio:String?,
        password: String?,
        image:String?
    ) :User? {
        val response=authApi.updateUser(UserUpdateRequest(UserUpdateData(
            bio=bio,
            email=email,
            image=image,
            username = username,
            password = password
        )))
        return response.body()?.user
    }


    suspend fun getProfile(username:String) : Profile?{
        val response=authApi.getProfile(username)

        return response.body()?.profile
    }

    suspend fun followUser(username:String) :Profile?{
        val response=authApi.followUser(username)

        return response.body()?.profile
    }

    suspend fun unfollowUser(username:String) :Profile? {
        val response=authApi.unfollowUser(username)

        return response.body()?.profile
    }

     fun logout(){
        MediumClient.token=null
    }
}