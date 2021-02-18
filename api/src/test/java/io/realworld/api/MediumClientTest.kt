package io.realworld.api

import io.realworld.api.models.entities.AuthData
import io.realworld.api.models.entities.UserUpdateData
import io.realworld.api.models.requests.AuthRequest
import io.realworld.api.models.requests.UserUpdateRequest
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

class MediumClientTest {

    private val mediumClient=MediumClient

    @Test
    fun `get articles`(){
        runBlocking {
            val article=mediumClient.mediumAPI.getArticles()
            assertNotNull(article.body()?.articles)
        }
    }


    @Test
    fun `register and login`(){

        val authRequest=AuthRequest(
            AuthData("doesitmatter@gmail.com",
            "asdf1234")
        )
        runBlocking{

            val userAuth=mediumClient.mediumAPI.loginUser(authRequest)


            val token =userAuth.body()?.user?.token
            mediumClient.token=token
            val updateRequest =UserUpdateRequest(
                UserUpdateData(null,"doesitmatter@gmail.com",null,"Karan1628","asdf1234")
            )
            val updatedUser=mediumClient.mediumAuthAPI.updateUser(updateRequest)

            assertNotNull(updatedUser.body()?.user)
            assertEquals("Karan1628",updatedUser.body()?.user?.username)
        }
    }


}