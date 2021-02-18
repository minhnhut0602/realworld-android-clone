package io.realworld.api


import io.realworld.api.models.entities.*
import io.realworld.api.models.requests.AuthRequest
import io.realworld.api.models.requests.UpsertArticleRequest
import io.realworld.api.models.requests.UserUpdateRequest
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull

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

            // updating User
            val updateRequest =UserUpdateRequest(
                UserUpdateData(null,"doesitmatter@gmail.com",null,"Karan1628","asdf1234")
            )
            val updatedUser=mediumClient.mediumAuthAPI.updateUser(updateRequest)

            //Getting user Profile of Someone
            val getProfile=mediumClient.mediumAuthAPI.getProfile(
                "Karate22"
            )

            //Follow and Unfollow user
            val followUser=mediumClient.mediumAuthAPI.followUser("Karate22")

            //Get Feed Article
            val feedArticles=mediumClient.mediumAuthAPI.getArticleFeed()

            //Create and Update article
            val articleRequest=UpsertArticleRequest(
                ArticleData(title = "Article on Conduit ",
                        description= "Test Article",
                        body = """ 
                            This is a testing article, Which means it is created while testing phase
                        """.trimIndent()
                        ,tagList = listOf("test", "dragon"))
            )
            val articleData=mediumClient.mediumAuthAPI.createArticle(articleRequest)

            val comments=mediumClient.mediumAuthAPI.getComments("cass-7ydkyl")


            assertNotNull(comments.body()?.comments)
            assertNull(articleData.body()?.article)
            assertNotNull(feedArticles.body()?.articles)
            assertNotNull(followUser.body()?.profile)
            assertNotNull(updatedUser.body()?.user)
            assertEquals("Karan1628",updatedUser.body()?.user?.username)
            assertNotNull(getProfile.body()?.profile)
        }
    }


}