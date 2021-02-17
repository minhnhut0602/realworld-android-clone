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

    private val mediumClient=MediumClient()

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
            AuthData(email = "testemail${Random.nextInt(999, 9999)}@test.com",
                password = "pass${Random.nextInt(9999, 999999)}",
                username = "rand_user_${Random.nextInt(99, 999)}")
        )
        runBlocking{

            val user=mediumClient.mediumAPI.registerUser(authRequest)

            assertEquals(user.body()?.user?.username,authRequest.user.username)
        }
    }
}