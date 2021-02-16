package io.realworld.api

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertNotNull
import org.junit.Test

class MediumClientTest {

    val mediumClient=MediumClient()

    @Test
    fun `get articles`(){
        runBlocking {
            val article=mediumClient.mediumAPI.getArticles()
            assertNotNull(article.body()?.articles)
        }
    }
}