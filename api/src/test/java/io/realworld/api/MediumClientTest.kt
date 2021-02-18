package io.realworld.api

import io.realworld.api.models.entities.AuthData
import io.realworld.api.models.requests.AuthRequest
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import java.util.*
import kotlin.random.Random

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

            val user=mediumClient.mediumAPI.loginUser(authRequest)


            val token =user.body()?.user?.token
            mediumClient.token=token
            val getUser=mediumClient.mediumAuthAPI.getUser()
            assertNotNull(getUser.body()?.user?.username)
        }
    }


}