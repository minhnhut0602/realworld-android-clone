package io.realworld.api

import io.realworld.api.services.MediumAPI
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MediumClient {

    val retrofit= Retrofit.Builder().
            baseUrl("https://conduit.productionready.io/api/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()


    val mediumAPI= retrofit.create(MediumAPI::class.java)
}